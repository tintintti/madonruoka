package tintintti.madonruoka.fragments;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import tintintti.madonruoka.R;
import tintintti.madonruoka.adapters.CustomArrayAdapter;
import tintintti.madonruoka.data.Entry;
import tintintti.madonruoka.data.EntryComparator;
import tintintti.madonruoka.db.EntryDataSource;
import tintintti.madonruoka.interfaces.OnListItemClickedListener;

import java.sql.SQLException;
import java.util.List;

public class ListFeedingEntries extends ListFragment {
    private EntryDataSource dataSource;
    private CustomArrayAdapter adapter;
    private String petName = "pet";
    private OnListItemClickedListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.etry_list_fragment, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListItemClickedListener) {
            listener = (OnListItemClickedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implemenet OnListItemClickedListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataSource = new EntryDataSource(getActivity());


        //Gets all the entries from the database and sets them as values for the CustomArrayAdapter
        try {
            dataSource.open();

            List<Entry> values = dataSource.getAllInfo(petName);

            System.out.println(getView());

            adapter = new CustomArrayAdapter(getActivity(), R.layout.enty_list, R.id.entry, values);
            adapter.sort(new EntryComparator());
            setListAdapter(adapter);

        } catch (SQLException e) {
            System.out.println("EntryDataSource couldn't be opened.");
        }


    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Entry entry = (Entry) getListAdapter().getItem(position);


        listener.onListItemClicked(entry);
    }

    public void addEntryToList(Entry entry) {
        adapter.add(entry);
    }

    public void removeEntryFromList(Entry entry) {
        dataSource.deleteInfo(entry);

        List<Entry> values = dataSource.getAllInfo(petName);

        adapter.clear();
        adapter.addAll(values);
        adapter.sort(new EntryComparator());
    }






}
