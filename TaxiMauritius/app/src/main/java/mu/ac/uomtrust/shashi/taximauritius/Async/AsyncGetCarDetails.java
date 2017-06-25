package mu.ac.uomtrust.shashi.taximauritius.Async;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Base64;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import mu.ac.uomtrust.shashi.taximauritius.CarDetailsActivity;
import mu.ac.uomtrust.shashi.taximauritius.DAO.CarDetailsDAO;
import mu.ac.uomtrust.shashi.taximauritius.DTO.CarDetailsDTO;
import mu.ac.uomtrust.shashi.taximauritius.MainActivity;
import mu.ac.uomtrust.shashi.taximauritius.R;
import mu.ac.uomtrust.shashi.taximauritius.Utils;
import mu.ac.uomtrust.shashi.taximauritius.ViewRequestFromTaxiActivity;
import mu.ac.uomtrust.shashi.taximauritius.WebService;

/**
 * Created by Ashwin on 03-Jun-17.
 */

public class AsyncGetCarDetails extends AsyncTask<CarDetailsDTO, Void ,CarDetailsDTO > {

    private Context context;
    private ProgressDialog progressDialog;

    public AsyncGetCarDetails(final Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = Utils.progressDialogue(context, "Fetching car details");
    }


    @Override
    protected CarDetailsDTO doInBackground(CarDetailsDTO... params) {
        JSONObject postData = new JSONObject();
        CarDetailsDTO carDetailsDTO = params[0];
        HttpURLConnection httpURLConnection = null;

        try{
            postData.put("carId", carDetailsDTO.getCarId());

            httpURLConnection = (HttpURLConnection) new URL(WebService.TAXI_API_GET_CAR_DETAILS).openConnection();
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
            carDetailsDTO.setCarId(jsonObject.getInt("carId"));
            carDetailsDTO.setYear(jsonObject.getInt("year"));
            carDetailsDTO.setAccountId(jsonObject.getInt("accountId"));
            carDetailsDTO.setMake(jsonObject.getString("make"));
            carDetailsDTO.setPlateNum(jsonObject.getString("plateNum"));
            carDetailsDTO.setNumOfPassenger(jsonObject.getInt("numOfPassenger"));


            if(jsonObject.get("sPicture1") != null)
                carDetailsDTO.setPicture1(Base64.decode(jsonObject.getString("sPicture1"), Base64.DEFAULT));

            if(jsonObject.get("sPicture2") != null)
                carDetailsDTO.setPicture2(Base64.decode(jsonObject.getString("sPicture2"), Base64.DEFAULT));

            if(jsonObject.get("sPicture3") != null)
                carDetailsDTO.setPicture3(Base64.decode(jsonObject.getString("sPicture3"), Base64.DEFAULT));

            if(jsonObject.get("sPicture4") != null)
                carDetailsDTO.setPicture4(Base64.decode(jsonObject.getString("sPicture4"), Base64.DEFAULT));

            return carDetailsDTO;

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }

            if(progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
        }

        return null;
    }

    @Override
    protected void onPostExecute(CarDetailsDTO carDetailsDTO){
        super.onPostExecute(carDetailsDTO);

        if(carDetailsDTO != null && carDetailsDTO.getCarId() > 0) {
            Intent intent = new Intent(context, CarDetailsActivity.class);
            intent.putExtra("carDetails", carDetailsDTO);
            context.startActivity(intent);
        }
        else{
            Utils.showToast(context, context.getString(R.string.error_server));
        }
    }
}
