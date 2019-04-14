package com.example.activitease;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.persistence.room.Room;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.renderscript.ScriptGroup;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //EditText interestName, periodFrequency, basePeriodSpan, activityLength, numNotifications;
    static String currentInterestName;
    String startStopTimerText;
    public final String CHANNEL_ID = "Personal Notification";
    private final String NOTIFICATION_ID = "001";
    private static String currentDate = getCurrentDate();
    private boolean mShouldUnbind;
    private notificationService mBoundService;



    public static MyDB myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        createNotificationChannel();

        FragmentTransaction hp = getSupportFragmentManager().beginTransaction();
        hp.replace(R.id.fragment_container, new Home_Page_Fragment());
        hp.commit();


        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        /**
         Initializes the Room database object.
         Database is called 'interestdb'.
         */
        myDB = Room.databaseBuilder(getApplicationContext(), MyDB.class, "interestdb")
                .allowMainThreadQueries().build();

        //double[] notificationTimes = notification.getNotificationTimes();

        //for(Interest interest: interestList){

        popNotification();
    }

    public void popNotification()
    {
        Intent intent = new Intent(getApplicationContext(), notificationService.class);
        intent.setAction("com.example.activitease.notificationService");
        startService(intent);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  Because we have bound to a explicit
            // service that we know is running in our own process, we can
            // cast its IBinder to a concrete class and directly access it.
            mBoundService = ((notificationService.LocalBinder)service).getService();

            // Tell the user about this for our demo.
            Toast.makeText(MainActivity.this, "Service Connected",
                    Toast.LENGTH_SHORT).show();
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            // Because it is running in our same process, we should never
            // see this happen.
            mBoundService = null;

            Toast.makeText(MainActivity.this, "Service Disconnected",
                    Toast.LENGTH_SHORT).show();
        }
    };

    void doBindService() {
        // Attempts to establish a connection with the service.  We use an
        // explicit class name because we want a specific service
        // implementation that we know will be running in our own process
        // (and thus won't be supporting component replacement by other
        // applications).
        if (bindService(new Intent(MainActivity.this, notificationService.class),
                mConnection, Context.BIND_AUTO_CREATE)) {
            mShouldUnbind = true;
        } else {
            Log.e("MY_APP_TAG", "Error: The requested service doesn't " +
                    "exist, or this client isn't allowed access to it.");
        }
    }
    void doUnbindService() {
        if (mShouldUnbind) {
            // Release information about the service's state.
            unbindService(mConnection);
            mShouldUnbind = false;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }




   /* public void popNotification() {
        Intent intent = new Intent(this, notificationService.class);
        doBindService();
        startService(intent);
    } */

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String description = "Simple notification channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "channel", importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
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

        if (id == R.id.homePage) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Home_Page_Fragment()).commit();
        } else if (id == R.id.FAQ) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new FAQ_Fragment()).commit();

        } else if (id == R.id.About) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new About_Fragment()).commit();
        } else if (id == R.id.settings) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Settings_Fragment()).commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openAddInterest(View view) {
        FragmentTransaction hp = getSupportFragmentManager().beginTransaction();
        hp.replace(R.id.fragment_container, new Add_Interest_Fragment());
        hp.commit();
    }

    public void openInterest(View view) {
        //Loads the button that the method was called from.
        Button interestB = (Button) view;

        // Name of the interest found from the text of the button.
        String interestName = (String) interestB.getText();
        // Trims the button text to the interest name, which will be used to trigger db pull.
        interestName = interestName.substring(0, interestName.indexOf(" "));
        // Loads the interest, using the interest name as the key.
        Interest thisInterest = MainActivity.myDB.myDao().loadInterestByName(interestName);
        currentInterestName = thisInterest.getInterestName();

        FragmentTransaction hp = getSupportFragmentManager().beginTransaction();
        Interest_Fragment populatedInterest = new Interest_Fragment();

        /*
            Initializes variables in the Interest_Fragment object, which will then be used
            once the Interest_Fragment's onCreateView method is activated.
         */
        populatedInterest.setButtonText("Start Activity");
        populatedInterest.initializeInterest(thisInterest.getInterestName());
        /*
            pSpanPtr is the pointer for the Spinner selection.
            0 for day (1), 1 for week (7), 2 for month (30), 3 for year(365, or else in this case).
         */
        if (thisInterest.getBasePeriodSpan() == 1) populatedInterest.setpSpanPtr(0);
        else if (thisInterest.getBasePeriodSpan() == 7) populatedInterest.setpSpanPtr(1);
        else if (thisInterest.getBasePeriodSpan() == 30) populatedInterest.setpSpanPtr(2);
        else populatedInterest.setpSpanPtr(3);

        populatedInterest.setNumNotif(thisInterest.getNumNotifications());

        hp.replace(R.id.fragment_container, populatedInterest);
        hp.commit();
    }


    public void openAct1(View view) {


        FragmentTransaction hp = getSupportFragmentManager().beginTransaction();
        hp.replace(R.id.fragment_container, new Interest_Fragment());
        hp.commit();

        //myAwesomeTextView = (TextView)findViewById(R.id.actText);
        //myAwesomeTextView.setText("50 Push-Ups");

    }

    public void doneBtn(View view)  //When done button pressed, update interest and reload page.
    {
        Button b = (Button) view;
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Done")
                .setMessage("Are you sure you are done with this activity?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Interest_Fragment resetTimer = new Interest_Fragment();
                        resetTimer.resetTimer();
                        FragmentTransaction hp = getSupportFragmentManager().beginTransaction();
                        resetTimer.setTimerRunning(false);
                        Interest updatedInterest = MainActivity.myDB.myDao().loadInterestByName(currentInterestName);
                        resetTimer.initializeInterest(updatedInterest.getInterestName());
                        resetTimer.setButtonText("Start Activity");

                        hp.replace(R.id.fragment_container, resetTimer);
                        hp.commit();
                    }

                })
                .setNegativeButton("no", null)
                .show();
    }

    public static void interestComplete(Interest i) {
        i.setStreakCt(i.getStreakCt() + 1);
        i.setLastDate(getCurrentDate());
        myDB.myDao().updateInterest(i);
    }

    //Finds the current date in the Month/Day/Year format and returns it.
    public static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdFormat = new SimpleDateFormat("MM/dd/yyyy");
        String strDate = mdFormat.format(calendar.getTime());
        return strDate;
    }

    //Checks to see if the current date is different from the last date the app was opened.
    public static long getDateDifference() {
        List<Interest> interestList = myDB.myDao().getInterests();

        //Takes today's date and splits it into month, day, and year.
        String today[] = getCurrentDate().split("/");
        int todayMonth = Integer.parseInt(today[0]);
        int todayDay = Integer.parseInt(today[1]);
        int todayYear = Integer.parseInt(today[2]);

        //Takes the split date and assigns it to a comparable Calendar instance.
        Calendar todayDate = Calendar.getInstance();
        todayDate.set(Calendar.MONTH, todayMonth);
        todayDate.set(Calendar.DAY_OF_MONTH, todayDay);
        todayDate.set(Calendar.YEAR, todayYear);

        //Takes the last recorded currentDate and splits it into month, day, and year.
        String lastDate[] = currentDate.split("/");
        int lastMonth = Integer.parseInt(lastDate[0]);
        int lastDay = Integer.parseInt(lastDate[1]);
        int lastYear = Integer.parseInt(lastDate[2]);

        //Takes the split date and assigns it to a comparable Calendar variable.
        Calendar lastDateOpened = Calendar.getInstance();
        lastDateOpened.set(Calendar.MONTH, lastMonth);
        lastDateOpened.set(Calendar.DAY_OF_MONTH, lastDay);
        lastDateOpened.set(Calendar.YEAR, lastYear);

        //Finds the difference between both dates and returns it.
        Long difference = todayDate.getTimeInMillis() - lastDateOpened.getTimeInMillis();
        return difference;
    }

    //Resets the 'currentDate' to today's date.
    public static void resetCurrentDate() {
        currentDate = getCurrentDate();
    }

    public void onDeleteInterest(View v) {
        MainActivity.myDB.myDao().deleteByInterestName(currentInterestName);

        FragmentTransaction hp = getSupportFragmentManager().beginTransaction();
        hp.replace(R.id.fragment_container, new Home_Page_Fragment());
        Toast.makeText(this, "Interest deleted successfully", Toast.LENGTH_LONG).show();
        hp.commit();
    }

    public void onEditInterest(View v) {
        Interest thisInterest = myDB.myDao().loadInterestByName(currentInterestName);

        EditText activityLength = findViewById(R.id.activityLength);
        EditText activityAmount = findViewById(R.id.activityAmount);
        Spinner periodSpanInput = findViewById(R.id.periodSpanInput);
        EditText numNotifications = findViewById(R.id.numNotifications);

        int oldLength = thisInterest.getActivityLength();
        int oldNumNotif = thisInterest.getNumNotifications();

        /*
         * Finds the raw values of the EditTexts and the Spinner, and saves them in
         * int and String variables.
         */
        int newActivityLengthTemp = Integer.parseInt(activityLength.getText().toString());
        int newPeriodFreqTemp = Integer.parseInt(activityAmount.getText().toString());
        int newNumNotifications = Integer.parseInt(numNotifications.getText().toString());
        int basePeriodSpan = Integer.parseInt(activityAmount.getText().toString());


        // Refreshing the Interest with previous and newly set data
        thisInterest.setActivityLength(newActivityLengthTemp);
        thisInterest.setPeriodFreq(newPeriodFreqTemp);
        thisInterest.setBasePeriodSpan(basePeriodSpan);
        thisInterest.setTimeRemaining(newActivityLengthTemp);

        //thisInterest.setBasePeriodSpan(periodSpanInput);

        //Resetting the NotifTimes if the user enters a new notification value
        if (oldNumNotif != newNumNotifications) {
            thisInterest.setNotifTime1(0);
            thisInterest.setNotifTime2(0);
            thisInterest.setNotifTime3(0);
            thisInterest.setNotifTime4(0);
            thisInterest.setNotifTime5(0);
            thisInterest.setNotifTime6(0);
            thisInterest.setNotifTime7(0);
            thisInterest.setNotifTime8(0);
            thisInterest.setNotifTime9(0);
            thisInterest.setNotifTime10(0);
            thisInterest.setNotifTimes(thisInterest.getNumNotifications());
            thisInterest.setNumNotifications(newNumNotifications);
        }
        thisInterest.setNumIterations(0);
        myDB.myDao().updateInterest(thisInterest);
        Interest_Fragment updateInterest = new Interest_Fragment();
        FragmentTransaction hp = getSupportFragmentManager().beginTransaction();
        updateInterest.initializeInterest(thisInterest.getInterestName());
        hp.replace(R.id.fragment_container, updateInterest);
        hp.commit();


    }



    public void startStopTimer(View v) {
        Button b = (Button) v;
        startStopTimerText = b.getText().toString();
        if (startStopTimerText.equals("Start Activity")) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Start Activity")
                    .setMessage("Are you sure you want to start this activity?")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Interest updatedInterest = MainActivity.myDB.myDao().loadInterestByName(currentInterestName);

                            Interest_Fragment updateInterest = new Interest_Fragment();
                            updateInterest.setTimerRunning(true);
                            FragmentTransaction hp = getSupportFragmentManager().beginTransaction();
                            updateInterest.initializeInterest(updatedInterest.getInterestName());
                            GLRenderer.setNumIterations(updatedInterest.getNumIterations());
                            updateInterest.setButtonText("Pause");

                            hp.replace(R.id.fragment_container, updateInterest);
                            hp.commit();
                        }

                    })
                    .setNegativeButton("no", null)
                    .show();
        } else if (startStopTimerText.equals("Pause")) {

            Interest updatedInterest = MainActivity.myDB.myDao().loadInterestByName(currentInterestName);
            Interest_Fragment updateInterest = new Interest_Fragment();
            updateInterest.pauseTimer();
            FragmentTransaction hp = getSupportFragmentManager().beginTransaction();
            updateInterest.setTimerRunning(false);
            updateInterest.initializeInterest(updatedInterest.getInterestName());
            updateInterest.setButtonText("Start Activity");
            hp.replace(R.id.fragment_container, updateInterest);

            hp.commit();

            //Update timer. Update DB with new interest data
        }

    }

    public static boolean swipeLeftInterest(Interest thisIntr) {


        return false;
    }

    public static boolean swipeRightInterest(Interest thisIntr) {
        return false;
    }

  /*  public void openContactPage(View view)
    {
        startActivity(new Intent(getApplicationContext(), ContactManager.class));
        //getSupportFragmentManager().beginTransaction().
        // replace(R.id.fragment_container, new Contact()).commit();
    } */

    public void applyColorChange(View view) {
        Button b = (Button) view;
        Toolbar toolbar = findViewById(R.id.toolbar);
        Spinner colorSpinner = findViewById(R.id.colorSpinner);
        if (colorSpinner.getSelectedItem().equals("Yellow")) {
            toolbar.setBackgroundColor(Color.YELLOW);
            b.setBackgroundColor(Color.YELLOW);
        } else if (colorSpinner.getSelectedItem().equals("Red")) {
            toolbar.setBackgroundColor(Color.RED);
            b.setBackgroundColor(Color.RED);
        } else if (colorSpinner.getSelectedItem().equals("Green")) {
            toolbar.setBackgroundColor(Color.GREEN);
            b.setBackgroundColor(Color.GREEN);
        } else if (colorSpinner.getSelectedItem().equals("Blue")) {
            toolbar.setBackgroundColor(Color.BLUE);
            b.setBackgroundColor(Color.BLUE);
        }
    }
}