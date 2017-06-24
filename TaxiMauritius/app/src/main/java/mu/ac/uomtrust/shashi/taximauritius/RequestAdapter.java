package mu.ac.uomtrust.shashi.taximauritius;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.List;

import mu.ac.uomtrust.shashi.taximauritius.DTO.RequestDTO;
import mu.ac.uomtrust.shashi.taximauritius.Enums.RequestStatus;
import mu.ac.uomtrust.shashi.taximauritius.Enums.UserRole;

import static android.R.attr.fragment;
import static android.R.attr.key;
import static android.R.attr.value;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ashwin on 18-Jun-17.
 */

public class RequestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<RequestDTO> requestDTOList;
    private Context context;
    private FragmentManager fragmentManager;
    private boolean history;
    private UserRole userRole;

    public RequestAdapter (Context context, List<RequestDTO> requestDTOList, FragmentManager fragmentManager, boolean history, UserRole userRole){
        this.requestDTOList = requestDTOList;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.history = history;
        this.userRole = userRole;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_requests, parent, false);
        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RequestDTO requestDTO = requestDTOList.get(position);
        RequestViewHolder  requestViewHolder = (RequestViewHolder)holder;
        requestViewHolder.txtFrom.setText(requestDTO.getPlaceFrom());
        requestViewHolder.txtTo.setText(requestDTO.getPlaceTo());

        String sDate = null, sTime = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMMM-yyyy");
            sDate = simpleDateFormat.format(requestDTO.getEvenDateTime());

            SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("H : mm");
            sTime = simpleTimeFormat.format(requestDTO.getEvenDateTime());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        requestViewHolder.txtDate.setText(sDate);
        requestViewHolder.txtTime.setText(sTime);

        if(!history) {
            requestViewHolder.llMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = context.getSharedPreferences("TaxiMauritius", MODE_PRIVATE).edit();
                    Gson gson = new Gson();
                    String gsonRequestDTO = gson.toJson(requestDTO);
                    editor.putString("gsonRequestDTO", gsonRequestDTO);
                    editor.commit();

                    if(userRole == UserRole.USER) {
                        if(requestDTO.getRequestStatus().equals(RequestStatus.REQUEST_PENDING)) {
                            fragmentManager
                                    .beginTransaction()
                                    .replace(R.id.details, new ViewRequestActivity())
                                    .commit();
                        }
                        else if(requestDTO.getRequestStatus().equals(RequestStatus.TAXI_DRIVER_ACCEPTED)){
                            fragmentManager
                                    .beginTransaction()
                                    .replace(R.id.details, new ViewRequestFromTaxiActivity())
                                    .commit();
                        }
                    }
                    else{
                        fragmentManager
                                .beginTransaction()
                                .replace(R.id.details, new ViewRequestTaxiActivity())
                                .commit();
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return requestDTOList.size();
    }


    public class RequestViewHolder extends RecyclerView.ViewHolder {
        public TextView txtFrom, txtTo, txtDate, txtTime;
        public LinearLayout llMain;

        public RequestViewHolder(View view) {
            super(view);
            txtFrom = (TextView) view.findViewById(R.id.txtFrom);
            txtTo = (TextView) view.findViewById(R.id.txtTo);
            txtDate = (TextView) view.findViewById(R.id.txtDate);
            txtTime = (TextView) view.findViewById(R.id.txtTime);
            llMain = (LinearLayout) view.findViewById(R.id.llMain);
        }
    }

    public void setRequestDTOList(List<RequestDTO> requestDTOList) {
        this.requestDTOList = requestDTOList;
    }
}
