package com.davidllorca.dialogexample;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends Activity {

    private static final int SIMPLE_DIALOG = 0;
    private static final int DETAILED_PROGRESS_DIALOG = 2;
    private static final int TIMEPICKER_DIALOG = 3;

    String[] items = {"Google", "Apple", "Microsoft"};
    boolean[] itemsChecked = new boolean[items.length];

    ProgressDialog progressDialog;

    int hour, min;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour = hourOfDay;
            min = minute;
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
            Date date = new Date(0, 0, 0, hourOfDay, minute);
            String strDate = timeFormat.format(date);

            Toast.makeText(getBaseContext(), "You have selected " + strDate, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        showDialog(SIMPLE_DIALOG);
    }

    public void onClick2(View view) {
        final ProgressDialog progressDialog = ProgressDialog.show(this, "Loading", "Please wait...", true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Simulate waiting
                    Thread.sleep(5000);
                    // Close dialog
                    progressDialog.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void onClick3(View view) {
        showDialog(DETAILED_PROGRESS_DIALOG);
        progressDialog.setProgress(0);

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 15; i++) {
                    try {
                        Log.d("onClick3", "on thread");
                        // Simulate doing somthig
                        Thread.sleep(1000);
                        progressDialog.incrementProgressBy((int) (100 / 15));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                progressDialog.dismiss();
            }
        }).start();
    }

    public void onClick4(View view) {
        showDialog(TIMEPICKER_DIALOG);
    }

    public void onClick5(View view) {
        DialogFragmentExample dialogFragment = DialogFragmentExample.newInstance("This is a DialogFragment");
        dialogFragment.show(getFragmentManager(), "dialog fragment");
    }

    public void doPositiveClick(){
        Toast.makeText(getBaseContext(), "Ok clicked", Toast.LENGTH_SHORT).show();
    }

    public void doNegativeClick(){
        Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case SIMPLE_DIALOG:
                return new AlertDialog.Builder(this)
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle("This is a dialog with some simple text")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getBaseContext(), "Ok clicked", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                            }
                        }).setMultiChoiceItems(items, itemsChecked, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                Toast.makeText(getBaseContext(), items[which] + (isChecked ? " checked!" : " unchecked!"), Toast.LENGTH_SHORT).show();
                            }
                        }).create();
            case DETAILED_PROGRESS_DIALOG:
                progressDialog = new ProgressDialog(this);
                progressDialog.setIcon(R.mipmap.ic_launcher);
                progressDialog.setTitle("Downloading file...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getBaseContext(), "Ok clicked", Toast.LENGTH_SHORT).show();
                    }
                });
                progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();

                    }
                });
                return progressDialog;
            case TIMEPICKER_DIALOG:
                return new TimePickerDialog(this, mTimeSetListener, hour, min, false);
        }
        return null;
    }


}
