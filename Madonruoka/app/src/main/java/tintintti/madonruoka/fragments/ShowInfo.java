package tintintti.madonruoka.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import tintintti.madonruoka.data.Entry;
import tintintti.madonruoka.R;
import tintintti.madonruoka.interfaces.ShowInfoFragmentListener;

/**
 * Shows the entry of an individual entry.
 */
public class ShowInfo extends Fragment {
    private Entry entry;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_show_info, container, false);

        Bundle extras = getArguments();
        entry = (Entry) extras.getSerializable("entry");

        setTextsToViews(view);

        Button delete = (Button) view.findViewById(R.id.remove);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowInfoFragmentListener listener = (ShowInfoFragmentListener) getActivity();
                listener.onRemoveEntryClicked(entry);
            }
        });

        Button back = (Button) view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowInfoFragmentListener listener = (ShowInfoFragmentListener) getActivity();
                listener.backToList();
            }
        });

        return view;
    }



    /**
     * Sets the entry's values to text views.
     */
    private void setTextsToViews(View view) {

        TextView  dateView   = (TextView) view.findViewById(R.id.date_info);
        TextView  foodView   = (TextView) view.findViewById(R.id.food_info);
        TextView  amountView = (TextView) view.findViewById(R.id.amount_info);
        TextView  extraView  = (TextView) view.findViewById(R.id.extra_info);
        ImageView ateView    = (ImageView) view.findViewById(R.id.ate_info);

        dateView.setText(entry.getDate());
        foodView.setText(entry.getFoodItem());
        String amount = "" + entry.getAmount();
        amountView.setText(amount);
        extraView.setText(entry.getExtra());
        if (entry.isAte()) {
            ateView.setImageResource(R.drawable.ic_done_24dp);
        } else {
            ateView.setImageResource(R.drawable.ic_clear_24dp);
        }
    }



}
