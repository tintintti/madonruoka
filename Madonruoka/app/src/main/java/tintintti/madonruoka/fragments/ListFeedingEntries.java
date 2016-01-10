package tintintti.madonruoka.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import tintintti.madonruoka.adapters.CustomArrayAdapter;
import tintintti.madonruoka.data.EntryComparator;
import tintintti.madonruoka.data.Entry;
import tintintti.madonruoka.db.EntryDataSource;
import tintintti.madonruoka.interfaces.OnListItemClickedListener;

import java.sql.SQLException;
import java.util.List;

public class ListFeedingEntries extends ListFragment {
    private EntryDataSource dataSource;
    private CustomArrayAdapter<Entry> adapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataSource = new EntryDataSource(getActivity());

        /*
        Gets all the entries from the database and sets them as values for the CustomArrayAdapter
        which is set as the ListAdapter
         */
        try {
            dataSource.open();

            List<Entry> values = dataSource.getAllInfo();


            adapter = new CustomArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, values);
            adapter.sort(new EntryComparator());
            setListAdapter(adapter);

        } catch (SQLException e) {
            System.out.println("EntryDataSource couldn't be opened.");
        }


    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Entry info = (Entry) getListAdapter().getItem(position);

        OnListItemClickedListener listener = (OnListItemClickedListener) getActivity();
        listener.onListItemClicked(info);
    }

    public void addEntryToList(Entry info) {
        adapter.add(info);
    }

    public void removeEntryFromList(Entry info) {
        dataSource.deleteInfo(info);

        List<Entry> values = dataSource.getAllInfo();

        adapter.clear();
        adapter.addAll(values);
        adapter.sort(new EntryComparator());
    }






}
