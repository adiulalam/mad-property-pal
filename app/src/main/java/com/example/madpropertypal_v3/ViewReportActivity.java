package com.example.madpropertypal_v3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewReportActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    DB_Controller db_controller;
    ArrayList<ReportModelClass> allReport = new ArrayList<>();

    ReportAdapterClass reportadapterclass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);


        recyclerView = findViewById(R.id.recyclerViewreport);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        db_controller = new DB_Controller(this);
        allReport= db_controller.getReportList();

        if (allReport.size() > 0){
            reportadapterclass = new ReportAdapterClass (allReport,ViewReportActivity.this);
            recyclerView.setAdapter(reportadapterclass);

        }
        else{
            Toast.makeText(this,"There is no property in the database", Toast.LENGTH_SHORT).show();

        }


    }
}