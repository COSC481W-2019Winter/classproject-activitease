package com.example.activitease;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

import static com.example.activitease.MainActivity.getCurrentDate;
import static com.example.activitease.MainActivity.myDB;

public class Interest_Fragment extends Fragment {
    // EditText interestName, periodFrequency, basePeriodSpan, activityLength, numNotifications;
    MyGLSurfaceView glSurfaceView;
    static boolean timerRunning;
    private static boolean isTimerRunning = false;
    private static String buttonText;

    private static long START_TIME_MILLIS ;
    private static long mTimeLeftInMillis;
    private static double timeRemaining;
    private static int numIterations;

    private TextView textViewCountdown, streakCount;
    private static CountDownTimer countDownTimer;

    Button delete, editInterestBn, doneBTN;

    private EditText activityAmount, activityLength, numNotifications;
    private Spinner periodSpanInput;

    private int pSpanInput, numNotif;

    private static String iName;
    static Interest thisInterest;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.interest_page, container, false);
        TextView mytextview = view.findViewById(R.id.InterestName);
        String[] periodSpanTypes =
                {"Day", "Week", "Month", "Year"};

        Button startStop = view.findViewById(R.id.startStop);
        startStop.setText(buttonText);

        // Builds the period Span Spinner.
        periodSpanInput = view.findViewById(R.id.periodSpanInput);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, periodSpanTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        periodSpanInput.setAdapter(adapter);

        streakCount = view.findViewById(R.id.streakCount);

        doneBTN = view.findViewById(R.id.doneButton);
        if(isTimerRunning)
            doneBTN.setVisibility(View.VISIBLE);
        else
            doneBTN.setVisibility(View.GONE);

        activityAmount = view.findViewById(R.id.activityAmount);
        activityLength = view.findViewById(R.id.activityLength);
        numNotifications = view.findViewById(R.id.numNotifications);

        // Initializes the interest page with set variables from the MainActivity call.
        mytextview.setText(iName);
        activityLength.setText(Integer.toString(thisInterest.getActivityLength()));
        activityAmount.setText(Integer.toString(thisInterest.getPeriodFreq()));
        numNotifications.setText(Integer.toString(thisInterest.getNumNotifications()));

        final int spanInput;
        if(thisInterest.getBasePeriodSpan() == 1)
            spanInput = 0;
        else if(thisInterest.getBasePeriodSpan() == 7)
            spanInput = 1;
        else if(thisInterest.getBasePeriodSpan() == 30)
            spanInput = 2;
        else
            spanInput = 3;

        periodSpanInput.setSelection(spanInput);

        String streakCountString = "Streak Count: " + thisInterest.getStreakCt();
        streakCount.setText(streakCountString);

        glSurfaceView = view.findViewById(R.id.openGLView);

        textViewCountdown = view.findViewById(R.id.text_view_countdown);
        updateCountDownText();
        if(isTimerRunning)
        {
            GLRenderer.setTimerRunning(true);
            GLRenderer.setActivityLength(thisInterest.getTimeRemaining() * 60 * 1000);
            startTimer();
        }
        if(!isTimerRunning)
        {
            GLRenderer.setTimerRunning(false);
        }

        //Stuff past here is for deleting an interest
        // Finds the submit button, and an onClick method submits the data into the database.
        view.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            @Override
            public void onSwipeLeft() {
                Toast.makeText(getActivity(), "Swiped left", Toast.LENGTH_LONG).show();

                int intrPos = MainActivity.getInterestPos(thisInterest);

                // the last interest in the interest table cannot access a later interest.
                if (intrPos == 0) {
                    Toast.makeText(getActivity(), "No more interests", Toast.LENGTH_LONG).show();
                }
                else {
                    Interest prevInterest = myDB.myDao().getInterests().get(intrPos-1);
                    swipeNextInterest(prevInterest);
                }
            }

            public void onSwipeRight() {
                Toast.makeText(getActivity(), "Swiped right", Toast.LENGTH_LONG).show();

                int intrPos = MainActivity.getInterestPos(thisInterest);

                // the last interest in the interest table cannot access a later interest.
                if (intrPos+1 == myDB.myDao().getInterestCt()) {
                    Toast.makeText(getActivity(), "No more interests", Toast.LENGTH_LONG).show();
                }
                else {
                    Interest nextInterest = myDB.myDao().getInterests().get(intrPos+1);
                    swipeNextInterest(nextInterest);
                }
            }
        });
        return view;
    }

    public void swipeNextInterest(Interest nextInterest) {
        setButtonText("Start Activity");
        initializeInterest(nextInterest.getInterestName());

        activityLength.setText(Integer.toString(nextInterest.getActivityLength()));
        activityAmount.setText(Integer.toString(nextInterest.getPeriodFreq()));
        numNotifications.setText(Integer.toString(nextInterest.getNumNotifications()));

        int currSpanInput;
        if(nextInterest.getBasePeriodSpan() == 1)
            currSpanInput = 0;
        else if(nextInterest.getBasePeriodSpan() == 7)
            currSpanInput = 1;
        else if(nextInterest.getBasePeriodSpan() == 30)
            currSpanInput = 2;
        else
            currSpanInput = 3;

        periodSpanInput.setSelection(currSpanInput);

        MainActivity.currentInterestName = nextInterest.getInterestName();

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.detach(Interest_Fragment.this);
        fragmentTransaction.attach(Interest_Fragment.this);
        fragmentTransaction.commit();
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
                thisInterest.setTimeRemaining(timeRemaining);
                MainActivity.myDB.myDao().updateInterest(thisInterest);
                mTimeLeftInMillis = millisUntilFinished;
                timeRemaining = (float) mTimeLeftInMillis /60000;
                updateCountDownText();
            }

            @Override
            public void onFinish() {  //When analog timer finishes
                pauseTimer();
                timerRunning = false;
                resetTimer();

                thisInterest.addTimeSpent(thisInterest.getActivityLength());
                thisInterest.setLastDate(getCurrentDate());

                if (!thisInterest.getStreakCTBool()) {
                    thisInterest.decPeriodRemaining();

                    if (thisInterest.getPeriodRemaining() == 0) {
                        thisInterest.setStreakCTBool(true);
                        thisInterest.setStreakCt(thisInterest.getStreakCt() + 1);
                    }
                }
                MainActivity.myDB.myDao().updateInterest(thisInterest);

                // Resets interest page, and the timer.
                initializeInterest(thisInterest.getInterestName());
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                setButtonText("Start Activity");
                fragmentTransaction.detach(Interest_Fragment.this);
                fragmentTransaction.attach(Interest_Fragment.this);
                fragmentTransaction.commit();
            }
        }.start();
        timerRunning = true;
    }

    public void pauseTimer()
    {
        countDownTimer.cancel();
        GLRenderer.setTimerRunning(false);
        thisInterest.setTimeRemaining(timeRemaining);
        GLRenderer renderer = new GLRenderer();
        numIterations = renderer.getNumIterations();
        thisInterest.setNumIterations(numIterations);
        MainActivity.myDB.myDao().updateInterest(thisInterest);
    }

    public void resetTimer()
    {
        GLRenderer renderer = new GLRenderer();
        countDownTimer.cancel();
        mTimeLeftInMillis = START_TIME_MILLIS;
        isTimerRunning = false;
        thisInterest.setTimeRemaining(thisInterest.getActivityLength());
        thisInterest.setNumIterations(0);
        renderer.setNumIterations(0);
        MainActivity.myDB.myDao().updateInterest(thisInterest);

    }

    // Getters and setters for the variables that will inflate the interest page.
    public void initializeInterest (String iName) {
        this.iName = iName;
        thisInterest = MainActivity.myDB.myDao().loadInterestByName(iName);
        START_TIME_MILLIS = Math.round(thisInterest.getTimeRemaining() * 60 * 1000);
        GLRenderer.setNumIterations(thisInterest.getNumIterations());
        mTimeLeftInMillis = START_TIME_MILLIS;
    }

    public void setTimerRunning(boolean timerRunning) {isTimerRunning = timerRunning; }
    public void setButtonText(String btnText){buttonText = btnText; }
    public void setpSpanPtr(int pSpanPtr) { this.pSpanInput = pSpanPtr; }
    public void setNumNotif(int numNotif) { this.numNotif = numNotif; }
}
