package com.example.activitease;

import android.content.DialogInterface;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.amitshekhar.utils.DatabaseHelper;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    EditText interestName, activityAmount, activityLength, numNotifications;
    Spinner periodFrequency, numNotificationSpan;
    Interest interest = new Interest();
    String startStopTimerText;
    private TextView textViewCountdown;
    boolean timerRunning;
    private static final long START_TIME_MILLIS = 600000;

    private CountDownTimer countDownTimer;
    private long mTimeLeftInMillis = START_TIME_MILLIS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        FragmentTransaction hp = getSupportFragmentManager().beginTransaction();
        hp.replace(R.id.fragment_container, new Home_Page_Fragment());
        hp.commit();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        textViewCountdown = findViewById(R.id.text_view_countdown);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.homePage) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home_Page_Fragment()).commit();
        }
        else if (id == R.id.FAQ) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FAQ_Fragment()).commit();

        } else if (id == R.id.Interest) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Interest_Fragment()).commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void openAddInterest(View view)
    {
        FragmentTransaction hp = getSupportFragmentManager().beginTransaction();
        hp.replace(R.id.fragment_container, new Add_Interest_Fragment());
        hp.commit();
    }

    public void submitEditInterest(View view)
    {
        //Throw error if in invalid format
        //Call instance of user to store data
        //From user call function to store data
        //Then return next page with updated content

        User db = new User(this);

        interestName = findViewById(R.id.interestName);
        String theInterestName = interestName.getText().toString();
        periodFrequency = findViewById(R.id.periodSpan);
        String periodFreq = periodFrequency.getSelectedItem().toString();
        activityLength = findViewById(R.id.activityLength);
        int theActivityLength = Integer.parseInt(activityLength.getText().toString());
        activityAmount = findViewById(R.id.activityAmount);
        int theActivityAmount = Integer.parseInt(activityAmount.getText().toString());
        numNotifications = findViewById(R.id.numNotifications);
        int theNumNotification = Integer.parseInt(numNotifications.getText().toString());
        numNotificationSpan = findViewById(R.id.numNotificationSpan);
        String theNumNotificationSpan = numNotificationSpan.getSelectedItem().toString();

        Interest interest = new Interest(theInterestName,periodFreq,theActivityLength, theActivityAmount, theNumNotification, theNumNotificationSpan);
        db.addInterest(interest);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Interest_Fragment()).commit();
    }

    public void setButtonText(String buttonText)
    {
        Button b = findViewById(R.id.startStop);
        b.setText(buttonText);

    }

    public void startStopTimer(View view) {
        Button b = (Button)view;
        startStopTimerText = b.getText().toString();
        if(startStopTimerText.equals("Start Activity")) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Start Activity")
                    .setMessage("Are you sure you want to start this activity?")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startStopTimerText = "Done";
                            setButtonText(startStopTimerText);
                            startTimer();
                        }

                    })
                    .setNegativeButton("no", null)
                    .show();
        }
        else if(startStopTimerText.equals("Done")) {
            startStopTimerText = "Start Activity";
            setButtonText(startStopTimerText);
            pauseTimer();
            //Update timer. Update DB with new interest data
        }

    }
    public void openActivity(View view) {


        FragmentTransaction hp = getSupportFragmentManager().beginTransaction();
        hp.replace(R.id.fragment_container, new Interest_Fragment());
        hp.commit();

        //myAwesomeTextView = (TextView)findViewById(R.id.actText);
        //myAwesomeTextView.setText("50 Push-Ups");

    }
    public void submitInterest(View view)
    {
        User db = new User(this);
        String intName = "interest";
        String periodFreq = "weekly";
        int activityLength = 3;
        int activityAmount = 1;
        int numNotifications = 3;
        String numNotificationSpan = "weekly";

        Interest interest = new Interest(intName, periodFreq, activityLength, activityAmount, numNotifications, numNotificationSpan);

        db.addInterest(interest);
    }
    private void startTimer() {
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
    private void updateCountDownText() {
        int minutes = (int) mTimeLeftInMillis / 1000 / 60;
        int seconds = (int) mTimeLeftInMillis / 1000 % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textViewCountdown.setText(timeLeftFormatted);

    }
}


