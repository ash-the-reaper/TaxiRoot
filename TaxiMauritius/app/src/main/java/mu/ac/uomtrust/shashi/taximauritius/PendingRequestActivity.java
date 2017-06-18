package mu.ac.uomtrust.shashi.taximauritius;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mu.ac.uomtrust.shashi.taximauritius.DAO.RequestDAO;
import mu.ac.uomtrust.shashi.taximauritius.DTO.RequestDTO;
import mu.ac.uomtrust.shashi.taximauritius.Enums.RequestStatus;


/**
 * A simple {@link Fragment} subclass.
 */


public class PendingRequestActivity extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.pending_request, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvPendingList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        FragmentManager fragmentManager = getFragmentManager();

        List<RequestDTO> requestDTOList = new RequestDAO(getActivity()).getRequestByStatus(RequestStatus.REQUEST_PENDING);
        RequestAdapter requestAdapter = new RequestAdapter(getActivity(), requestDTOList, fragmentManager);
        recyclerView.setAdapter(requestAdapter);


        return view;
    }




}
