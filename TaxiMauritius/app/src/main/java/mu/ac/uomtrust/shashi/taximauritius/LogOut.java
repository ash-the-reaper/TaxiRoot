package mu.ac.uomtrust.shashi.taximauritius;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.FacebookSdk;

import mu.ac.uomtrust.shashi.taximauritius.DTO.AccountDTO;

/**
 * Created by Ashwin on 11-Jun-17.
 */

public class LogOut extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.logout);

        TextView txtYes = (TextView)findViewById(R.id.txtYes);
        txtYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.disconnectFromFacebook();

                SharedPreferences.Editor editor = getSharedPreferences("TaxiMauritius", MODE_PRIVATE).edit();
                editor.remove("login");
                editor.remove("accountId");
                editor.commit();

                Intent intent = new Intent(LogOut.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        TextView txtNo = (TextView)findViewById(R.id.txtNo);
        txtNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
