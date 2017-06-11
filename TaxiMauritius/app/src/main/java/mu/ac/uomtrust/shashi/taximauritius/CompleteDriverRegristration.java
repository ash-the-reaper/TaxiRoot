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
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mu.ac.uomtrust.shashi.taximauritius.Async.AsyncCreateAccount;
import mu.ac.uomtrust.shashi.taximauritius.Async.AsyncCreateCarDetails;
import mu.ac.uomtrust.shashi.taximauritius.DAO.AccountDAO;
import mu.ac.uomtrust.shashi.taximauritius.DAO.CarDetailsDAO;
import mu.ac.uomtrust.shashi.taximauritius.DTO.AccountDTO;
import mu.ac.uomtrust.shashi.taximauritius.DTO.CarDetailsDTO;

import static android.R.attr.bitmap;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

/**
 * Created by Ashwin on 05-Jun-17.
 */

public class CompleteDriverRegristration extends Activity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PERMISSION_CAMERA = 2;
    private static final int CAMERA = 3;
    private static final String IMAGE_DIRECTORY_NAME = "Chombo";
    private int nextImage = -1;

    Dialog dialog;

    EditText editTextMake, editTextPassenger, editTextYear ,editTextPlateNum;
    ImageView img1, img2, img3, img4;

    Button btnAddImage, btnNext;

    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_registration);

        editTextMake = (EditText)findViewById(R.id.editTextMake);
        editTextPassenger = (EditText)findViewById(R.id.editTextNumOfPassenger);
        editTextYear = (EditText)findViewById(R.id.editTextYear);
        editTextPlateNum = (EditText)findViewById(R.id.editTextPlateNum);

        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);

        btnAddImage = (Button) findViewById(R.id.btnAddImage);
        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addImage();
            }
        });

        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateForm())
                    setData();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            // getData for single -- getClipData returns null

            //if single image()
            try {
                if(data.getData() != null){
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    ImageView imageView = checkAvailability();
                    imageView.setImageBitmap(bitmap);
                }
                // multiple images
                else if(data.getData() == null && data.getClipData() != null){
                    int size = data.getClipData().getItemCount() > 4 ? 4 : data.getClipData().getItemCount();
                    for (int x = 0; x < size; x++) {
                        Uri uri = data.getClipData().getItemAt(x).getUri();

                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                        ImageView imageView = checkAvailability();
                        imageView.setImageBitmap(bitmap);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(requestCode == CAMERA && resultCode == RESULT_OK) {
            try {
                // bimatp factory
                BitmapFactory.Options options = new BitmapFactory.Options();

                // downsizing image as it throws OutOfMemory Exception for larger
                // images
                options.inSampleSize = 8;
                Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);

                ImageView imageView = checkAvailability();
                imageView.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        if(dialog != null)
            dialog.dismiss();
    }

    private ImageView checkAvailability(){

        ImageView imgArr [] = {img1 ,img2, img3, img4};

        if(nextImage == 3)
            nextImage = 0;
        else
            nextImage++;
        return imgArr[nextImage];

    }

    private boolean validateForm(){
        boolean validForm = true;
        if(TextUtils.isEmpty(editTextMake.getText().toString())) {
            Utils.showToast(CompleteDriverRegristration.this, getResources().getString(R.string.complete_registration_validation_make));
            validForm = false;
        }
        else if(TextUtils.isEmpty(editTextYear.getText().toString())) {
            Utils.showToast(CompleteDriverRegristration.this, getResources().getString(R.string.complete_registration_validation_year));
            validForm = false;
        }
        else if(Integer.parseInt(editTextYear.getText().toString()) > Calendar.getInstance().get(Calendar.YEAR)) {
            Utils.showToast(CompleteDriverRegristration.this, getResources().getString(R.string.complete_registration_validation_year_above));
            validForm = false;
        }
        else if((Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(editTextYear.getText().toString())) > 15) {
            Utils.showToast(CompleteDriverRegristration.this, getResources().getString(R.string.complete_registration_validation_year_below));
            validForm = false;
        }
        else if(TextUtils.isEmpty(editTextPlateNum.getText().toString())) {
            Utils.showToast(CompleteDriverRegristration.this, getResources().getString(R.string.complete_registration_validation_number_of_passenger));
            validForm = false;
        }
        else if(TextUtils.isEmpty(editTextPassenger.getText().toString())){
            Utils.showToast(CompleteDriverRegristration.this, getResources().getString(R.string.complete_registration_validation_number_of_passenger));
            validForm = false;
        }
        else if(Integer.parseInt(editTextPassenger.getText().toString()) > 6){
            Utils.showToast(CompleteDriverRegristration.this, getResources().getString(R.string.complete_registration_validation_number_of_passenger_above));
            validForm = false;
        }
        else if(Integer.parseInt(editTextPassenger.getText().toString()) <1){
            Utils.showToast(CompleteDriverRegristration.this, getResources().getString(R.string.complete_registration_validation_number_of_passenger_below));
            validForm = false;
        }
        else if(img1.getDrawable() == null || img2.getDrawable() == null) {
            Utils.showToast(CompleteDriverRegristration.this, getResources().getString(R.string.complete_registration_validation_image));
            validForm = false;
        }
        
        return validForm;
    }


    private void addImage() {
        dialog = new Dialog(CompleteDriverRegristration.this, R.style.WalkthroughTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_dilaogue_camera);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);

        Button btnGallery = (Button)dialog.findViewById(R.id.btnGallery);
        Button btnCamera = (Button)dialog.findViewById(R.id.btnCamera);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(intent, "Select max 4 Pictures"), PICK_IMAGE_REQUEST);
            }
        });

        dialog.show();
    }

    private void openCamera(){
        if (
                ActivityCompat.checkSelfPermission(CompleteDriverRegristration.this, Manifest.permission.CAMERA ) != PackageManager.PERMISSION_GRANTED
            ||  ActivityCompat.checkSelfPermission(CompleteDriverRegristration.this,Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED
            ||  ActivityCompat.checkSelfPermission(CompleteDriverRegristration.this, Manifest.permission.READ_EXTERNAL_STORAGE )!= PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(CompleteDriverRegristration.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CAMERA);
        }
        else {
           /* Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivityForResult(intent, CAMERA);*/

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent, CAMERA);

        }
    }

    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }



    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /*
 * returning image / video
 */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CAMERA:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED )
                    openCamera();
                break;
        }
    }

    private void setData(){

        CarDetailsDTO carDetailsDTO = new CarDetailsDTO();
        carDetailsDTO.setCarId(-1);
        carDetailsDTO.setMake(editTextMake.getText().toString());
        carDetailsDTO.setNumOfPassenger(Integer.parseInt(editTextPassenger.getText().toString()));
        carDetailsDTO.setYear(Integer.parseInt(editTextYear.getText().toString()));
        carDetailsDTO.setPlateNum(editTextPlateNum.getText().toString());
        carDetailsDTO.setAccountId(-1);

        carDetailsDTO.setPicture1(Utils.convertBitmapToBlob(((BitmapDrawable)img1.getDrawable()).getBitmap()));
        carDetailsDTO.setPicture2(Utils.convertBitmapToBlob(((BitmapDrawable)img2.getDrawable()).getBitmap()));

        if(img3.getDrawable() != null)
            carDetailsDTO.setPicture3(Utils.convertBitmapToBlob(((BitmapDrawable)img3.getDrawable()).getBitmap()));

        if(img4.getDrawable() != null)
            carDetailsDTO.setPicture4(Utils.convertBitmapToBlob(((BitmapDrawable)img4.getDrawable()).getBitmap()));

        new CarDetailsDAO(CompleteDriverRegristration.this).saveCarDetails(carDetailsDTO);

        AccountDTO accountDTO = new AccountDAO(CompleteDriverRegristration.this).getAccountById(-1);
        new AsyncCreateAccount(CompleteDriverRegristration.this).execute(accountDTO);

       /* //to delete
        carDetailsDTO.setAccountId(1);
        new AsyncCreateCarDetails(CompleteDriverRegristration.this).execute(carDetailsDTO);*/

    }

}
