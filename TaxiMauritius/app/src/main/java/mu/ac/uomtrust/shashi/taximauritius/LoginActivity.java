package mu.ac.uomtrust.shashi.taximauritius;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

import mu.ac.uomtrust.shashi.taximauritius.Async.AsyncCheckAccount;
import mu.ac.uomtrust.shashi.taximauritius.Async.AsyncCreateAccount;
import mu.ac.uomtrust.shashi.taximauritius.DAO.AccountDAO;
import mu.ac.uomtrust.shashi.taximauritius.DTO.AccountDTO;
import mu.ac.uomtrust.shashi.taximauritius.Enums.Gender;
import mu.ac.uomtrust.shashi.taximauritius.Enums.UserRole;
import mu.ac.uomtrust.shashi.taximauritius.Enums.UserStatus;

public class LoginActivity extends Activity {

    CallbackManager callbackManager;
    AccountDTO accountDTO = new AccountDTO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        SharedPreferences prefs = getSharedPreferences("TaxiMauritius", MODE_PRIVATE);
        Boolean login = prefs.getBoolean("login", false);
        if(login){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {

            setContentView(R.layout.activity_login);

            callbackManager = CallbackManager.Factory.create();

            LoginButton loginButton = (LoginButton) findViewById(R.id.btnFbLogin);
            loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    GraphRequest request = GraphRequest.newMeRequest(
                            loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {

                                    if (object != null) {
                                        getFbData(object);
                                        if(accountDTO == null || accountDTO.getAccountId() == null || accountDTO.getAccountId() <1)
                                            selectUserType();
                                    } else {
                                        Utils.disconnectFromFacebook();
                                    }
                                }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id, picture.type(large), first_name, last_name, email,gender, birthday");
                    request.setParameters(parameters);
                    request.executeAsync();
                }

                @Override
                public void onCancel() {
                    Utils.disconnectFromFacebook();
                }

                @Override
                public void onError(FacebookException error) {
                    Utils.disconnectFromFacebook();
                }
                //...
            });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void getFbData(JSONObject object){
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            if(checkIfAccountExist(object.getString("email"))) {
                SharedPreferences.Editor editor = getSharedPreferences("TaxiMauritius", MODE_PRIVATE).edit();
                editor.putBoolean("login", true);
                editor.putInt("accountId", accountDTO.getAccountId());
                editor.commit();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            else {

                if (object.has("picture")) {
                    String profilePicUrl = object.getJSONObject("picture").getJSONObject("data").getString("url");

                    if (!TextUtils.isEmpty(profilePicUrl) && !profilePicUrl.equalsIgnoreCase("null")) {
                        URL fb_url = new URL(profilePicUrl);
                        HttpsURLConnection conn1 = (HttpsURLConnection) fb_url.openConnection();
                        HttpsURLConnection.setFollowRedirects(true);
                        conn1.setInstanceFollowRedirects(true);

                        accountDTO.setProfilePicture(Utils.toByteArray(conn1.getInputStream()));
                    }
                }

                if (object.has("email")) {
                    accountDTO.setEmail(object.getString("email"));
                }

                if (object.has("id")) {
                    accountDTO.setFacebookUserId(object.getString("id"));
                }

                if (object.has("first_name")) {
                    accountDTO.setFirstName(object.getString("first_name"));
                }
                if (object.has("last_name")) {
                    accountDTO.setLastName(object.getString("last_name"));
                }

                Gender gender;
                if (object.has("gender")) {
                    gender = object.getString("gender").equalsIgnoreCase("female") ? Gender.FEMALE : Gender.MALE;
                    accountDTO.setGender(gender);
                } else {
                    accountDTO.setGender(Gender.MALE);
                }

                if (object.has("birthday")) {
                    String[] dob = object.getString("birthday").split("/");
                    Calendar c = Calendar.getInstance();
                    c.set(Integer.parseInt(dob[2]), Integer.parseInt(dob[0]), Integer.parseInt(dob[1]));
                    accountDTO.setDateOfBirth(c.getTime());
                }

                accountDTO.setAccountId(-1);
                accountDTO.setUserStatus(UserStatus.ACTIVE);
                accountDTO.setDateCreated(new Date());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private boolean checkIfAccountExist(String email){
        Boolean exist = false;
        try {
            accountDTO.setAccountId(new AsyncCheckAccount(LoginActivity.this).execute(email).get());
            exist = accountDTO.getAccountId() == null? false: true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return exist;
    }
    private void selectUserType() {
        final Dialog dialog = new Dialog(LoginActivity.this, R.style.WalkthroughTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_dilaogue_user_type);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        Spinner spinnerUserType = (Spinner) dialog.findViewById(R.id.spinnerUserType);

        final ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.user_type_arrays,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUserType.setAdapter(adapter);

        spinnerUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 1){
                    dialog.dismiss();

                    accountDTO.setRole(UserRole.TAXI_DRIVER);
                    new AccountDAO(LoginActivity.this).saveOrUpdateAccount(accountDTO);

                    Intent intent = new Intent(LoginActivity.this, CompleteDriverRegistration.class);
                    //intent.putExtra("accountDTO", accountDTO);
                    startActivity(intent);
                    finish();
                }
                else if (position == 2){
                    dialog.dismiss();

                    accountDTO.setRole(UserRole.USER);
                    new AccountDAO(LoginActivity.this).saveOrUpdateAccount(accountDTO);
                    new AsyncCreateAccount(LoginActivity.this).execute(accountDTO);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        dialog.show();
    }
}
