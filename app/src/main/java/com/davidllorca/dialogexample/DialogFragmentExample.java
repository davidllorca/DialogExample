package com.davidllorca.dialogexample;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * DialogFragment example.
 * <p/>
 * Created by David Llorca <davidllorcabaron@gmail.com> on 9/7/15.
 */
public class DialogFragmentExample extends android.app.DialogFragment {

    static DialogFragmentExample newInstance(String title) {
        DialogFragmentExample fragment = new DialogFragmentExample();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        return new AlertDialog.Builder(getActivity())
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ((MainActivity) getActivity()).doPositiveClick();
                            }
                        })
                .setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ((MainActivity) getActivity()).doNegativeClick();
                            }
                        }).create();
    }
}
