package com.mobdev.rowenavroom.patientapp;

/**
 * Created by Rowena Vroom on 18-2-2016.
 */
public class Patient {

    private String pat_id, fname, lname, current_condition;

    public Patient(String pat_id, String fname,String lname,String current_condition){

        this.setPat_id(pat_id);
        this.setCurrent_condition(current_condition);
        this.setFname(fname);
        this.setLname(lname);

    }

    public String getPat_id() {
        return pat_id;
    }

    public void setPat_id(String pat_id) {
        this.pat_id = pat_id;
    }


    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getCurrent_condition() {
        return current_condition;
    }

    public void setCurrent_condition(String current_condition) {
        this.current_condition = current_condition;
    }

}
