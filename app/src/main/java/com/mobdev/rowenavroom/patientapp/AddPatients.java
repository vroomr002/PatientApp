package com.mobdev.rowenavroom.patientapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import java.util.Random;

/**
 * Created by Rowena Vroom on 11-2-2016.
 */
public class AddPatients extends AppCompatActivity {
    EditText fname;
    String firstname;
    EditText lname;
    String lastname;
    EditText curr_condition;
    String current_condition;
    Context context = this;
    public static final String MY_PREFS_NAME = "LoggedInEmployee";
    private Toolbar mToolbar;
    

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_patient);
        fname = (EditText) findViewById(R.id.FnameText);
        lname = (EditText) findViewById(R.id.LnameText);
        curr_condition = (EditText) findViewById(R.id.ConditionText);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add Patient");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void RegisterPatient(View view) {
        //Get the ID of the logged in Employee
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String emp_nr = prefs.getString("emp_nr", null);

        String patient_num;
        firstname = fname.getText().toString();
        lastname = lname.getText().toString();
        current_condition = curr_condition.getText().toString();

        Random rand = new Random();
        int n = rand.nextInt(50000) + 1;
        patient_num = Integer.toString(n);

        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("add_info", patient_num, firstname, lastname, current_condition, emp_nr);

        Intent intent = new Intent(context, DisplayPatient.class);
        startActivity(intent);
    }



}
