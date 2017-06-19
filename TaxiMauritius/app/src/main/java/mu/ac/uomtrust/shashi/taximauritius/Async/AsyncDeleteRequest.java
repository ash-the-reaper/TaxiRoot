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

import mu.ac.uomtrust.shashi.taximauritius.DAO.RequestDAO;
import mu.ac.uomtrust.shashi.taximauritius.DTO.RequestDTO;
import mu.ac.uomtrust.shashi.taximauritius.ManageRequestActivity;
import mu.ac.uomtrust.shashi.taximauritius.R;
import mu.ac.uomtrust.shashi.taximauritius.Utils;
import mu.ac.uomtrust.shashi.taximauritius.WebService;

/**
 * Created by Ashwin on 03-Jun-17.
 */

public class AsyncDeleteRequest extends AsyncTask<RequestDTO, Void ,Boolean > {

    private Context context;
    private ProgressDialog progressDialog;
    private FragmentManager fragmentManager;
    private Integer requestId = null;

    public AsyncDeleteRequest(final Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @Override
    protected void onPreExecute() {
        String message = "Deleting your request";

        progressDialog = Utils.progressDialogue(context,message);
        progressDialog.show();
    }


    @Override
    protected Boolean doInBackground(RequestDTO... params) {
        JSONObject postData = new JSONObject();
        RequestDTO requestDTO = params[0];

        HttpURLConnection httpURLConnection = null;

        try{
            postData.put("requestId", requestDTO.getRequestId());

            httpURLConnection = (HttpURLConnection) new URL(WebService.API_DELETE_REQUEST).openConnection();
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
            boolean result = jsonObject.getBoolean("requestId");

            return result;

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
    protected void onPostExecute(Boolean deleted){
        super.onPostExecute(deleted);

        if(deleted == true) {
            new RequestDAO(context).deleteRequest(requestId);
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
