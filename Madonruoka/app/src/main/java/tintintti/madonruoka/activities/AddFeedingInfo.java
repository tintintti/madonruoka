package tintintti.madonruoka.activities;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import tintintti.madonruoka.R;
import tintintti.madonruoka.data.Entry;
import tintintti.madonruoka.db.EntryDataSource;
import tintintti.madonruoka.fragments.DatePickerFragment;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Activity for adding an entry.
 */
public class AddFeedingInfo extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EntryDataSource dataSource;
    private Entry entry;
    private TextView dateView;
    private Calendar calendar;
    private SimpleDateFormat format;
    private String unitOfMeasure = "pcs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feeding_info);

        calendar = Calendar.getInstance();
        setCurrentDateToDateView();

        dataSource = new EntryDataSource(this);

    }

    private void setCurrentDateToDateView() {
        dateView   = (TextView) findViewById(R.id.date);

        format = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        dateView.setText(format.format(calendar.getTimeInMillis()));
    }

    /**
     * Shows a DatePickerFragment for choosing a date for the entry.
     * @param view
     */
    public void setDate(View view) {
        DialogFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getFragmentManager(), "dialog");
    }


    /**
     * Adds an entry to the database and returns to ListFeedingEntries
     * @param view
     */
    public void add(View view) {
        EditText foodView   = (EditText) findViewById(R.id.food);
        EditText amountView = (EditText) findViewById(R.id.amount);
        CheckBox ateView    = (CheckBox) findViewById(R.id.ate);
        EditText extraView  = (EditText) findViewById(R.id.extra);

        String date = dateView.getText().toString();
        String food = foodView.getText().toString();
        double amount = 0;

        if (!amountView.getText().toString().equals("")) {
            amount = Double.parseDouble(amountView.getText().toString());
        }

        boolean ate = ateView.isChecked();
        String extra = extraView.getText().toString();

        try {
            dataSource.open();
            entry = dataSource.createInfo(date, food, amount, ate, extra, unitOfMeasure);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        finish();
    }

    public void cancel(View view) {
        super.finish();
    }

    /**
     * Puts the added entry to Intent and returns it to ListFeedingEntries
     */
    @Override
    public void finish(){
        Intent i = new Intent();
        i.putExtra("entry", entry);
        setResult(RESULT_OK, i);
        super.finish();
    }

    /**
     * Sets the given date to the date view.
     *
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     */
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth);
        dateView.setText(format.format(c.getTimeInMillis()));
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.radio_pcs:
                if(checked)
                    unitOfMeasure = "pcs";
                break;
            case R.id.radio_g:
                if(checked)
                    unitOfMeasure = "g";
                break;
        }
    }
}
