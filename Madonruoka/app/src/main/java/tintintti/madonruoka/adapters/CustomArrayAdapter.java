package tintintti.madonruoka.adapters;

import tintintti.madonruoka.data.Entry;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import tintintti.madonruoka.R;

import tintintti.madonruoka.data.EntryComparator;

import java.util.List;

/**
 * Custom ArrayAdapter to sort entries after adding one.
 */
public class CustomArrayAdapter extends ArrayAdapter<Entry> {
    private final Context context;
    private final List<Entry> values;


    public CustomArrayAdapter(Context context, int resource, int textViewResourceId, List<Entry> entries) {
        super(context, resource, textViewResourceId, entries);
        this.context = context;
        this.values = entries;
    }

    @Override
    public void add(Entry entry) {
        super.add(entry);
        super.sort(new EntryComparator());
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View      listView  = inflater.inflate(R.layout.enty_list, parent, false);
        TextView  textView  = (TextView) listView.findViewById(R.id.entry);
        ImageView imageView = (ImageView) listView.findViewById(R.id.img);

        Entry entry = values.get(position);

        textView.setText(entry.toString());

        if (!entry.isAte()) {
            imageView.setImageResource(R.drawable.ic_clear_24dp);
        }

        return listView;
    }

}
