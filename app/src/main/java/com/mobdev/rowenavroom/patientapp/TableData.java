package com.mobdev.rowenavroom.patientapp;

import android.provider.BaseColumns;

/**
 * Created by Rowena Vroom on 11-2-2016.
 */
public class TableData {

    public TableData(){

    }

    public static abstract class TableInfo implements BaseColumns{

        public static final String DATABASE_NAME = "amc_info";

        //Data needed for table employees_info
        public static final String TABLE_NAME = "employees_info";
        public static final String EMP_NUM = "emp_num";


        //Data needed for table patients_info
        public static final String TABLE_NAME_patients = "patients_info";
        public static final String PAT_NUM = "pat_num";
        public static final String PAT_FNAME = "pat_fname";
        public static final String PAT_LNAME = "pat_lname";
        public static final String PAT_CURRENT_CONDITION = "pat_current_condition";

        //Data needed for employees_patients
        public static final String TABLE_NAME_EMPLOYEES_PATIENTS = "employees_patients";
        public static final String EMP_NUM_FK = "emp_num_fk";
        public static final String PAT_NUM_FK = "pat_num_fk";
    }
}
