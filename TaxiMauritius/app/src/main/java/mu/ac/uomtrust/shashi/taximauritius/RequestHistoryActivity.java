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


public class RequestHistoryActivity extends Fragment {

    RequestAdapter requestAdapter = null;
    List<RequestDTO> requestDTOList = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.request_history, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvPendingList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        FragmentManager fragmentManager = getFragmentManager();

        SharedPreferences prefs = getActivity().getSharedPreferences("TaxiMauritius", MODE_PRIVATE);
        Integer accountId = prefs.getInt("accountId", 1);
        AccountDTO accountDTO = new AccountDAO(getActivity()).getAccountById(accountId);
        UserRole userRole = accountDTO.getRole();

        requestDTOList = new ArrayList<>();
        requestAdapter = new RequestAdapter(getActivity(), requestDTOList, fragmentManager, true, userRole);
        recyclerView.setAdapter(requestAdapter);

        RequestDTO requestDTO = new RequestDAO(getActivity()).getOneRequest();
        requestDTO.setRequestStatus(RequestStatus.PAID);

        if(requestDTO.getAccountId() == null){

            requestDTO.setAccountId(accountId);
        }

        new AsyncGetRequest(getActivity(), requestAdapter).execute(requestDTO);

        return view;
    }
}
