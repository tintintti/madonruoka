package tintintti.madonruoka.activities;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import tintintti.madonruoka.R;
import tintintti.madonruoka.data.Entry;
import tintintti.madonruoka.db.EntryDataSource;
import tintintti.madonruoka.fragments.ConfirmDeletionFragment;
import tintintti.madonruoka.fragments.ListFeedingEntries;
import tintintti.madonruoka.fragments.ShowInfo;
import tintintti.madonruoka.interfaces.OnConfirmedListener;
import tintintti.madonruoka.interfaces.OnListItemClickedListener;
import tintintti.madonruoka.interfaces.ShowInfoFragmentListener;

public class MainActivity extends AppCompatActivity implements OnListItemClickedListener,
        OnConfirmedListener, ShowInfoFragmentListener {

    private EntryDataSource dataSource;
    private ListFeedingEntries listFragment;
    private FragmentManager fragmentManager;
    private static final int REQUEST_CODE_ADD = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dataSource = new EntryDataSource(this);

        listFragment = new ListFeedingEntries();

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, listFragment).commit();
        }

        fragmentManager = getFragmentManager();

    }

    public void addInfo(View view) {
        Intent i = new Intent(this, AddFeedingInfo.class);
        startActivityForResult(i, REQUEST_CODE_ADD);
    }

    @Override
    public void onListItemClicked(Entry entry) {

        ShowInfo show = new ShowInfo();
        Bundle args = new Bundle();
        args.putSerializable("entry", entry);

        show.setArguments(args);

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.fragment_container, show);
        transaction.addToBackStack(null);
        transaction.commit();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_ADD && data.hasExtra("entry")) {
            Entry entry = (Entry) data.getSerializableExtra("entry");
            if (entry != null) {
                listFragment.addEntryToList(entry);
            }
        }
    }

    @Override
    public void onConfirmed(Entry entry) {
        listFragment.removeEntryFromList(entry);
        backToList();
    }

    @Override
    public void onRemoveEntryClicked(Entry entry) {
        Bundle args = new Bundle();
        args.putSerializable("entry", entry);
        DialogFragment dialog = new ConfirmDeletionFragment();
        dialog.setArguments(args);
        dialog.show(getFragmentManager(), "dialog");
    }

    @Override
    public void backToList() {
        fragmentManager.popBackStack();
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() != 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }


}
