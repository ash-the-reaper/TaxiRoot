package mu.ac.uomtrust.shashi.taximauritius.Async;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.android.gms.maps.MapFragment;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import mu.ac.uomtrust.shashi.taximauritius.DAO.AccountDAO;
import mu.ac.uomtrust.shashi.taximauritius.DAO.CarDetailsDAO;
import mu.ac.uomtrust.shashi.taximauritius.DAO.RequestDAO;
import mu.ac.uomtrust.shashi.taximauritius.DTO.AccountDTO;
import mu.ac.uomtrust.shashi.taximauritius.DTO.CarDetailsDTO;
import mu.ac.uomtrust.shashi.taximauritius.DTO.RequestDTO;
import mu.ac.uomtrust.shashi.taximauritius.Enums.RequestStatus;
import mu.ac.uomtrust.shashi.taximauritius.Enums.UserRole;
import mu.ac.uomtrust.shashi.taximauritius.MainActivity;
import mu.ac.uomtrust.shashi.taximauritius.R;
import mu.ac.uomtrust.shashi.taximauritius.Utils;
import mu.ac.uomtrust.shashi.taximauritius.WebService;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ashwin on 03-Jun-17.
 */

public class AsyncCreateRequest extends AsyncTask<RequestDTO, Void ,RequestDTO > {

    private Context context;
    private ProgressDialog progressDialog;
    private FragmentManager fragmentManager;

    public AsyncCreateRequest(final Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = Utils.progressDialogue(context, "Creating your request");
        progressDialog.show();
    }


    @Override
    protected RequestDTO doInBackground(RequestDTO... params) {
        JSONObject postData = new JSONObject();
        RequestDTO requestDTO = params[0];

        try{
            postData.put("placeFrom", requestDTO.getPlaceFrom());
            postData.put("placeTo", requestDTO.getPlaceTo());
            postData.put("requestStatus", RequestStatus.REQUEST_PENDING);
            postData.put("accountId", requestDTO.getAccountId());

            HttpURLConnection httpURLConnection = null;
            try {

                httpURLConnection = (HttpURLConnection) new URL(WebService.API_CREATE_REQUEST).openConnection();
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
                requestDTO.setRequestId(jsonObject.getInt("requestId"));

                Date date = new Date();
                requestDTO.setDateCreated(date);
                requestDTO.setDateUpdated(date);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            return requestDTO;

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            progressDialog.dismiss();
        }

        return null;
    }

    @Override
    protected void onPostExecute(RequestDTO requestDTO){
        super.onPostExecute(requestDTO);
        new RequestDAO(context).saveRequest(requestDTO);
        fragmentManager
                .beginTransaction()
                .replace(R.id.details, new MapFragment())
                .commit();
    }
}
