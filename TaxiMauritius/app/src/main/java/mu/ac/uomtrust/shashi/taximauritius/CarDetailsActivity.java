package mu.ac.uomtrust.shashi.taximauritius;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import mu.ac.uomtrust.shashi.taximauritius.Async.AsyncCreateAccount;
import mu.ac.uomtrust.shashi.taximauritius.DAO.AccountDAO;
import mu.ac.uomtrust.shashi.taximauritius.DAO.CarDetailsDAO;
import mu.ac.uomtrust.shashi.taximauritius.DTO.AccountDTO;
import mu.ac.uomtrust.shashi.taximauritius.DTO.CarDetailsDTO;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

/**
 * Created by Ashwin on 05-Jun-17.
 */

public class CarDetailsActivity extends Activity {

    private TextView editTextMake, editTextPassenger, editTextYear ,editTextPlateNum;
    private ImageView img1, img2, img3, img4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        editTextMake = (TextView)findViewById(R.id.editTextMake);
        editTextPassenger = (TextView)findViewById(R.id.editTextNumOfPassenger);
        editTextYear = (TextView)findViewById(R.id.editTextYear);
        editTextPlateNum = (TextView)findViewById(R.id.editTextPlateNum);

        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);

        CarDetailsDTO carDetailsDTO = (CarDetailsDTO) getIntent().getSerializableExtra("carDetails");

        editTextMake.setText(carDetailsDTO.getMake());
        editTextPlateNum.setText(carDetailsDTO.getPlateNum());
        editTextPassenger.setText(String.valueOf(carDetailsDTO.getNumOfPassenger()));
        editTextYear.setText(String.valueOf(carDetailsDTO.getYear()));

        if(carDetailsDTO.getPicture1() != null)
            img1.setImageBitmap(Utils.convertBlobToBitmap(carDetailsDTO.getPicture1()));

        if(carDetailsDTO.getPicture2() != null)
            img2.setImageBitmap(Utils.convertBlobToBitmap(carDetailsDTO.getPicture2()));

        if(carDetailsDTO.getPicture3() != null)
            img3.setImageBitmap(Utils.convertBlobToBitmap(carDetailsDTO.getPicture3()));

        if(carDetailsDTO.getPicture4() != null)
            img4.setImageBitmap(Utils.convertBlobToBitmap(carDetailsDTO.getPicture4()));
    }
}
