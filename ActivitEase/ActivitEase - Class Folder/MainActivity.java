package com.example.activitease;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    EditText interestName, periodFrequency, basePeriodSpan, activityLength, numNotifications;
    static String currentInterestName;
    String startStopTimerText;
    public final String CHANNEL_ID = "Personal Notification";
    private final int NOTIFICATION_ID = 001;
    private static String currentDate = getCurrentDate();


    public static MyDB myDB;

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

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        /**
         Initializes the Room database object.
         Database is called 'interestdb'.
         */
        myDB = Room.databaseBuilder(getApplicationContext(), MyDB.class, "interestdb")
                .allowMainThreadQueries().build();

        List<Interest> interestList = MainActivity.myDB.myDao().getInterests();

        popNotification(interestList);


    }

    public void popNotification(List<Interest> interestList){

        for (int i = 0; i < interestList.size(); i++) {

            String interestName = interestList.get(i).getInterestName();
            double[] notificationTimes = interestList.get(i).getNotifTimes();
            int numOfNotification = interestList.get(i).getNumNotifications();

            Log.d(interestName, "----------------------------------------------------------------");
            for(int j = 0; j < numOfNotification; j++){

                //ArrayList<Notification> _arrayListOfNotification = new ArrayList<Notification>();

                int hour = (int) notificationTimes[j];
                int minute = (int) (notificationTimes[j] * 60 % 60);
                int second = (int) (notificationTimes[j] * 60 * 60 % 60);

               // Notification notification = new Notification(hour, minute, second);
               // _arrayListOfNotification.add(notification);
                Log.d(interestName,String.valueOf(hour + ":" + minute + ":" + second));

                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.HOUR_OF_DAY, minute);
                calendar.set(Calendar.HOUR_OF_DAY, second);

                Intent intent = new Intent(getApplicationContext(), notification_reciever.class);


                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100*i+j, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

            }
        }
    }


    public void notifyMe(View view) {
        NotificationCompat.Builder builder = new
                NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Simple Notification")
                .setWhen(System.currentTimeMillis())
                .setContentText("This is a simple notification")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity
                (this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());

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
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Home_Page_Fragment()).commit();
        }
        else if (id == R.id.FAQ) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new FAQ_Fragment()).commit();

        }
//        else if (id == R.id.Interest) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.fragment_container, new Interest_Fragment()).commit();
//        }
        else if (id == R.id.About) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new About_Fragment()).commit();
        }
       /* else if (id == R.id.Setting) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Settings_Fragment()).commit();
        } */

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

    public void openInterest(View view)
    {
        //Loads the button that the method was called from.
        Button interestB = (Button)view;

        // Name of the interest found from the text of the button.
        String interestName = (String)interestB.getText();
        // Trims the button text to the interest name, which will be used to trigger db pull.
        interestName = interestName.substring(0, interestName.indexOf("\n") - 1);
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


        populatedInterest.setNumNotif(thisInterest.getNumNotifications());

        hp.replace(R.id.fragment_container, populatedInterest);
        hp.commit();
    }
    
    public void openAct1(View view)
    {


        FragmentTransaction hp = getSupportFragmentManager().beginTransaction();
        hp.replace(R.id.fragment_container, new Interest_Fragment());
        hp.commit();

        //myAwesomeTextView = (TextView)findViewById(R.id.actText);
        //myAwesomeTextView.setText("50 Push-Ups");

    }

    public void doneBtn(View view)  //When done button pressed, update interest and reload page.
    {
        Button b = (Button) view;
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

    public static void interestComplete(Interest i) {
        i.setStreakCt(i.getStreakCt() + 1);
        i.setStreakCTBool(true);
        i.setLastDate(getCurrentDate());
        myDB.myDao().updateInterest(i);
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
        }
        else if(startStopTimerText.equals("Pause")) {

            Interest updatedInterest = MainActivity.myDB.myDao().loadInterestByName(currentInterestName);
            Interest_Fragment updateInterest = new Interest_Fragment();
            updateInterest.pauseTimer();
            FragmentTransaction hp = getSupportFragmentManager().beginTransaction();
            updateInterest.setTimerRunning(false);
            updateInterest.initializeInterest(updatedInterest.getInterestName());
            updateInterest.setButtonText("Resume");
            hp.replace(R.id.fragment_container, updateInterest);

            hp.commit();

            double totalTimeSpent = updatedInterest.getTotalTimeSpent();
            totalTimeSpent = totalTimeSpent + 1;
        }
        else if (startStopTimerText.equals("Resume")) {
            Interest_Fragment updateInterest = new Interest_Fragment();
            updateInterest.setButtonText("Pause");
        }
    }
    /*
        This method checks to make sure that a new interest will not have the same name as
        an already used one.
     */
    public static boolean interestNameUsed(String newInterestName) {
        List<Interest> interests = myDB.myDao().getInterests();

        // Iterates through all of the interests, to make sure a new name is not yet used.
        for (int i = 0; i < interests.size(); i++) {
            if (interests.get(i).getInterestName().equals(newInterestName))
                return true;
        }
        return false;
    }

    public static int getInterestTableSz() {
        return MainActivity.myDB.myDao().getInterests().size();
    }

    //Finds the current date in the Month/Day/Year format and returns it.
    public static String getCurrentDate(){
      Calendar calendar = Calendar.getInstance();
      SimpleDateFormat mdFormat = new SimpleDateFormat("MM/dd/yyyy");
      String strDate = mdFormat.format(calendar.getTime());
      return strDate;
  }

    //Checks to see if the current date is different from the last date the app was opened.
    public static long getDateDifference(){
        List<Interest> interestList = myDB.myDao().getInterests();

        //Takes today's date and splits it into month, day, and year.
        String today [] = getCurrentDate().split("/");
        int todayMonth = Integer.parseInt(today[0]);
        int todayDay = Integer.parseInt(today[1]);
        int todayYear = Integer.parseInt(today[2]);

        //Takes the split date and assigns it to a comparable Calendar variable.
        Calendar todayDate = Calendar.getInstance();
        todayDate.set(Calendar.MONTH,todayMonth);
        todayDate.set(Calendar.DAY_OF_MONTH,todayDay);
        todayDate.set(Calendar.YEAR, todayYear);

        //Takes the last recorded currentDate and splits it into month, day, and year.
        String lastDate [] = currentDate.split("/");
        int lastMonth = Integer.parseInt(lastDate[0]);
        int lastDay = Integer.parseInt(lastDate[1]);
        int lastYear = Integer.parseInt(lastDate[2]);

        //Takes the split date and assigns it to a comparable Calendar variable.
        Calendar lastDateOpened = Calendar.getInstance();
        lastDateOpened.set(Calendar.MONTH,lastMonth);
        lastDateOpened.set(Calendar.DAY_OF_MONTH,lastDay);
        lastDateOpened.set(Calendar.YEAR, lastYear);

        //Finds the difference between both dates and returns it.
        Long difference = todayDate.getTimeInMillis() - lastDateOpened.getTimeInMillis();
        return difference;
    }
    //Resets the 'currentDate' to today's date.
    public static void resetCurrentDate(){
        currentDate = getCurrentDate();
    }
}
