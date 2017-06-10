package mu.ac.uomtrust.shashi.taximauritius.Async;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import mu.ac.uomtrust.shashi.taximauritius.DAO.CarDetailsDAO;
import mu.ac.uomtrust.shashi.taximauritius.DTO.CarDetailsDTO;
import mu.ac.uomtrust.shashi.taximauritius.MainActivity;
import mu.ac.uomtrust.shashi.taximauritius.Utils;
import mu.ac.uomtrust.shashi.taximauritius.WebService;

/**
 * Created by Ashwin on 03-Jun-17.
 */

public class AsyncCreateCarDetails extends AsyncTask<CarDetailsDTO, Void ,Integer > {

    private Context context;
    private ProgressDialog progressDialog;

    public AsyncCreateCarDetails(final Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = Utils.progressDialogue(context, "Creating your car details");
    }


    @Override
    protected Integer doInBackground(CarDetailsDTO... params) {
        JSONObject postData = new JSONObject();
        CarDetailsDTO carDetailsDTO = params[0];

        try{
            postData.put("make", carDetailsDTO.getMake());
            postData.put("numOfPassenger", carDetailsDTO.getNumOfPassenger());
            postData.put("year", carDetailsDTO.getYear());
            postData.put("plateNum", carDetailsDTO.getPlateNum());
            postData.put("picture1", carDetailsDTO.getPicture1());
            postData.put("picture2", carDetailsDTO.getPicture2());
            postData.put("picture3", carDetailsDTO.getPicture3());
            postData.put("picture4", carDetailsDTO.getPicture4());
            postData.put("accountId", carDetailsDTO.getAccountId());

            HttpURLConnection httpURLConnection = null;
            try {

                httpURLConnection = (HttpURLConnection) new URL(WebService.API_CREATE_CAR_DETAILS).openConnection();
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
                carDetailsDTO.setCarId(jsonObject.getInt("carId"));


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            return carDetailsDTO.getCarId();



        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Integer carId){
        super.onPostExecute(carId);

        new CarDetailsDAO(context).updateCarDetailsIdFromWS(carId);

        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
