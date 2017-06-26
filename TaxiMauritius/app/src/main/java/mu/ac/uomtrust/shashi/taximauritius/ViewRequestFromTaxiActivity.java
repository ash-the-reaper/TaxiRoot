package mu.ac.uomtrust.shashi.taximauritius;


import android.app.Fragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mu.ac.uomtrust.shashi.taximauritius.Async.AsyncAcceptOrRejectRequestUser;
import mu.ac.uomtrust.shashi.taximauritius.Async.AsyncGetCarDetails;
import mu.ac.uomtrust.shashi.taximauritius.DAO.CarDetailsDAO;
import mu.ac.uomtrust.shashi.taximauritius.DTO.CarDetailsDTO;
import mu.ac.uomtrust.shashi.taximauritius.DTO.ManageRequestDTO;
import mu.ac.uomtrust.shashi.taximauritius.DTO.RequestDTO;
import mu.ac.uomtrust.shashi.taximauritius.Enums.RequestStatus;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */


public class ViewRequestFromTaxiActivity extends Fragment {

    private TextView txtTo, txtFrom, txtDetails, txtPrice, txtDriverName;
    private TextView mTimePicker, datePicker;

    private Calendar requestDateTime = Calendar.getInstance();
    private RequestDTO requestDTO = new RequestDTO();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_request_from_driver, container, false);

        mTimePicker = (TextView) view.findViewById(R.id.timePicker);
        datePicker = (TextView) view.findViewById(R.id.datePicker);
        txtDetails = (TextView) view.findViewById(R.id.txtDetails);
        txtTo = (TextView) view.findViewById(R.id.txtTo);
        txtFrom = (TextView) view.findViewById(R.id.txtFrom);
        txtPrice = (TextView) view.findViewById(R.id.txtPrice);
        txtDriverName = (TextView) view.findViewById(R.id.txtDriverName);

        Button btnAcceptRequest = (Button)view.findViewById(R.id.btnAcceptRequest);
        btnAcceptRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(requestDTO.getRequestStatus() != RequestStatus.CLIENT_ACCEPTED) {
                    requestDTO.setRequestStatus(RequestStatus.CLIENT_ACCEPTED);
                    new AsyncAcceptOrRejectRequestUser(getActivity(), getFragmentManager(), true).execute(requestDTO);
                }
                else{
                    requestDTO.setRequestStatus(RequestStatus.PAID);
                    new AsyncAcceptOrRejectRequestUser(getActivity(), getFragmentManager(), true).execute(requestDTO);
                }
            }
        });

        Button btnRejectRequest = (Button)view.findViewById(R.id.btnRejectRequest);
        btnRejectRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(requestDTO.getRequestStatus() != RequestStatus.CLIENT_REJECTED) {
                    alertDelete();
                }
                else{
                    requestDTO.setRequestStatus(RequestStatus.CLIENT_REJECTED);
                    new AsyncAcceptOrRejectRequestUser(getActivity(), getFragmentManager(), true).execute(requestDTO);
                }
            }
        });

        Button btnDriverDetails = (Button)view.findViewById(R.id.btnDriverDetails);
        btnDriverDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CarDetailsDTO carDetailsDTO = new CarDetailsDTO();
                carDetailsDTO.setCarId(requestDTO.getCarId());
                new AsyncGetCarDetails(getActivity()).execute(carDetailsDTO);
            }
        });

       SharedPreferences prefs = getActivity().getSharedPreferences("TaxiMauritius", MODE_PRIVATE);

        Gson gson = new Gson();
        String gsonRequestDTO = prefs.getString("gsonRequestDTO", null);
        requestDTO = gson.fromJson(gsonRequestDTO, RequestDTO.class);

        if(requestDTO != null && requestDTO.getRequestId() != null) {

            txtFrom.setText(requestDTO.getPlaceFrom());
            txtTo.setText(requestDTO.getPlaceTo());
            txtPrice.setText(String.valueOf(requestDTO.getPrice()));
            txtDriverName.setText(requestDTO.getDriverName());

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

            if(requestDTO.getPrice() != null)
                txtPrice.setText(String.valueOf(requestDTO.getPrice()));

            if(requestDTO.getRequestStatus() == RequestStatus.CLIENT_ACCEPTED){
               /* btnAcceptRequest.setEnabled(false);
                btnAcceptRequest.setVisibility(View.GONE);*/

               btnAcceptRequest.setText("PAID");
            }
            else if(requestDTO.getRequestStatus() == RequestStatus.CLIENT_REJECTED){
                btnRejectRequest.setText("CANCEL");
            }
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

                requestDTO.setRequestStatus(RequestStatus.CLIENT_REJECTED);
                new AsyncAcceptOrRejectRequestUser(getActivity(), getFragmentManager(), true).execute(requestDTO);

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
}
