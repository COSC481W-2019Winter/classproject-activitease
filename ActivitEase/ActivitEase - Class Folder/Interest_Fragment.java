package com.example.activitease;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;
import android.widget.EditText;
import android.os.AsyncTask;
import android.widget.Toast;

public class Interest_Fragment extends Fragment {
    // EditText interestName, periodFrequency, basePeriodSpan, activityLength, numNotifications;
    MyGLSurfaceView glSurfaceView;
    boolean timerRunning;
    private static final long START_TIME_MILLIS = 600000;
    private long mTimeLeftInMillis = START_TIME_MILLIS;

    private TextView textViewCountdown;
    private CountDownTimer countDownTimer;
    private EditText delInterestName;

    Button delete, editInterestBn;

    private EditText interestName, activityLength, numNotifications, periodFreq;
    private Spinner periodSpanInput;


    private String iName;
    private int aLength, numNotif, pSpanPtr, pFreq;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.interest_page, container, false);
        TextView mytextview = view.findViewById(R.id.EditInterestName);
        final Interest theInterest = new Interest();
        mytextview.setText(theInterest.getInterestName());
        String[] periodSpanTypes =
                {"Day", "Week", "Month", "Year"};

        // Builds the period Span Spinner.
        periodSpanInput = (Spinner) view.findViewById(R.id.periodSpanInput);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, periodSpanTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        periodSpanInput.setAdapter(adapter);

        Spinner notificationSpan = (Spinner) view.findViewById(R.id.numNotificationSpan);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, periodSpanTypes);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        notificationSpan.setAdapter(adapter1);

        interestName = view.findViewById(R.id.interestName);
        activityLength = view.findViewById(R.id.activityLength);
        numNotifications = view.findViewById(R.id.numNotifications);
        periodFreq = view.findViewById(R.id.periodFreq);

        // Initializes the interest page with set variables from the MainActivity call.
        interestName.setText(iName);
        activityLength.setText(Integer.toString(aLength));
        numNotifications.setText(Integer.toString(numNotif));
        periodSpanInput.setSelection(pSpanPtr);
        periodFreq.setText(Integer.toString(pFreq));

        glSurfaceView = view.findViewById(R.id.openGLView);
        GLRenderer.setActivityLength(START_TIME_MILLIS);
        textViewCountdown = view.findViewById(R.id.text_view_countdown);
        updateCountDownText();
        if(true)
        {
            GLRenderer.setTimerRunning(true);
            startTimer();
        }




//The following lines are for Editing the Interest.
        interestName = view.findViewById(R.id.interestName);
        activityLength = view.findViewById(R.id.activityLength);
        periodFreq = view.findViewById(R.id.periodFreq);
        periodSpanInput = view.findViewById(R.id.periodSpanInput);
        numNotifications = view.findViewById(R.id.numNotifications);

        // Finds the submit button, and an onClick method submits the data into the database.
        editInterestBn = view.findViewById(R.id.SubmitEditInterest);
        editInterestBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Interest updateInterest = theInterest;
                // Sets the interestName, which is the key for the database
                String newInterestName = interestName.getText().toString();

                /*
                 * Finds the raw values of the EditTexts and the Spinner, and saves them in
                 * int and String variables.
                 */
                String newActivityLengthTemp = activityLength.getText().toString();
                String newPeriodFreqTemp = periodFreq.getText().toString();
                String newPeriodSpan = periodSpanInput.getSelectedItem().toString();
                String newNumNotificationsTemp = numNotifications.getText().toString();

                if (!newInterestName.equals(updateInterest.getInterestName()))
                    updateInterest.setInterestName(newInterestName);
                if (!newActivityLengthTemp.equals(updateInterest.getActivityLength()))
                    updateInterest.setActivityLength(Integer.parseInt(newActivityLengthTemp));
                if (!newPeriodFreqTemp.equals(updateInterest.getPeriodFreq()))
                    updateInterest.setPeriodFreq(Integer.parseInt(newPeriodFreqTemp));
                if (!newNumNotificationsTemp.equals(updateInterest.getNumNotifications())) {
                    int newNumNotifications = Integer.parseInt(newNumNotificationsTemp);
                    updateInterest.setNumNotifications(newNumNotifications);
                    updateInterest.setNotifTimes(Interest.presetNotifTimes(newNumNotifications));
                }


                if (!newPeriodSpan.equals("Day")) {


                    int basePeriodSpan = 0;

                    /*
                     * Temporary values of basePeriodSpan, which will have to be revised for later.
                     * basePeriodSpan serves as a numeric representation of how long a day, week, month,
                     * and year are.
                     */
                    switch (newPeriodSpan) {
                        case "Day":
                            basePeriodSpan = 1;
                            break;
                        case "Week":
                            basePeriodSpan = 7;
                            break;
                        case "Month":
                            basePeriodSpan = 30;
                            break;
                        case "Year":
                            basePeriodSpan = 365;
                            break;
                    }
                    updateInterest.setBasePeriodSpan(basePeriodSpan);
                }


                // The database updates the interest to the interests table.
                MainActivity.myDB.myDao().updateInterest(updateInterest);

                // Announces that an interest was successfully edited.
                Toast.makeText(getActivity(), "Interest edited successfully", Toast.LENGTH_LONG).show();

                // Resets the AddInterest form.
                interestName.setText(theInterest.getInterestName());
                activityLength.setText(Integer.toString(theInterest.getActivityLength()));
                periodFreq.setText(Integer.toString(theInterest.getPeriodFreq()));
                numNotifications.setText(Integer.toString(theInterest.getNumNotifications()));

            }
        });







        //Stuff past here is for deleting an interest
        delete=(Button)view.findViewById(R.id.delete);
        //finding the name from the edit interest page
        delInterestName = view.findViewById(R.id.interestName);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //have to put string declaration in here or else it crashes
                final String delInterestName1 = delInterestName.getText().toString();
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                // this is where the interest is deleted
                                MainActivity.myDB.myDao().deleteByInterestName(delInterestName1);
                            }
                        });
                    }
                });
            }
        });



        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }
    @Override
    public void onPause(){
        super.onPause();
        glSurfaceView.onPause();
    }

    private void updateCountDownText() {
        int minutes = (int) mTimeLeftInMillis / 1000 / 60;
        int seconds = (int) mTimeLeftInMillis / 1000 % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textViewCountdown.setText(timeLeftFormatted);

    }
    public void startTimer() {
        countDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;

            }
        }.start();

        timerRunning = true;

    }
    private void pauseTimer()
    {
        countDownTimer.cancel();
        timerRunning = false;

    }
    private void resetTimer()
    {
        mTimeLeftInMillis = START_TIME_MILLIS;
        updateCountDownText();
    }

    // Getters and setters for the variables that will inflate the interest page.
    public void setiName (String iName) { this.iName = iName; }
    public void setaLength (int length) { aLength = length; }
    // pSpanPtr is the pointer for the Spinner selection.
    // 0 for day, 1 for week, 2 for month, 3 for year.
    public void setpSpanPtr (int pSpanPtr) { this.pSpanPtr = pSpanPtr; }
    public void setNumNotif (int numNotif) { this.numNotif = numNotif; }
    public void setpFreq(int pFreq) { this.pFreq = pFreq; }
}
