package mu.ac.uomtrust.shashi.taximauritius.Async;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mu.ac.uomtrust.shashi.taximauritius.DAO.AccountDAO;
import mu.ac.uomtrust.shashi.taximauritius.DAO.RequestDAO;
import mu.ac.uomtrust.shashi.taximauritius.DTO.AccountDTO;
import mu.ac.uomtrust.shashi.taximauritius.DTO.RequestDTO;
import mu.ac.uomtrust.shashi.taximauritius.Enums.UserRole;
import mu.ac.uomtrust.shashi.taximauritius.ManageRequestActivity;
import mu.ac.uomtrust.shashi.taximauritius.R;
import mu.ac.uomtrust.shashi.taximauritius.RequestAdapter;
import mu.ac.uomtrust.shashi.taximauritius.Utils;
import mu.ac.uomtrust.shashi.taximauritius.WebService;

/**
 * Created by Ashwin on 03-Jun-17.
 */

public class AsyncGetRequest extends AsyncTask<RequestDTO, Void ,List<RequestDTO >> {

    private Context context;
    private ProgressDialog progressDialog;
    private RequestAdapter requestAdapter;

    public AsyncGetRequest(final Context context, RequestAdapter requestAdapter) {
        this.context = context;
        this.requestAdapter = requestAdapter;
    }

    @Override
    protected void onPreExecute() {
        String message;
        message =  "Fetching requests";

        progressDialog = Utils.progressDialogue(context,message);
        progressDialog.show();
    }


    @Override
    protected List<RequestDTO> doInBackground(RequestDTO... params) {
        JSONObject postData = new JSONObject();

        HttpURLConnection httpURLConnection = null;

        try{

            RequestDTO requestDTO = params[0];
            if(requestDTO != null) {
                postData.put("requestStatus", requestDTO.getRequestStatus().getValue());
                postData.put("accountId", requestDTO.getAccountId());
            }

            String url = WebService.API_GET_REQUEST_LIST_USER;

            UserRole userRole = new AccountDAO(context).getAccountById(requestDTO.getAccountId()).getRole();
            if(userRole == UserRole.TAXI_DRIVER)
                url = WebService.API_GET_REQUEST_LIST_TAXI;

            httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
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

            JSONArray json;

            json = new JSONArray(builder.toString());
            List<RequestDTO> newRequestDTOList = new ArrayList<>();

            for(int x = 0; x<json.length(); x++){
                JSONObject jsonObject = json.getJSONObject(x);
                RequestDTO newRequestDTO = new RequestDTO();

                newRequestDTO.setAccountId(jsonObject.getInt("accountId"));
                newRequestDTO.setDetails(jsonObject.getString("details"));
                newRequestDTO.setRequestId(jsonObject.getInt("requestId"));
                newRequestDTO.setEvenDateTime(new Date(jsonObject.getLong("eventDateTime")));
                newRequestDTO.setPlaceFrom(jsonObject.getString("placeFrom"));
                newRequestDTO.setPlaceTo(jsonObject.getString("placeTo"));
                newRequestDTOList.add(newRequestDTO);
            }

            return newRequestDTOList;

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
    protected void onPostExecute(List<RequestDTO> requestDTOList){
        super.onPostExecute(requestDTOList);

        if(requestDTOList != null && requestDTOList.size() > 0) {
            requestAdapter.setRequestDTOList(requestDTOList);
            requestAdapter.notifyDataSetChanged();
        }
        else{
            //Utils.showToast(context, context.getString(R.string.error_server));
            requestAdapter.setRequestDTOList(requestDTOList);
            requestAdapter.notifyDataSetChanged();
        }
    }
}
