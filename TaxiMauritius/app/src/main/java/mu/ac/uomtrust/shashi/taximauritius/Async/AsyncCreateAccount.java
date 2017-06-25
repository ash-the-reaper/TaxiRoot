package mu.ac.uomtrust.shashi.taximauritius.Async;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import mu.ac.uomtrust.shashi.taximauritius.DAO.AccountDAO;
import mu.ac.uomtrust.shashi.taximauritius.DAO.CarDetailsDAO;
import mu.ac.uomtrust.shashi.taximauritius.DTO.AccountDTO;
import mu.ac.uomtrust.shashi.taximauritius.DTO.CarDetailsDTO;
import mu.ac.uomtrust.shashi.taximauritius.Enums.UserRole;
import mu.ac.uomtrust.shashi.taximauritius.LoginActivity;
import mu.ac.uomtrust.shashi.taximauritius.MainActivity;
import mu.ac.uomtrust.shashi.taximauritius.R;
import mu.ac.uomtrust.shashi.taximauritius.Utils;
import mu.ac.uomtrust.shashi.taximauritius.WebService;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ashwin on 03-Jun-17.
 */

public class AsyncCreateAccount extends AsyncTask<AccountDTO, Void ,AccountDTO > {

    private Context context;
    private ProgressDialog progressDialog;

    public AsyncCreateAccount(final Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = Utils.progressDialogue(context, "Creating your account");
        progressDialog.show();
    }


    @Override
    protected AccountDTO doInBackground(AccountDTO... params) {
        JSONObject postData = new JSONObject();
        AccountDTO accountDTO = params[0];
        HttpURLConnection httpURLConnection = null;

        try{
            postData.put("firstName", accountDTO.getFirstName());
            postData.put("lastName", accountDTO.getLastName());
            postData.put("email", accountDTO.getEmail());
            postData.put("userRole", accountDTO.getRole());
            postData.put("userStatus", accountDTO.getUserStatus());
            postData.put("gender", accountDTO.getGender());

            if(accountDTO.getAddress() != null)
                postData.put("address", accountDTO.getAddress());


            httpURLConnection = (HttpURLConnection) new URL(WebService.API_CREATE_ACCOUNT).openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            httpURLConnection.setRequestProperty("Accept", "application/json; charset=utf-8");
            httpURLConnection.setRequestProperty("accept-charset", "UTF-8");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(postData.toString());
            wr.flush();
            wr.close();

            InputStream responseStream = httpURLConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream));

            final StringBuilder builder = new StringBuilder();
            String inputLine;

            while ((inputLine = reader.readLine()) != null) {
                builder.append(inputLine).append("\n");
            }

            JSONObject jsonObject = new JSONObject(builder.toString());
            accountDTO.setAccountId(jsonObject.getInt("accountId"));

            return accountDTO;

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }

            if(progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
        }

        return null;
    }

    @Override
    protected void onPostExecute(AccountDTO accountDTO){
        super.onPostExecute(accountDTO);

        if(accountDTO != null && accountDTO.getAccountId() > 0) {
            new AccountDAO(context).updateAccountIdFromWS(accountDTO.getAccountId());

            SharedPreferences.Editor editor = context.getSharedPreferences("TaxiMauritius", MODE_PRIVATE).edit();
            editor.putBoolean("login", true);
            editor.putInt("accountId", accountDTO.getAccountId());
            editor.commit();


            if (accountDTO.getRole() == UserRole.TAXI_DRIVER) {
                CarDetailsDTO carDetailsDTO = new CarDetailsDAO(context).getCarDetailsByAccountID(-1);
                carDetailsDTO.setAccountId(accountDTO.getAccountId());
                new CarDetailsDAO(context).saveOrUpdateCarDetails(carDetailsDTO);
                new AsyncCreateCarDetails(context).execute(carDetailsDTO);
            } else {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        }
        else{
            Utils.showToast(context, context.getString(R.string.error_server));
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        }
    }
}
