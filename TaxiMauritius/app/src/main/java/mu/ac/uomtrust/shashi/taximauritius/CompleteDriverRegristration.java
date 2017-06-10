package mu.ac.uomtrust.shashi.taximauritius;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mu.ac.uomtrust.shashi.taximauritius.Async.AsyncCreateAccount;
import mu.ac.uomtrust.shashi.taximauritius.DAO.AccountDAO;
import mu.ac.uomtrust.shashi.taximauritius.DAO.CarDetailsDAO;
import mu.ac.uomtrust.shashi.taximauritius.DTO.AccountDTO;
import mu.ac.uomtrust.shashi.taximauritius.DTO.CarDetailsDTO;

/**
 * Created by Ashwin on 05-Jun-17.
 */

public class CompleteDriverRegristration extends Activity {

    private static final int PICK_IMAGE_REQUEST = 1;
    Dialog dialog;

    EditText editTextMake, editTextPassenger, editTextYear ,editTextPlateNum;
    ImageView img1, img2, img3, img4;

    Button btnAddImage, btnNext;

    List<Boolean> listPhoto = new ArrayList<>();

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

            try {
                int size = data.getClipData().getItemCount() >4?4:data.getClipData().getItemCount();
                for(int x = 0; x<size; x++) {
                    Uri uri = data.getClipData().getItemAt(x).getUri();

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    switch (x) {
                        case 0:
                            listPhoto.add(true);
                            img1.setImageBitmap(bitmap);
                            break;
                        case 1:
                            listPhoto.add(true);
                            img2.setImageBitmap(bitmap);
                            break;
                        case 2:
                            listPhoto.add(true);
                            img3.setImageBitmap(bitmap);
                            break;
                        case 3:
                            listPhoto.add(true);
                            img4.setImageBitmap(bitmap);
                            break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        dialog.dismiss();
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
        else if(TextUtils.isEmpty(editTextPlateNum.getText().toString())) {
            Utils.showToast(CompleteDriverRegristration.this, getResources().getString(R.string.complete_registration_validation_number_of_passenger));
            validForm = false;
        }
        else if(TextUtils.isEmpty(editTextPassenger.getText().toString())){
            Utils.showToast(CompleteDriverRegristration.this, getResources().getString(R.string.complete_registration_validation_number_of_passenger));
            validForm = false;
        }
        else if(listPhoto == null || listPhoto.size() <2 || listPhoto.get(0) == false || listPhoto.get(1) == false) {
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
        dialog.setCancelable(false);

        Button btnGallery = (Button)dialog.findViewById(R.id.btnGallery);
        Button btnCamera = (Button)dialog.findViewById(R.id.btnCamera);

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

    }

}
