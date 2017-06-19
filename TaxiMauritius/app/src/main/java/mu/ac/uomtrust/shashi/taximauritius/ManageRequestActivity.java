package mu.ac.uomtrust.shashi.taximauritius;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

import mu.ac.uomtrust.shashi.taximauritius.DAO.RequestDAO;
import mu.ac.uomtrust.shashi.taximauritius.DTO.RequestDTO;
import mu.ac.uomtrust.shashi.taximauritius.Enums.RequestStatus;


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
        final ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),R.array.manage_request_arrays,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRequestType.setAdapter(adapter);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvPendingList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        FragmentManager fragmentManager = getFragmentManager();

        requestDTOList = new RequestDAO(getActivity()).getRequestByStatus(RequestStatus.REQUEST_PENDING);
        requestAdapter = new RequestAdapter(getActivity(), requestDTOList, fragmentManager);
        recyclerView.setAdapter(requestAdapter);


        spinnerRequestType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                    requestDTOList = new RequestDAO(getActivity()).getRequestByStatus(RequestStatus.REQUEST_PENDING);

                else
                    requestDTOList = new RequestDAO(getActivity()).getRequestByStatus(RequestStatus.TAXI_DRIVER_ACCEPTED);

                requestAdapter.setRequestDTOList(requestDTOList);
                requestAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }
}
