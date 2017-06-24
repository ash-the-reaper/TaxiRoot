package mu.ac.uomtrust.shashi.taximauritius.Async;

import android.app.FragmentManager;
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

import mu.ac.uomtrust.shashi.taximauritius.DAO.ManageRequestDAO;
import mu.ac.uomtrust.shashi.taximauritius.DTO.ManageRequestDTO;
import mu.ac.uomtrust.shashi.taximauritius.ManageRequestActivity;
import mu.ac.uomtrust.shashi.taximauritius.R;
import mu.ac.uomtrust.shashi.taximauritius.Utils;
import mu.ac.uomtrust.shashi.taximauritius.WebService;

/**
 * Created by Ashwin on 03-Jun-17.
 */

public class AsyncTaxiCreateOrUpdateRequest extends AsyncTask<ManageRequestDTO, Void ,ManageRequestDTO> {

    private Context context;
    private ProgressDialog progressDialog;
    private FragmentManager fragmentManager;
    boolean newRequest;

    public AsyncTaxiCreateOrUpdateRequest(final Context context, FragmentManager fragmentManager , boolean newRequest) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.newRequest = newRequest;
    }

    @Override
    protected void onPreExecute() {
        String message;
        if(newRequest)
            message =  "Creating your request";
        else
            message =  "Updating your request";

        progressDialog = Utils.progressDialogue(context,message);
        progressDialog.show();
    }


    @Override
    protected ManageRequestDTO doInBackground(ManageRequestDTO... params) {
        JSONObject postData = new JSONObject();
        ManageRequestDTO manageRequestDTO = params[0];

        HttpURLConnection httpURLConnection = null;

        try{
            postData.put("requestId", manageRequestDTO.getRequestId());
            postData.put("dateCreated", manageRequestDTO.getDateCreated());
            postData.put("dateupdated", manageRequestDTO.getDateUpdated());
            postData.put("price", manageRequestDTO.getPrice());
            postData.put("carId", manageRequestDTO.getCarId());
            postData.put("requestStatus", manageRequestDTO.getRequestStatus());
            postData.put("accountId", manageRequestDTO.getAccountId());


            httpURLConnection = (HttpURLConnection) new URL(WebService.USER_API_CREATE_UPDATE_REQUEST).openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            httpURLConnection.setRequestProperty("Accept", "application/json;charset=UTF-8");
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
            manageRequestDTO.setRequestId(jsonObject.getInt("manageRequestId"));

            return manageRequestDTO;

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
    protected void onPostExecute(ManageRequestDTO manageRequestDTO){
        super.onPostExecute(manageRequestDTO);

        if(manageRequestDTO != null && manageRequestDTO.getRequestId() > 0) {
            new ManageRequestDAO(context).saveOrUpdateManageRequest(manageRequestDTO);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.details, new ManageRequestActivity())
                    .addToBackStack(null)
                    .commit();
        }
        else{
            Utils.showToast(context, context.getString(R.string.error_server));
        }
    }
}
