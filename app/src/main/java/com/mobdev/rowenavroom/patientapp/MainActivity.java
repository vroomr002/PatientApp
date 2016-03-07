package com.mobdev.rowenavroom.patientapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

/**
 * Created by Rowena Vroom on 7-2-2016.
 */
public class MainActivity extends AppCompatActivity {
    EditText persnumr;
    String personeelnr;
    Context context = this;
    public static final String MY_PREFS_NAME = "LoggedInEmployee";
    private Toolbar mToolbar;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        RegisterExample();
        persnumr = (EditText) findViewById(R.id.personeels_nummer);
        persnumr.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Login();
                }
                return false;
            }
        });
    }

    public void RegisterExample(){
        DatabaseOperations DB =  new DatabaseOperations(context);
        DB.insertEmployee(DB, "1234567890");
        DB.insertEmployee(DB, "0622083790");
        DB.insertEmployee(DB, "0644807257");
        DB.insertEmployee(DB, "8898021567");
        DB.insertEmployee(DB, "1999199694");
        Log.d("0001", "Just to be sure");
        DB.close();

    }

    public void Login(){
        personeelnr = persnumr.getText().toString();
        DatabaseOperations DOP =  new DatabaseOperations(context);
        Cursor CR = DOP.getEmployeeInformation(DOP);
        CR.moveToFirst();
        boolean loginstatus = false;
        String num = "";
        do {
            Log.d("db_values",CR.getString(0).toString());
            if(personeelnr.equals(CR.getString(0).toString())){
                loginstatus = true;
                num = CR.getString(0).toString();
            }
        }while(CR.moveToNext());
        if(loginstatus){
            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString("emp_nr", num );
            editor.putInt("id", 1);
            editor.apply();

            Toast.makeText(getBaseContext(), "Login successful, Welcome employee "+ num, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, DisplayPatient.class);
            startActivity(intent);
        }else{
            Toast.makeText(getBaseContext(), "Error while logging in please try this again", Toast.LENGTH_LONG).show();
        }

        DOP.close();
    }


}
