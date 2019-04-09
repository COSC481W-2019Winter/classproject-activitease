package com.example.activitease;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    EditText interestName, periodFrequency, basePeriodSpan, activityLength, numNotifications;
    static String currentInterestName;
    String startStopTimerText;
    public final String CHANNEL_ID = "Personal Notification";
    private final int NOTIFICATION_ID = 001;


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
        GLRenderer.setTimerRunning(false);
        GLRenderer.setNumIterations(updatedInterest.getNumIterations());


        hp.replace(R.id.fragment_container, resetTimer);
        hp.commit();

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
            updateInterest.setButtonText("Start Activity");
            GLRenderer.setNumIterations(updatedInterest.getNumIterations());

            hp.replace(R.id.fragment_container, updateInterest);

             hp.commit();

            //Update timer. Update DB with new interest data
        }

    }

    public static int getInterestTableSz() {
        return MainActivity.myDB.myDao().getInterests().size();
    }

  /*  public void openContactPage(View view)
    {
        startActivity(new Intent(getApplicationContext(), ContactManager.class));
        //getSupportFragmentManager().beginTransaction().
        // replace(R.id.fragment_container, new Contact()).commit();
    } */

}
