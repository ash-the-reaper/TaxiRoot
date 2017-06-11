package mu.ac.uomtrust.shashi.taximauritius;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.FacebookSdk;

import mu.ac.uomtrust.shashi.taximauritius.DAO.AccountDAO;
import mu.ac.uomtrust.shashi.taximauritius.DTO.AccountDTO;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View view =  navigationView.getHeaderView(0);


        SharedPreferences prefs = getSharedPreferences("TaxiMauritius", MODE_PRIVATE);
        Integer accountId = prefs.getInt("accountId", 1);

        AccountDTO accountDTO = new AccountDAO(this).getAccountById(accountId);

        ImageView imgProfilePic = (ImageView) view.findViewById(R.id.imgProfilePic);
        imgProfilePic.setImageBitmap(Utils.convertBlobToBitmap(accountDTO.getProfilePicture()));

        TextView txtFirstName = (TextView) view.findViewById(R.id.txtFirstName);
        txtFirstName.setText(accountDTO.getFirstName());

        TextView txtLastName = (TextView) view.findViewById(R.id.txtLastName);
        txtLastName.setText(accountDTO.getLastName());


        MapActivity mapActivity = new MapActivity();
        changeFragment(mapActivity);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       /* int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navGoogleMap) {
            MapActivity mapActivity = new MapActivity();
            changeFragment(mapActivity);
        } else if (id == R.id.navSearch) {

        } else if (id == R.id.navHistory) {

        }
        else if(id == R.id.navLogOut){
           /* Logout logout = new Logout();
            changeFragment(logout);*/
            //popUpLogout();
            Intent intent = new Intent(MainActivity.this, LogOut.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeFragment(Fragment fragment){
        // Create new fragment and transaction
        Fragment newFragment = fragment;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
        transaction.replace(R.id.details, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    private void popUpLogout(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

        // Setting Dialog Title
        alertDialog.setTitle("Log out");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want to log out?");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.taxi_logo);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                SharedPreferences.Editor editor = getSharedPreferences("TaxiMauritius", MODE_PRIVATE).edit();
                editor.remove("login");
                editor.remove("accountId");
                editor.commit();

                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                //getActivity().finish();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}
