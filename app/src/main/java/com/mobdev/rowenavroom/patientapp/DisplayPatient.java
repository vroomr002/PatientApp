package com.mobdev.rowenavroom.patientapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DisplayPatient extends AppCompatActivity {
    public static final String MY_PREFS_NAME = "LoggedInEmployee";
    private Toolbar mToolbar;
    Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_patient_layout);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String emp_nr = prefs.getString("emp_nr", null);
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("get_info", emp_nr);

        FloatingActionButton registerbutton = (FloatingActionButton) findViewById(R.id.FAB);
        registerbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, AddPatients.class);
                startActivity(intent);
            }
        });

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Patients");
        getSupportActionBar().setDisplayShowHomeEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public boolean Logout(MenuItem item){
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        Toast.makeText(getBaseContext(), "You've succesfully logged out ", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onBackPressed(){
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        Toast.makeText(getBaseContext(), "You've succesfully logged out ", Toast.LENGTH_SHORT).show();
    }
}
