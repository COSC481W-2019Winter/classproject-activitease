package com.example.activitease;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ContactManager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_manager);
    }

    public void openHomePage(View view)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home_Page_Fragment()).commit();
    }
}
