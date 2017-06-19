package mu.ac.uomtrust.shashi.taximauritius;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import mu.ac.uomtrust.shashi.taximauritius.Async.AsyncGetRequest;
import mu.ac.uomtrust.shashi.taximauritius.DAO.AccountDAO;
import mu.ac.uomtrust.shashi.taximauritius.DAO.RequestDAO;
import mu.ac.uomtrust.shashi.taximauritius.DTO.AccountDTO;
import mu.ac.uomtrust.shashi.taximauritius.DTO.RequestDTO;
import mu.ac.uomtrust.shashi.taximauritius.Enums.RequestStatus;
import mu.ac.uomtrust.shashi.taximauritius.Enums.UserRole;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */


public class ManageRequestActivity extends Fragment {

    RequestAdapter requestAdapter = null;
    List<RequestDTO> requestDTOList = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.manage_request, container, false);

        Spinner spinnerRequestType = (Spinner)view.findViewById(R.id.spinnerRequestType);

        SharedPreferences prefs = getActivity().getSharedPreferences("TaxiMauritius", MODE_PRIVATE);
        Integer accountId = prefs.getInt("accountId", 1);

        AccountDTO accountDTO = new AccountDAO(getActivity()).getAccountById(accountId);

        int resourceId;
        if(accountDTO.getRole() == UserRole.TAXI_DRIVER){
            resourceId = R.array.manage_request_taxi_arrays;
        }
        else
            resourceId = R.array.manage_request_arrays;


        final ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),resourceId,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRequestType.setAdapter(adapter);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvPendingList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        FragmentManager fragmentManager = getFragmentManager();


        requestDTOList = new ArrayList<>();
        requestAdapter = new RequestAdapter(getActivity(), requestDTOList, fragmentManager, false);
        recyclerView.setAdapter(requestAdapter);

        final RequestDTO requestDTO = new RequestDAO(getActivity()).getOneRequest();

        spinnerRequestType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0 && requestDTO != null && requestDTO.getRequestId() != null) {
                    requestDTO.setRequestStatus(RequestStatus.REQUEST_PENDING);
                    new AsyncGetRequest(getActivity(), requestAdapter).execute(requestDTO);
                } else if(requestDTO != null && requestDTO.getRequestId() != null){
                    requestDTO.setRequestStatus(RequestStatus.TAXI_DRIVER_ACCEPTED);
                    new AsyncGetRequest(getActivity(), requestAdapter).execute(requestDTO);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }
}
