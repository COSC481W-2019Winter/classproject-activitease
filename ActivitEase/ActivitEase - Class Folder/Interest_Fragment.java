package com.example.activitease;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


public class Interest_Fragment extends Fragment {
    // EditText interestName, periodFrequency, basePeriodSpan, activityLength, numNotifications;
    MyGLSurfaceView glSurfaceView;
    boolean timerRunning;
    private static final long START_TIME_MILLIS = 600000;
    private long mTimeLeftInMillis = START_TIME_MILLIS;
    private TextView textViewCountdown;
    private CountDownTimer countDownTimer;

    private Button editInterestBn;
    private EditText interestName, activityAmount, numNotifications, activityLength;
    private Spinner periodSpan;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.interest_page, container, false);
        TextView mytextview = view.findViewById(R.id.EditInterestName);


        // For development purposes, we have a "Test" interest. When we have Interests populating the Home Page, edit the following line to snatch the interest selected.
        final Interest theInterest = MainActivity.myDB.myDao().loadInterestByName("Test");



        mytextview.setText(theInterest.getInterestName());
        String[] periodSpanTypes =
                {"Day", "Week", "Month", "Year"};

        Spinner periodSpanSpinner = (Spinner) view.findViewById(R.id.periodSpanInput);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, periodSpanTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        periodSpanSpinner.setAdapter(adapter);

        Spinner notificationSpan = (Spinner) view.findViewById(R.id.numNotificationSpan);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, periodSpanTypes);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        notificationSpan.setAdapter(adapter1);
        glSurfaceView = view.findViewById(R.id.openGLView);

        textViewCountdown = view.findViewById(R.id.text_view_countdown);
        updateCountDownText();
        if(true)
        {
            startTimer();
        }




        //The following lines are for Editing the Interest.
        interestName = view.findViewById(R.id.interestName);
        activityLength = view.findViewById(R.id.activityLength);
        activityAmount = view.findViewById(R.id.activityAmount);
        periodSpan = view.findViewById(R.id.periodSpanInput);
        numNotifications = view.findViewById(R.id.numNotifications);

        // Finds the submit button, and an onClick method submits the data into the database.
        editInterestBn = view.findViewById(R.id.SubmitEditInterest);
        editInterestBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Sets the interestName, which is the key for the database
                String newInterestName = interestName.getText().toString();
             //   String newInterestName = "Bitchin";

                /*
                 * Finds the raw values of the EditTexts and the Spinner, and saves them in
                 * int and String variables.
                 */
                String newActivityLengthTemp = activityLength.getText().toString();
                String newPeriodFreqTemp = activityAmount.getText().toString();
                String newPeriodSpan = periodSpan.getSelectedItem().toString();
                String newNumNotificationsTemp = numNotifications.getText().toString();

                if (!newInterestName.equals(""))
                    theInterest.setInterestName(newInterestName);
                if (!newActivityLengthTemp.equals(""))
                    theInterest.setActivityLength(Integer.parseInt(newActivityLengthTemp));
                if (!newPeriodFreqTemp.equals(""))
                    theInterest.setPeriodFreq(Integer.parseInt(newPeriodFreqTemp));
                if (!newNumNotificationsTemp.equals("")) {
                    int newNumNotifications = Integer.parseInt(newNumNotificationsTemp);
                    theInterest.setNumNotifications(newNumNotifications);
                    theInterest.setNotifTimes(Interest.presetNotifTimes(newNumNotifications));
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
                    theInterest.setBasePeriodSpan(basePeriodSpan);
                }


                    // The database updates the interest to the interests table.
                    MainActivity.myDB.myDao().updateInterest(theInterest);

                    // Announces that an interest was successfully edited.
                    Toast.makeText(getActivity(), "Interest edited successfully", Toast.LENGTH_LONG).show();

                    // Resets the AddInterest form.
                    interestName.setText("");
                    activityLength.setText("");
                    activityAmount.setText("");
                    numNotifications.setText("");

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


}
