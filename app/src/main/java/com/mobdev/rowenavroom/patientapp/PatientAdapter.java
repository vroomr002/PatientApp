package com.mobdev.rowenavroom.patientapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rowena Vroom on 18-2-2016.
 */
public class PatientAdapter extends ArrayAdapter{
    List list = new ArrayList();
    private Context context;

    public PatientAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    public void add(Patient object){
        list.add(object);
        super.add(object);
    }

    public int getCount(){
        return list.size();
    }

    public Object getItem(int position){
        return list.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        PatientHolder patientHolder;
        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.display_patient_row,parent, false);
            patientHolder = new PatientHolder();
            patientHolder.tx_pat_id = (TextView)row.findViewById(R.id.t_pat_id);
            patientHolder.tx_fname = (TextView)row.findViewById(R.id.tx_fname);
            patientHolder.tx_lname = (TextView)row.findViewById(R.id.tx_lname);
//            patientHolder.tx_current_condition = (TextView)row.findViewById(R.id.tx_current_condition);
            patientHolder.edit = (ImageView)row.findViewById(R.id.edit);
            patientHolder.delete = (ImageView)row.findViewById(R.id.delete);
            row.setTag(patientHolder);
        }else{
            patientHolder = (PatientHolder) row.getTag();
        }

        Patient patient  = (Patient) getItem(position);
        patientHolder.tx_pat_id.setText(patient.getPat_id().toString());
        patientHolder.tx_fname.setText(patient.getFname().toString());
        patientHolder.tx_lname.setText(patient.getLname().toString());

        final String new_id = patient.getPat_id().toString();
        final String new_fname = patient.getFname().toString();
        final String new_lname = patient.getLname().toString();
        final String new_condition = patient.getCurrent_condition().toString();

        patientHolder.edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdatePatient.class);
                intent.putExtra("id", new_id);
                intent.putExtra("first_name",new_fname);
                intent.putExtra("last_name",new_lname);
                intent.putExtra("condition",new_condition);
                context.startActivity(intent);
            }
        });
        patientHolder.delete.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                deleteConfirm(view, new_id);
            }
        });
//        patientHolder.tx_current_condition.setText(patient.getCurrent_condition().toString());


        return row;
    }

    static class PatientHolder{
        TextView tx_pat_id, tx_fname,tx_lname, tx_current_condition;
        ImageView delete, edit;
    }

    public void deleteConfirm(View view, final String pat_id){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Delete");
        alertDialogBuilder.setIcon(R.drawable.trash);
        alertDialogBuilder.setMessage("Are you sure you want to delete patient " + pat_id + "?");
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                BackgroundTask backgroundTask = new BackgroundTask(context);
                backgroundTask.execute("del_pat", pat_id);
                Intent intent2 = new Intent(context, DisplayPatient.class);
                context.startActivity(intent2);
            }
        });
        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
