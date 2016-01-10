package tintintti.madonruoka.fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import java.util.Calendar;

/**
 * Fragment for picking a date for the entry.
 */
public class DatePickerFragment extends DialogFragment {


    public DatePickerFragment() {
        // Required empty public constructor
    }

    /**
     * Creates the date picker dialog, sets it to current date and disables future dates.
     *
     * @param savedInstanceState    saved instance date
     * @return                      created DatePickerDialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();

        // sets current date to date picker
        int year  = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day   = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);

        // disables future dates from picker
        dpd.getDatePicker().setMaxDate(calendar.getTimeInMillis());

        return dpd;
    }


}
