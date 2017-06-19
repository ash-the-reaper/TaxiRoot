package mu.ac.uomtrust.shashi.taximauritius;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.maps.MapFragment;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mu.ac.uomtrust.shashi.taximauritius.Async.AsyncCreateOrUpdateRequest;
import mu.ac.uomtrust.shashi.taximauritius.DTO.RequestDTO;
import mu.ac.uomtrust.shashi.taximauritius.Enums.RequestStatus;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */


public class CreateRequestActivity extends Fragment {

    private AutoCompleteTextView autoCompleteFrom, autoCompleteTo;
    private TextView mTimePicker, datePicker;
    private EditText editTextDetails;
    private String[] places;

    private Calendar requestDateTime = Calendar.getInstance();
    private RequestDTO requestDTO = new RequestDTO();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_request, container, false);

        mTimePicker = (TextView) view.findViewById(R.id.timePicker);
        mTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentTime = Calendar.getInstance();
                final int mHour = currentTime.get(Calendar.HOUR_OF_DAY);
                final int mMinute = currentTime.get(Calendar.MINUTE);
                final TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        String min =  String.valueOf(selectedMinute).length() <2? "0"+String.valueOf(selectedMinute):String.valueOf(selectedMinute);

                        mTimePicker.setText(String.valueOf(selectedHour)+" : "+min);

                        requestDateTime.set(Calendar.HOUR_OF_DAY, selectedHour);
                        requestDateTime.set(Calendar.MINUTE, selectedMinute);

                        Utils.hideKeyboard(getActivity());
                    }
                }, mHour, mMinute, false);

                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });


        datePicker = (TextView) view.findViewById(R.id.datePicker);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                Calendar currentTime = Calendar.getInstance();
                final int mMonth = currentTime.get(Calendar.MONTH);
                final int mYear = currentTime.get(Calendar.YEAR);
                final int mDay = currentTime.get(Calendar.DAY_OF_MONTH);
                final DatePickerDialog datePickerDialogue;
                datePickerDialogue = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {

                        requestDateTime.set(Calendar.YEAR, year);
                        requestDateTime.set(Calendar.MONTH, monthOfYear);
                        requestDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        datePicker.setText(String.valueOf(dayOfMonth) +" "+ new DateFormatSymbols().getMonths()[monthOfYear-1]);

                        Utils.hideKeyboard(getActivity());

                    }
                }, mYear, mMonth, mDay);

                datePickerDialogue.getDatePicker().setMinDate(currentTime.getTimeInMillis());
                datePickerDialogue.setTitle("Select Date");
                datePickerDialogue.show();
            }
        });

        autoCompleteFrom = (AutoCompleteTextView)view.findViewById(R.id.autoCompleteFrom);
        autoCompleteTo = (AutoCompleteTextView)view.findViewById(R.id.autoCompleteTo);

        places = getResources().getStringArray(R.array.address_arrays);
        ArrayAdapter<String> adapterFrom = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,places);
        autoCompleteFrom.setAdapter(adapterFrom);

        ArrayAdapter<String> adapterTo = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,places);
        autoCompleteTo.setAdapter(adapterTo);

        editTextDetails = (EditText)view.findViewById(R.id.editTextDetails);

        Button btnCreateRequest = (Button)view.findViewById(R.id.btnCreateRequest);
        btnCreateRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validForm()){
                   new AsyncCreateOrUpdateRequest(getActivity(), getFragmentManager(), true).execute(requestDTO);
                }
            }
        });

        Button btnCancelRequest = (Button)view.findViewById(R.id.btnCancelRequest);
        btnCancelRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertCancel();
            }
        });


        return view;
    }

    private void alertCancel() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this.getActivity());

        // Setting Dialog Title
        alertDialog.setTitle("Confirm Delete...");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want delete this?");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.details, new MapFragment())
                        .commit();
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
        boolean validAddressFrom = false;
        boolean validAddressTo= false;

        if(autoCompleteFrom.getText() == null){
            Utils.showToast(this.getActivity(), getResources().getString(R.string.create_request_activity_validation_autocomplete_address));
            validForm = false;
        }
        else if(autoCompleteTo.getText() == null ){
            Utils.showToast(this.getActivity(), getResources().getString(R.string.create_request_activity_validation_autocomplete_address));
            validForm = false;
        }
        else if(datePicker.getText() == null ){
            Utils.showToast(this.getActivity(), getResources().getString(R.string.create_request_activity_validation_autocomplete_date));
            validForm = false;
        }
        else if(mTimePicker.getText() == null ){
            Utils.showToast(this.getActivity(), getResources().getString(R.string.create_request_activity_validation_autocomplete_time));
            validForm = false;
        } else if(editTextDetails.getText() == null ){
            Utils.showToast(this.getActivity(), getResources().getString(R.string.create_request_activity_validation_autocomplete_details));
            validForm = false;
        }

        String addressFrom = autoCompleteFrom.getText().toString();
        for(int x = 0; x < places.length; x++){
            if(addressFrom.equalsIgnoreCase(places[x])){
                validAddressFrom = true;
            }
        }

        String addressTo = autoCompleteTo.getText().toString();
        for(int x = 0; x < places.length; x++){
            if(addressTo.equalsIgnoreCase(places[x])){
                validAddressTo = true;
            }
        }

        if(validForm && !validAddressFrom){
            Utils.showToast(this.getActivity(), getResources().getString(R.string.create_request_activity_validation_autocomplete_address_no_match));
        }
        else if(validForm && !validAddressTo){
            Utils.showToast(this.getActivity(), getResources().getString(R.string.create_request_activity_validation_autocomplete_address_no_match));
        }

        if(validForm&&validAddressFrom&&validAddressTo){
            SharedPreferences prefs = getActivity().getSharedPreferences("TaxiMauritius", MODE_PRIVATE);
            int accountId = prefs.getInt("accountId", 1);
            requestDTO.setAccountId(accountId);

            requestDTO.setPlaceFrom(autoCompleteFrom.getText().toString());
            requestDTO.setPlaceTo(autoCompleteTo.getText().toString());
            requestDTO.setDetails(editTextDetails.getText().toString());
            requestDTO.setEvenDateTime(requestDateTime.getTime());
            requestDTO.setRequestStatus(RequestStatus.REQUEST_PENDING);

            Date date = new Date();
            requestDTO.setDateCreated(date);
            requestDTO.setDateUpdated(date);
        }

        return validForm&&validAddressFrom&&validAddressTo;
    }

}
