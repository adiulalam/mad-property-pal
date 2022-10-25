package com.example.madpropertypal_v3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReportAdapterClass extends RecyclerView.Adapter<ReportAdapterClass.ViewHolder>{

    ArrayList<ReportModelClass> report;
    Context context;
    DB_Controller db_controller;

    public ReportAdapterClass(ArrayList<ReportModelClass> report, Context context) {
        this.report = report;
        this.context = context;
        db_controller= new DB_Controller(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_item_list_report,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final ReportModelClass reportModelClass = report.get(position);

        holder.textViewrID.setText(Integer.toString(reportModelClass.getRid()));
        holder.editText_rdate.setText(reportModelClass.getRdate());
        holder.editText_rinterest.setText(reportModelClass.getRinterest());
        holder.editText_rprice.setText(reportModelClass.getRprice());
        holder.editText_rexpiry.setText(reportModelClass.getRexpiry());
        holder.editText_rconditions.setText(reportModelClass.getRconditions());
        holder.editText_rcomments.setText(reportModelClass.getRcomments());


        holder.button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db_controller.deleteReport(reportModelClass.getRid());
                report.remove(position);
                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {

        return report.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewrID,editText_rdate,editText_rinterest,editText_rprice,editText_rexpiry,editText_rconditions,editText_rcomments;
        Button button_delete;

        Context context;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();

            textViewrID = itemView.findViewById(R.id.text_rid);

            editText_rdate = itemView.findViewById(R.id.text_rdate);
            editText_rinterest = itemView.findViewById(R.id.text_rinterest);
            editText_rprice = itemView.findViewById(R.id.text_rprice);
            editText_rexpiry = itemView.findViewById(R.id.text_rexpiry);
            editText_rconditions = itemView.findViewById(R.id.text_rconditions);
            editText_rcomments = itemView.findViewById(R.id.text_rcomments);

            button_delete = itemView.findViewById(R.id.button_delete);

        }



    }


}
