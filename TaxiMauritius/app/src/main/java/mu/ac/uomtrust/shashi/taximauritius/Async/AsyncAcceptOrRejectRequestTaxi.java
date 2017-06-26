package mu.ac.uomtrust.shashi.taximauritius.Async;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import mu.ac.uomtrust.shashi.taximauritius.DAO.CarDetailsDAO;
import mu.ac.uomtrust.shashi.taximauritius.DAO.RequestDAO;
import mu.ac.uomtrust.shashi.taximauritius.DTO.RequestDTO;
import mu.ac.uomtrust.shashi.taximauritius.ManageRequestActivity;
import mu.ac.uomtrust.shashi.taximauritius.R;
import mu.ac.uomtrust.shashi.taximauritius.Utils;
import mu.ac.uomtrust.shashi.taximauritius.WebService;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ashwin on 03-Jun-17.
 */

public class AsyncAcceptOrRejectRequestTaxi extends AsyncTask<RequestDTO, Void ,RequestDTO > {

    private Context context;
    private ProgressDialog progressDialog;
    private FragmentManager fragmentManager;
    boolean newRequest;

    public AsyncAcceptOrRejectRequestTaxi(final Context context, FragmentManager fragmentManager , boolean newRequest) {
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
    protected RequestDTO doInBackground(RequestDTO... params) {
        JSONObject postData = new JSONObject();
        RequestDTO requestDTO = params[0];

        HttpURLConnection httpURLConnection = null;

        try{
            if(requestDTO.getRequestId() != null && requestDTO.getRequestId() >0)
                postData.put("requestId", requestDTO.getRequestId());

            postData.put("accountId", requestDTO.getAccountId());
            SharedPreferences prefs = context.getSharedPreferences("TaxiMauritius", MODE_PRIVATE);
            int userId = prefs.getInt("accountId", 1);
            int carId = new CarDetailsDAO(context).getCarDetailsByAccountID(userId).getCarId();
            postData.put("carId", carId);
            postData.put("dateUpdated", requestDTO.getDateUpdated().getTime());
            postData.put("dateCreated", requestDTO.getDateCreated().getTime());
            postData.put("requestStatus", requestDTO.getRequestStatus());

            if(requestDTO.getPrice() != null)
                postData.put("price", requestDTO.getPrice());


            httpURLConnection = (HttpURLConnection) new URL(WebService.TAXI_API_ACCEPT_OR_REJECT_REQUEST).openConnection();
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
            requestDTO.setRequestId(jsonObject.getInt("requestId"));

            return requestDTO;

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
    protected void onPostExecute(RequestDTO requestDTO){
        super.onPostExecute(requestDTO);

        if(requestDTO != null && requestDTO.getRequestId() > 0) {
            //new RequestDAO(context).saveOrUpdateRequest(requestDTO);
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
