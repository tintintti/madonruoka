package tintintti.madonruoka.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import tintintti.madonruoka.data.InfoComparator;

import java.util.List;

/**
 * Custom ArrayAdapter to sort entries after adding one.
 */
public class CustomArrayAdapter<Info> extends ArrayAdapter {


    public CustomArrayAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    @Override
    public void add(Object o) {
        Info info = (Info) o;
        super.add(info);
        super.sort(new InfoComparator());
    }

}
