package com.mobdev.rowenavroom.patientapp;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Rowena Vroom on 18-2-2016.
 */
public class BackgroundTask extends AsyncTask<String,Patient,String> {
    Context ctx;
    PatientAdapter patientAdapter;
    String emp_num;
    Activity activity;
    ListView listView;
    BackgroundTask(Context ctx){
        this.ctx = ctx;
        activity = (Activity) ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        String method = params[0];
        DatabaseOperations dbOperations = new DatabaseOperations(ctx);
        if(method.equals("add_info")){
            String patient_num = params[1];
            String firstname = params[2];
            String lastname = params[3];
            String current_condition= params[4];
            String emp_nr = params[5];

            dbOperations.insertPatient(dbOperations, patient_num, firstname, lastname, current_condition);
            dbOperations.insertEmployee_Patient(dbOperations, emp_nr, patient_num);
            dbOperations.close();

            return "One row added";
        }
        else if(method.equals("get_info")){
            listView = (ListView)activity.findViewById(R.id.display_listview);
            emp_num = params[1];
            String pat_num = "";

            Cursor CR = dbOperations.getPatientsFromEmployee(dbOperations, emp_num);
            String pat_id, fname, lname,current_condition;

            patientAdapter = new PatientAdapter(ctx, R.layout.display_patient_row);

//            CR.moveToFirst();
            while(CR.moveToNext()){
                pat_num = CR.getString(0).toString();
                Log.d("patientnumbervalue", CR.getString(0).toString());

                Cursor cursor = dbOperations.getPatientInformation(dbOperations, pat_num.toString());
                cursor.moveToFirst();
                    pat_id = cursor.getString(0).toString();
                    fname = cursor.getString(1).toString();
                    lname = cursor.getString(2).toString();
                    current_condition = cursor.getString(3).toString();

                    Patient patient = new Patient(pat_id, fname, lname, current_condition);
                    publishProgress(patient);
            }
            return "get_info";
        } else if(method.equals("del_pat")){
            String patient_num = params[1];

            dbOperations.deletePatientsFromEmployee(dbOperations, patient_num);
            dbOperations.deletePatientInformation(dbOperations, patient_num);
            dbOperations.close();
            return "One row deleted";
        }else if(method.equals("upd_pat")){
            String patient_num = params[1];
            String firstname = params[2];
            String lastname = params[3];
            String current_condition= params[4];
            dbOperations.updatePatientInformation(dbOperations, patient_num, firstname, lastname, current_condition);
            dbOperations.close();
            return "One row updated";
        }

        return null;

    }

    @Override
    protected void onProgressUpdate(Patient... patients) {
        patientAdapter.add(patients[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("get_info")){
            listView.setAdapter(patientAdapter);
        }else{
            Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();
        }
    }
}
