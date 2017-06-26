package mu.ac.uomtrust.shashi.taximauritius.Async;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import org.json.JSONArray;
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
import mu.ac.uomtrust.shashi.taximauritius.DTO.RequestDTO;
import mu.ac.uomtrust.shashi.taximauritius.Enums.RequestStatus;
import mu.ac.uomtrust.shashi.taximauritius.Enums.UserRole;
import mu.ac.uomtrust.shashi.taximauritius.RequestAdapter;
import mu.ac.uomtrust.shashi.taximauritius.Utils;
import mu.ac.uomtrust.shashi.taximauritius.WebService;

import static android.content.Context.MODE_PRIVATE;

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

            SharedPreferences prefs = context.getSharedPreferences("TaxiMauritius", MODE_PRIVATE);
            int userId = prefs.getInt("accountId", 1);

            RequestDTO requestDTO = params[0];
            if(requestDTO != null) {
                postData.put("requestStatus", requestDTO.getRequestStatus().getValue());
                postData.put("accountId", userId);
            }

            String url;

            UserRole userRole = new AccountDAO(context).getAccountById(userId).getRole();
            if(userRole == UserRole.TAXI_DRIVER) {
                if(requestDTO.getRequestStatus().equals(RequestStatus.REQUEST_PENDING))
                    url = WebService.TAXI_API_GET_PENDING_REQUEST_LIST;
                else
                    url = WebService.TAXI_API_OTHER_REQUEST_LIST;
            }
            else{
                if(requestDTO.getRequestStatus().equals(RequestStatus.REQUEST_PENDING))
                    url = WebService.USER_API_GET_PENDING_REQUEST_LIST;
                else
                    url = WebService.USER_API_OTHER_REQUEST_LIST;
            }

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
                newRequestDTO.setRequestStatus(Utils.setRequestStatus(jsonObject.getString("requestStatus")));
                newRequestDTO.setDetails(jsonObject.getString("details"));
                newRequestDTO.setRequestId(jsonObject.getInt("requestId"));
                newRequestDTO.setEvenDateTime(new Date(jsonObject.getLong("eventDateTime")));
                newRequestDTO.setPlaceFrom(jsonObject.getString("placeFrom"));
                newRequestDTO.setPlaceTo(jsonObject.getString("placeTo"));

                if (jsonObject.get("price") != null && !jsonObject.get("price").equals("null") && !jsonObject.getString("price").equals("null"))
                    newRequestDTO.setPrice(jsonObject.getInt("price"));

                if (jsonObject.getString("driverName") != null && !jsonObject.getString("driverName").equals("null") )
                    newRequestDTO.setDriverName(jsonObject.getString("driverName"));

                if(jsonObject.get("carId") != null && !jsonObject.get("carId").equals("null") && !jsonObject.getString("carId").equals("null"))
                    newRequestDTO.setCarId(jsonObject.getInt("carId"));


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

        requestAdapter.setRequestDTOList(requestDTOList);
        requestAdapter.notifyDataSetChanged();
    }
}
