package com.example.activitease;

import android.os.AsyncTask;
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

import java.util.Locale;

public class Interest_Fragment extends Fragment {
    // EditText interestName, periodFrequency, basePeriodSpan, activityLength, numNotifications;
    MyGLSurfaceView glSurfaceView;
    boolean timerRunning;
    private static boolean isTimerRunning = false;
    private static String buttonText;

    private static long START_TIME_MILLIS;
    private long mTimeLeftInMillis;

    private TextView textViewCountdown;
    private CountDownTimer countDownTimer;
    private EditText delInterestName;

    Button delete;

    private EditText interestName, activityLength, numNotifications;
    private Spinner periodSpanInput;


    private String iName;
    Interest thisInterest;

    private int aLength, numNotif, pSpanPtr;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.interest_page, container, false);
        TextView mytextview = view.findViewById(R.id.EditInterestName);
        Interest theInterest = new Interest();
        mytextview.setText(theInterest.getInterestName());
        String[] periodSpanTypes =
                {"Day", "Week", "Month", "Year"};

        Button startStop = view.findViewById(R.id.startStop);
        startStop.setText(buttonText);

        // Builds the period Span Spinner.
        periodSpanInput = view.findViewById(R.id.periodSpanInput);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, periodSpanTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        periodSpanInput.setAdapter(adapter);

        Spinner notificationSpan = view.findViewById(R.id.numNotificationSpan);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, periodSpanTypes);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        notificationSpan.setAdapter(adapter1);

        interestName = view.findViewById(R.id.interestName);
        activityLength = view.findViewById(R.id.activityLength);
        numNotifications = view.findViewById(R.id.numNotifications);

        // Initializes the interest page with set variables from the MainActivity call.
        interestName.setText(thisInterest.getInterestName());
        activityLength.setText(Integer.toString(thisInterest.getActivityLength()));
        numNotifications.setText(Integer.toString(thisInterest.getNumNotifications()));
        periodSpanInput.setSelection(thisInterest.getBasePeriodSpan());

        glSurfaceView = view.findViewById(R.id.openGLView);

        textViewCountdown = view.findViewById(R.id.text_view_countdown);
        updateCountDownText();
        if(isTimerRunning)
        {
            GLRenderer.setTimerRunning(true);
            GLRenderer.setActivityLength(thisInterest.getActivityLength() * 60 * 1000);
            startTimer();
        }
        if(!isTimerRunning)
        {
            GLRenderer.setTimerRunning(false);
        }

        //Stuff past here is for deleting an interest
        delete= view.findViewById(R.id.delete);
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
    public void initializeInterest (String iName) {
        this.iName = iName;
        thisInterest = MainActivity.myDB.myDao().loadInterestByName(iName);
        START_TIME_MILLIS = thisInterest.getActivityLength() * 60 * 1000;
        mTimeLeftInMillis = START_TIME_MILLIS;

        }
    public void setpSpanPtr (int pSpanPtr) { this.pSpanPtr = pSpanPtr; }
    public void setTimerRunning(boolean timerRunning) {isTimerRunning = timerRunning; }
    public void setNumNotif (int numNotif) { this.numNotif = numNotif; }
    public void setButtonText(String btnText){buttonText = btnText; }

}