package tintintti.madonruoka.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import tintintti.madonruoka.interfaces.OnConfirmedListener;
import tintintti.madonruoka.R;
import tintintti.madonruoka.data.Info;
import tintintti.madonruoka.fragments.ConfirmDeletionFragment;

/**
 * Shows the info of an individual entry.
 */
public class ShowInfo extends AppCompatActivity implements OnConfirmedListener {
    private Info info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);

        Bundle extras = getIntent().getExtras();
        info = (Info) extras.getSerializable("info");

        setTextsToViews();

    }

    /**
     * Sets the entry's values to text views.
     */
    private void setTextsToViews() {
        TextView dateView   = (TextView) findViewById(R.id.date_info);
        TextView foodView   = (TextView) findViewById(R.id.food_info);
        TextView amountView = (TextView) findViewById(R.id.amount_info);
        TextView extraView  = (TextView) findViewById(R.id.extra_info);
        TextView ateView    = (TextView) findViewById(R.id.ate_info);

        dateView.setText(info.getDate());
        foodView.setText(info.getFoodItem());
        String amount = "" + info.getAmount();
        amountView.setText(amount);
        extraView.setText(info.getExtra());
        if (info.isAte()) {
            ateView.setText("Ate");
        } else {
            ateView.setText("Didn't eat");
        }
    }

    /**
     * Opens a DialogFragment for to confirm from user to delete the entry.
     *
     * @param view  view
     */
    public void removeInfo(View view) {
        DialogFragment dialog = new ConfirmDeletionFragment();
        dialog.show(getFragmentManager(), "dialog");
    }

    /**
     * Returns to MainActivity.
     * @param view view
     */
    public void goBack(View view) {
        finish();
    }


    /**
     * Returns to MainActivity and passes info to remove the entry.
     */
    @Override
    public void onConfirmed() {
        Intent i = new Intent();
        i.putExtra("remove", true);
        i.putExtra("info", info);
        setResult(RESULT_OK, i);
        finish();
    }

}
