package mu.ac.uomtrust.shashi.taximauritius.Async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import mu.ac.uomtrust.shashi.taximauritius.DTO.AccountDTO;
import mu.ac.uomtrust.shashi.taximauritius.Utils;
import mu.ac.uomtrust.shashi.taximauritius.WebService;

/**
 * Created by Ashwin on 03-Jun-17.
 */

public class AsyncCheckAccount extends AsyncTask<String, Void ,Integer > {

    private Context context;
    private ProgressDialog progressDialog;

    public AsyncCheckAccount(final Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = Utils.progressDialogue(context, "Creating your account");
        progressDialog.show();
    }


    @Override
    protected Integer doInBackground(String... params) {
        JSONObject postData = new JSONObject();
        AccountDTO accountDTO  = new AccountDTO();
        accountDTO.setEmail(params[0]);

        try{
            postData.put("email", accountDTO.getEmail());

            HttpURLConnection httpURLConnection = null;
            try {

                httpURLConnection = (HttpURLConnection) new URL(WebService.API_CHECK_ACCOUNT_VIA_EMAIL).openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setRequestProperty("Accept", "application/json");
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

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            return accountDTO.getAccountId();

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            progressDialog.dismiss();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Integer result){
        super.onPostExecute(result);
    }
}
