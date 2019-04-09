package com.example.activitease;

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

    private TextView textViewCountdown;
    private static CountDownTimer countDownTimer;


    Button delete, editInterestBn, doneBTN;

    private EditText interestName, activityLength, numNotifications;
    private Spinner periodSpanInput;


    private static String iName;
    static Interest thisInterest;

    private int aLength, numNotif, pSpanPtr;

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

        doneBTN = view.findViewById(R.id.doneButton);  //Done button layout

        Spinner notificationSpan = view.findViewById(R.id.numNotificationSpan);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, periodSpanTypes);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        notificationSpan.setAdapter(adapter1);

        interestName = view.findViewById(R.id.interestName);
        activityLength = view.findViewById(R.id.activityLength);
        numNotifications = view.findViewById(R.id.numNotifications);

        // Initializes the interest page with set variables from the MainActivity call.
        mytextview.setText(iName);
        activityLength.setText(Integer.toString(thisInterest.getActivityLength()));
        numNotifications.setText(Integer.toString(thisInterest.getNumNotifications()));
        periodSpanInput.setSelection(thisInterest.getBasePeriodSpan());

        glSurfaceView = view.findViewById(R.id.openGLView);

        textViewCountdown = view.findViewById(R.id.text_view_countdown);
        updateCountDownText();
        if(isTimerRunning) //Conditions if timer is running
        {
            doneBTN.setVisibility(View.VISIBLE); //Makes done button visible
            GLRenderer.setTimerRunning(true); //Sets the animations to active

            startTimer();  //Starts the analog timer
        }
        if(!isTimerRunning)  //If not
        {
            doneBTN.setVisibility(View.GONE);  //Sets done button to invisible
            GLRenderer.setTimerRunning(false); //Turns off animation
        }

        //Stuff past here is for deleting an interest
        // Finds the submit button, and an onClick method submits the data into the database.
        editInterestBn = view.findViewById(R.id.SubmitEditInterest);
        editInterestBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creating an instance of Interest, so that should an error occur our original interest isn't corrupted.
                Interest updInterest = thisInterest;

                /*
                 * Finds the raw values of the EditTexts and the Spinner, and saves them in
                 * int and String variables.
                 */
                String newActivityLengthTemp = activityLength.getText().toString();
                int newNumNotifications = Integer.parseInt(numNotifications.getText().toString());
                int basePeriodSpan = 1;


                // Refreshing the Interest with previously and newly set data
                updInterest.setInterestName(iName);
                updInterest.setActivityLength(thisInterest.getActivityLength());
                updInterest.setNumNotifications(thisInterest.getNumNotifications());
                //updInterest.setNotifTimes(Interest.presetNotifTimes(newNumNotifications));

                /*
                 * Temporary values of basePeriodSpan, which will have to be revised for later.
                 * basePeriodSpan serves as a numeric representation of how long a day, week, month,
                 * and year are.
                 */
               /* switch (newPeriodSpan) {
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
                updInterest.setBasePeriodSpan(basePeriodSpan); */


                // The database updates the interest to the interests table.
                MainActivity.myDB.myDao().updateInterest(updInterest);

                // Announces that an interest was successfully edited.
                Toast.makeText(getActivity(), "Interest edited successfully", Toast.LENGTH_LONG).show();

                // Resets the EditInterest form to what is saved in the database.
                activityLength.setText(Integer.toString(thisInterest.getActivityLength()));
                numNotifications.setText(Integer.toString(thisInterest.getNumNotifications()));

            }
        });







        //Stuff past here is for deleting an interest
        delete=(Button)view.findViewById(R.id.delete);
        //finding the name from the edit interest page
//        delInterestName = view.findViewById(R.id.interestName);   //Deprecated as interestName is no longer a field of the Interest Page
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //have to put string declaration in here or else it crashes
                final String delInterestName1 = thisInterest.getInterestName();
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

    private void updateCountDownText() {   //Updates analog clock
        int minutes = (int) mTimeLeftInMillis / 1000 / 60;
        int seconds = (int) mTimeLeftInMillis / 1000 % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textViewCountdown.setText(timeLeftFormatted);

    }
    public void startTimer() {  //Starts the timer
        countDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) { //Called every tick, update data
                mTimeLeftInMillis = millisUntilFinished;
                timeRemaining = (float) mTimeLeftInMillis /60000;
                updateCountDownText();
            }

            @Override
            public void onFinish() {  //When analog timer finishes

                timerRunning = false;

            }
        }.start();

        timerRunning = true;

    }
    public void pauseTimer() //Pauses the timer
    {
        isTimerRunning = false;
        countDownTimer.cancel();
        thisInterest.setTimeRemaining(timeRemaining);
        thisInterest.setNumIterations(GLRenderer.getNumIterations());
        MainActivity.myDB.myDao().updateInterest(thisInterest);



    }
    public void resetTimer() //Resets the timer
    {
        countDownTimer.cancel();
        mTimeLeftInMillis = START_TIME_MILLIS;
        isTimerRunning = false;
        thisInterest.setTimeRemaining(thisInterest.getActivityLength());
        thisInterest.setNumIterations(0);
        MainActivity.myDB.myDao().updateInterest(thisInterest);

    }

    // Getters and setters for the variables that will inflate the interest page.
    public void initializeInterest (String iName) { //Initializes data specific to interest and draws initial timer on page load.
        this.iName = iName;
        thisInterest = MainActivity.myDB.myDao().loadInterestByName(iName);
        GLRenderer.setNumIterations(thisInterest.getNumIterations());
        GLRenderer.setActivityLength(thisInterest.getTimeRemaining() * 60 * 1000);
        START_TIME_MILLIS = Math.round(thisInterest.getTimeRemaining() * 60 * 1000);
        mTimeLeftInMillis = START_TIME_MILLIS;

        }
    public void setpSpanPtr (int pSpanPtr) { this.pSpanPtr = pSpanPtr; } //Methods that will be deprecated
    public void setTimerRunning(boolean timerRunning) {isTimerRunning = timerRunning; }
    public void setNumNotif (int numNotif) { this.numNotif = numNotif; }
    public void setButtonText(String btnText){buttonText = btnText; }

}