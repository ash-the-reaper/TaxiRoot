package mu.ac.uomtrust.shashi.taximauritius;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import mu.ac.uomtrust.shashi.taximauritius.DTO.CarDetailsDTO;
import mu.ac.uomtrust.shashi.taximauritius.Enums.RequestStatus;

/**
 * Created by Ashwin on 28-May-17.
 */



public class Utils {

    public static void disconnectFromFacebook() {

        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();

            }
        }).executeAsync();
    }

    public static void deletePhoto(File file) {
        file.delete();
    }



    public static byte[] convertBitmapToBlob(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 55, bos);
        return bos.toByteArray();
    }

    public static Bitmap convertBlobToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    /**
     * Reads and returns the rest of the given input stream as a byte array,
     * closing the input stream afterwards.
     */
    public static byte[] toByteArray(InputStream is) throws IOException {
        final int BUFFER_SIZE = 1024 * 4;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            byte[] b = new byte[BUFFER_SIZE];
            int n = 0;
            while ((n = is.read(b)) != -1) {
                output.write(b, 0, n);
            }
            return output.toByteArray();
        } finally {
            output.close();
        }
    }

    public static ProgressDialog progressDialogue(Context context, String message){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(message);
        progressDialog.show();
        return progressDialog;
    }

    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static RequestStatus setRequestStatus(String requestStatus){
        if(requestStatus.equalsIgnoreCase("REQUEST_PENDING"))
            return RequestStatus.REQUEST_PENDING;
        else if(requestStatus.equalsIgnoreCase("REQUEST_CANCEL"))
            return RequestStatus.REQUEST_CANCEL;
        else if(requestStatus.equalsIgnoreCase("CLIENT_ACCEPTED"))
            return RequestStatus.CLIENT_ACCEPTED;
        else if(requestStatus.equalsIgnoreCase("CLIENT_REJECTED"))
            return RequestStatus.CLIENT_REJECTED;
        else if(requestStatus.equalsIgnoreCase("TAXI_DRIVER_ACCEPTED"))
            return RequestStatus.TAXI_DRIVER_ACCEPTED;
        else if(requestStatus.equalsIgnoreCase("TAXI_DRIVER_REJECTED"))
            return RequestStatus.TAXI_DRIVER_REJECTED;
        else if(requestStatus.equalsIgnoreCase("PAID"))
            return RequestStatus.PAID;
        else
            return null;
    }
}
