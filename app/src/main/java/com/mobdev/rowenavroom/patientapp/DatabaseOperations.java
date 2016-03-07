package com.mobdev.rowenavroom.patientapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.mobdev.rowenavroom.patientapp.TableData.TableInfo;

/**
 * Created by Rowena Vroom on 11-2-2016.
 */
public class DatabaseOperations extends SQLiteOpenHelper {
    public static final int database_version = 1;
    public String CREATE_QUERY = "CREATE TABLE " + TableInfo.TABLE_NAME+"("+TableInfo.EMP_NUM+" TEXT);";
    public String CREATE_QUERY2 = "CREATE TABLE " + TableInfo.TABLE_NAME_patients+"("+TableInfo.PAT_NUM+" TEXT, " + TableInfo.PAT_FNAME + " TEXT, "+
            TableInfo.PAT_LNAME + " TEXT, "+TableInfo.PAT_CURRENT_CONDITION + " TEXT);";
    public String CREATE_QUERY3 = "CREATE TABLE " + TableInfo.TABLE_NAME_EMPLOYEES_PATIENTS+"("+TableInfo.EMP_NUM_FK+" TEXT, " + TableInfo.PAT_NUM_FK + " TEXT);";
    public DatabaseOperations(Context context) {
        super(context, TableInfo.DATABASE_NAME, null, database_version);
//        context.deleteDatabase(TableInfo.DATABASE_NAME);
        Log.d("Database operations", "Database has been created1");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        db.execSQL(CREATE_QUERY2);
        db.execSQL(CREATE_QUERY3);
        Log.d("Database operations", "Database has been created2");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void insertEmployee(DatabaseOperations dop, String pers_num){
        SQLiteDatabase SQ = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TableInfo.EMP_NUM, pers_num);
        long k = SQ.insert(TableInfo.TABLE_NAME, null, cv);
        Log.d("Database operations", "One employee has been inserted");
    }

    public Cursor getEmployeeInformation(DatabaseOperations dop){
        SQLiteDatabase SQ = dop.getReadableDatabase();
        String[] columns = {TableInfo.EMP_NUM};
        Cursor CR = SQ.query(TableInfo.TABLE_NAME, columns, null, null, null, null, null);
        return CR;
    }


    public void insertPatient(DatabaseOperations db, String pat_num, String pat_fname, String pat_lname,String pat_current_condition){
        SQLiteDatabase SQ = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TableInfo.PAT_NUM, pat_num);
        cv.put(TableInfo.PAT_FNAME, pat_fname);
        cv.put(TableInfo.PAT_LNAME, pat_lname);
        cv.put(TableInfo.PAT_CURRENT_CONDITION, pat_current_condition);

        SQ.insert(TableInfo.TABLE_NAME_patients, null, cv);

        Log.d("Database operations", "One patient has been inserted");
    }

    public Cursor getPatientInformation(DatabaseOperations db, String pat_num){
        SQLiteDatabase SQ = db.getReadableDatabase();
        String[] columns = {TableInfo.PAT_NUM, TableInfo.PAT_FNAME, TableInfo.PAT_LNAME,TableInfo.PAT_CURRENT_CONDITION};
        String [] pat_nr = {pat_num};
        Cursor CR = SQ.query(TableInfo.TABLE_NAME_patients, columns, "pat_num=?", pat_nr, null, null, null);
        return CR;
    }

    public void deletePatientInformation(DatabaseOperations db, String pat_num){
        String selection = TableInfo.PAT_NUM+ " LIKE ?";
        SQLiteDatabase SQ = db.getWritableDatabase();
        String patient_nr[] = {pat_num};
        SQ.delete(TableInfo.TABLE_NAME_patients, selection, patient_nr);

    }

    public void updatePatientInformation(DatabaseOperations db, String pat_num, String fname, String lname, String condition){
        SQLiteDatabase SQ = db.getWritableDatabase();
        String selection = TableInfo.PAT_NUM+ " LIKE ?";
        String patient_nr[] = {pat_num};

        ContentValues contentValues = new ContentValues();
        contentValues.put(TableInfo.PAT_FNAME, fname);
        contentValues.put(TableInfo.PAT_LNAME, lname);
        contentValues.put(TableInfo.PAT_CURRENT_CONDITION, condition);

        SQ.update(TableInfo.TABLE_NAME_patients,contentValues,selection, patient_nr);
    }

    public void insertEmployee_Patient(DatabaseOperations db, String emp_num, String pat_num){
        SQLiteDatabase SQ = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TableInfo.EMP_NUM_FK, emp_num);
        cv.put(TableInfo.PAT_NUM_FK, pat_num);

        SQ.insert(TableInfo.TABLE_NAME_EMPLOYEES_PATIENTS, null, cv);
        Log.d("Database operations", "One employee_patient has been inserted");
    }

    public Cursor getPatientsFromEmployee(DatabaseOperations db, String emp_num){
        SQLiteDatabase SQ = db.getReadableDatabase();
        String[] patients = {TableInfo.PAT_NUM_FK};
        String[] employee_nr = {emp_num};
        Cursor CR = SQ.query(TableInfo.TABLE_NAME_EMPLOYEES_PATIENTS, patients, "emp_num_fk=?", employee_nr, null, null, null);

        return CR;
    }

    public void deletePatientsFromEmployee(DatabaseOperations db, String pat_num){
        String selection = TableInfo.PAT_NUM_FK+ " LIKE ?";
        SQLiteDatabase SQ = db.getWritableDatabase();
        String patient_nr[] = {pat_num};
        SQ.delete(TableInfo.TABLE_NAME_EMPLOYEES_PATIENTS, selection, patient_nr);

    }
}

