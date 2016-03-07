package com.mobdev.rowenavroom.patientapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Rowena Vroom on 11-2-2016.
 */
public class UpdatePatient extends AppCompatActivity {
    String id;
    String firstname;
    EditText fname;
    String lastname;
    EditText lname;
    String current_condition;
    EditText cc;
    Context context = this;
    private Toolbar mToolbar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_patient);

        Intent myIntent = getIntent(); // gets the previously created intent in Patient Adapter
        id = myIntent.getStringExtra("id");
        firstname= myIntent.getStringExtra("first_name");
        lastname = myIntent.getStringExtra("last_name");
        current_condition= myIntent.getStringExtra("condition");

        fname = (EditText) findViewById(R.id.editFnameText);
        lname =(EditText) findViewById(R.id.editLnameText);
        cc =(EditText) findViewById(R.id.editConditionText);

        fname.setText(firstname);
        lname.setText(lastname);
        cc.setText(current_condition);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Edit Patient");
        getSupportActionBar().setDisplayShowHomeEnabled(false);

    }

    public void UpdatePatient(View view) {
        String patient_num = id;
        String new_firstname = fname.getText().toString();
        String new_lastname = lname.getText().toString();
        String new_current_condition = cc.getText().toString();

        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("upd_pat", patient_num, new_firstname, new_lastname, new_current_condition);

        Intent intent = new Intent(context, DisplayPatient.class);
        startActivity(intent);
    }



}
