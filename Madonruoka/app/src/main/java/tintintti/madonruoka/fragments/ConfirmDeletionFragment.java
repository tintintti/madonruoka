package tintintti.madonruoka.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import tintintti.madonruoka.R;
import tintintti.madonruoka.data.Entry;
import tintintti.madonruoka.interfaces.OnConfirmedListener;

/**
 * Asks the user confirmation before deleting an entry.
 */
public class ConfirmDeletionFragment extends DialogFragment {

    public ConfirmDeletionFragment() {

    }

    /**
     * Creates the dialog for asking user confirmation.
     *
     * @param savedInstanceState    saved instance state
     * @return                      the created dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Entry entry = (Entry) getArguments().getSerializable("entry");

        return new AlertDialog.Builder(getActivity())
                // set dialog icon
                .setIcon(android.R.drawable.stat_notify_error)
                        // set Dialog Title
                .setTitle(R.string.delete)
                        // Set Dialog Message
                .setMessage(R.string.are_you_sure)

                        // positive button
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ((OnConfirmedListener) getActivity()).onConfirmed(entry);
                    }
                })
                        // negative button
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
    }
}