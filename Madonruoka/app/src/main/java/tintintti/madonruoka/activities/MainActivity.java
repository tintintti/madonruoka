package tintintti.madonruoka.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import tintintti.madonruoka.adapters.CustomArrayAdapter;
import tintintti.madonruoka.data.InfoComparator;
import tintintti.madonruoka.R;
import tintintti.madonruoka.data.Info;
import tintintti.madonruoka.db.InfoDataSource;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends ListActivity {
    private InfoDataSource dataSource;
    private CustomArrayAdapter<Info> adapter;
    private static final int REQUEST_CODE_ADD = 1;
    private static final int REQUEST_CODE_EDIT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataSource = new InfoDataSource(this);

        /*
        Gets all the entries from the database and sets them as values for the CustomArrayAdapter
        which is set as the ListAdapter
         */
        try {
            dataSource.open();
            List<Info> values = dataSource.getAllInfo();

            adapter = new CustomArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
            adapter.sort(new InfoComparator());
            setListAdapter(adapter);

        } catch (SQLException e) {
            System.out.println("InfoDataSource couldn't be opened.");
        }


    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Info info = (Info) getListAdapter().getItem(position);

        Intent i = new Intent(this, ShowInfo.class);
        i.putExtra("info", info);
        System.out.println(info.getId());
        startActivityForResult(i, REQUEST_CODE_EDIT);
    }

    public void addInfo(View view) {
        Intent i = new Intent(this, AddFeedingInfo.class);
        startActivityForResult(i, REQUEST_CODE_ADD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_ADD && data.hasExtra("info")) {
            Info info = (Info) data.getSerializableExtra("info");
            if (info != null) {
                adapter.add(info);
            }
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_EDIT
                && data.hasExtra("remove")) {

            if (data.getBooleanExtra("remove", false)) {
                Info info = (Info) data.getSerializableExtra("info");
                dataSource.deleteInfo(info);

                List<Info> values = dataSource.getAllInfo();

                adapter.clear();
                adapter.addAll(values);
                adapter.sort(new InfoComparator());
            }
        }
    }


}
