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

    Button delete;

    private EditText interestName, activityLength, numNotifications;
    private Spinner periodSpanInput;


    private String iName;
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

        // Initializes the interest page with set variables from the MainActivity call.
        interestName.setText(iName);
        activityLength.setText(Integer.toString(aLength));
        numNotifications.setText(Integer.toString(numNotif));
        periodSpanInput.setSelection(pSpanPtr);

        glSurfaceView = view.findViewById(R.id.openGLView);

        textViewCountdown = view.findViewById(R.id.text_view_countdown);
        updateCountDownText();
        if(true)
        {
            //GLRenderer.setTimerRunning(Interest.getTimerRunning());
            startTimer();
        }

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

}