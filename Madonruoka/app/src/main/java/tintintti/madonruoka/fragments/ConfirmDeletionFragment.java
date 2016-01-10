package tintintti.madonruoka.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import tintintti.madonruoka.interfaces.OnConfirmedListener;

/**
 * Asks the user confirmation before deleting an entry.
 */
public class ConfirmDeletionFragment extends DialogFragment {


    /**
     * Creates the dialog for asking user confirmation.
     *
     * @param savedInstanceState    saved instance state
     * @return                      the created dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                // set dialog icon
                .setIcon(android.R.drawable.stat_notify_error)
                        // set Dialog Title
                .setTitle("Delete")
                        // Set Dialog Message
                .setMessage("Are you sure you want to delete this entry?")

                        // positive button
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ((OnConfirmedListener) getActivity()).onConfirmed();
                    }
                })
                        // negative button
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
    }
}