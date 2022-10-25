package com.example.madpropertypal_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddReportActivity extends AppCompatActivity {

    DB_Controller mydb;

    DatePickerDialog mDatePickerDialog,mDatePickerDialog2;
    EditText rdate, rprice, rexpiry, rconditions, rcomments;
    Spinner rinterest;

    Button btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);

        mydb = new DB_Controller(this);


        rdate = (EditText) findViewById(R.id.rdate);
        rexpiry = (EditText) findViewById(R.id.rexpiry);
        rprice = (EditText) findViewById(R.id.rprice);
        rconditions = (EditText) findViewById(R.id.rconditions);
        rcomments = (EditText) findViewById(R.id.rcomments);

        rinterest = (Spinner) findViewById(R.id.rinterest);

        btn_add= (Button) findViewById(R.id.radd);

        setDateTimeField();

        rdate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDatePickerDialog.show();
                //DatePicker to String;
                return false;
            }
        });
        setDateTimeField2();
        rexpiry.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDatePickerDialog2.show();
                return false;
            }
        });

        // Interest type dropdown
        String[] rinterestitems = new String[]{"Interest - Required", "Firm", "Conditional", "Considering", "Not Interested"};
        ArrayAdapter<String> rinterestadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, rinterestitems);
        rinterest.setAdapter(rinterestadapter);


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rdate.getText().toString().isEmpty() || rinterest.getSelectedItem().toString() == "Interest - Required"){
                    Toast.makeText(AddReportActivity.this,"COMPLETE ALL 'REQUIRED DATA'", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        ReportModelClass reportModelClass = new ReportModelClass(rdate.getText().toString(),
                                rinterest.getSelectedItem().toString(),rprice.getText().toString(),
                                rexpiry.getText().toString(),rconditions.getText().toString(),rcomments.getText().toString());
                        mydb.addReport(reportModelClass);
                        Toast.makeText(AddReportActivity.this,"Data Inserted", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(getIntent());

                    }
                    catch (SQLException e){
                        Toast.makeText(AddReportActivity.this,"Data NOT Inserted", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


    }

    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
                final Date startDate = newDate.getTime();
                String fdate = sd.format(startDate);

                rdate.setText(fdate);


            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private void setDateTimeField2() {

        Calendar newCalendar = Calendar.getInstance();
        mDatePickerDialog2 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
                final Date startDate = newDate.getTime();
                String fdate = sd.format(startDate);

                rexpiry.setText(fdate);


            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        mDatePickerDialog2.getDatePicker().setMaxDate(System.currentTimeMillis()+((- 1000)+1000*60*60*24*20));

    }

}