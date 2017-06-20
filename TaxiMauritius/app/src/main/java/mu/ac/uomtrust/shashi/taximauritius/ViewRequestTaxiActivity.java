package mu.ac.uomtrust.shashi.taximauritius;


import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mu.ac.uomtrust.shashi.taximauritius.Async.AsyncCreateOrUpdateRequest;
import mu.ac.uomtrust.shashi.taximauritius.Async.AsyncDeleteRequest;
import mu.ac.uomtrust.shashi.taximauritius.DAO.CarDetailsDAO;
import mu.ac.uomtrust.shashi.taximauritius.DTO.CarDetailsDTO;
import mu.ac.uomtrust.shashi.taximauritius.DTO.ManageRequestDTO;
import mu.ac.uomtrust.shashi.taximauritius.DTO.RequestDTO;
import mu.ac.uomtrust.shashi.taximauritius.Enums.RequestStatus;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */


public class ViewRequestTaxiActivity extends Fragment {

    private TextView txtTo, txtFrom, txtDetails;
    private EditText editTextPrice;
    private TextView mTimePicker, datePicker;

    private Calendar requestDateTime = Calendar.getInstance();
    private RequestDTO requestDTO = new RequestDTO();
    private ManageRequestDTO manageRequestDTO = new ManageRequestDTO();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_request_taxi, container, false);

        mTimePicker = (TextView) view.findViewById(R.id.timePicker);
        datePicker = (TextView) view.findViewById(R.id.datePicker);
        txtDetails = (TextView) view.findViewById(R.id.txtDetails);
        txtTo = (TextView) view.findViewById(R.id.txtTo);
        txtFrom = (TextView) view.findViewById(R.id.txtFrom);
        editTextPrice = (EditText) view.findViewById(R.id.editTextPrice);

        Button btnAcceptRequest = (Button)view.findViewById(R.id.btnAcceptRequest);
        btnAcceptRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validForm()){
                   //new AsyncCreateOrUpdateRequest(getActivity(), getFragmentManager(), false).execute(requestDTO);
                    Utils.showToast(getActivity(), "Test");
                }
            }
        });

        Button btnRejectRequest = (Button)view.findViewById(R.id.btnRejectRequest);
        btnRejectRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDelete();
            }
        });


       SharedPreferences prefs = getActivity().getSharedPreferences("TaxiMauritius", MODE_PRIVATE);

        Gson gson = new Gson();
        String gsonRequestDTO = prefs.getString("gsonRequestDTO", null);
        requestDTO = gson.fromJson(gsonRequestDTO, RequestDTO.class);

        if(requestDTO != null && requestDTO.getRequestId() != null) {

            txtFrom.setText(requestDTO.getPlaceFrom());
            txtTo.setText(requestDTO.getPlaceTo());

            String sDate = null, sTime = null;
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMMM");
                sDate = simpleDateFormat.format(requestDTO.getEvenDateTime());

                SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("H : mm");
                sTime = simpleTimeFormat.format(requestDTO.getEvenDateTime());
            } catch (Exception e) {
                e.printStackTrace();
            }

            datePicker.setText(sDate);
            mTimePicker.setText(sTime);

            txtDetails.setText(requestDTO.getDetails());

            requestDateTime.setTime(requestDTO.getEvenDateTime());
        }

        return view;
    }

    private void alertDelete() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this.getActivity());

        // Setting Dialog Title
        alertDialog.setTitle("Confirm Delete...");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want delete this?");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {

                //new AsyncDeleteRequest(getActivity(), getFragmentManager()).execute(requestDTO);

                dialog.cancel();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    private boolean validForm(){
        boolean validForm = true;

        if(TextUtils.isEmpty(editTextPrice.getText())){
            Utils.showToast(this.getActivity(), getResources().getString(R.string.create_request_activity_validation_autocomplete_price));
            validForm = false;
        }
        else{
            manageRequestDTO.setRequestId(requestDTO.getRequestId());
            manageRequestDTO.setAccountId(requestDTO.getAccountId());
            manageRequestDTO.setRequestStatus(RequestStatus.TAXI_DRIVER_ACCEPTED);
            manageRequestDTO.setPrice(Integer.parseInt(editTextPrice.getText().toString()));

            Integer carId = new CarDetailsDAO(getActivity()).getCarDetailsByAccountID(requestDTO.getAccountId()).getCarId();

            manageRequestDTO.setCarId(carId);

            Date date = new Date();
            manageRequestDTO.setDateCreated(date);
            manageRequestDTO.setDateUpdated(date);
        }

        return validForm;
    }

}
