package mu.ac.uomtrust.shashi.taximauritius;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */


public class CreateRequestActivity extends Fragment {

    private AutoCompleteTextView autoCompleteFrom, autoCompleteTo;
    private EditText timePicker, datePicker;
    private String[] places;

    private boolean currentDate = false;
    private boolean validTime = false;
    private Calendar requestDateTime = Calendar.getInstance();

    private int selectedYear, selectecMonth, selectedDayOfMonth, gHour,gMin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_request, container, false);

        timePicker = (EditText) view.findViewById(R.id.timePicker);
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentTime = Calendar.getInstance();
                final int mHour = currentTime.get(Calendar.HOUR_OF_DAY);
                final int mMinute = currentTime.get(Calendar.MINUTE);
                final TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {


                    }
                }, mHour, mMinute, false);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();

            }
        });

        datePicker = (EditText) view.findViewById(R.id.datePicker);
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

                        requestDateTime.set(selectedYear, Calendar.YEAR);
                        requestDateTime.set(selectecMonth, Calendar.MONTH);
                        requestDateTime.set(selectedDayOfMonth, Calendar.DAY_OF_MONTH);
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

        return view;
    }

}
