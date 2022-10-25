package com.example.madpropertypal_v3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class PropertyAdapterClass extends RecyclerView.Adapter<PropertyAdapterClass.ViewHolder> implements Filterable {

    ArrayList<PropertyModelClass> property;
    ArrayList<PropertyModelClass> mArrayList;
    Context context;
    DB_Controller db_controller;

    public PropertyAdapterClass(ArrayList<PropertyModelClass> property, Context context) {
        this.property = property;
        this.mArrayList=property;
        this.context = context;
        db_controller= new DB_Controller(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_item_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final PropertyModelClass propertyModelClass = property.get(position);


        holder.textViewID.setText(Integer.toString(propertyModelClass.getId()));
        holder.editText_name.setText(propertyModelClass.getName());
        holder.editText_protype.setText(propertyModelClass.getProtype());
        holder.editText_leasetype.setText(propertyModelClass.getLeasetype());
        holder.editText_bednum.setText(propertyModelClass.getBednum());
        holder.editText_bathnum.setText(propertyModelClass.getBathnum());
        holder.editText_size.setText(propertyModelClass.getSize());
        holder.editText_price.setText(propertyModelClass.getPrice());
        holder.editText_garden.setText(propertyModelClass.getGarden());
        holder.editText_drive.setText(propertyModelClass.getDrive());
        holder.editText_local.setText(propertyModelClass.getLocal());
        holder.editText_des.setText(propertyModelClass.getDes());

        holder.button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringname=holder.editText_name.getText().toString();
                String stringprotype=holder.editText_protype.getText().toString();
                String stringleasetype=holder.editText_leasetype.getText().toString();
                String stringbednum=holder.editText_bednum.getText().toString();
                String stringbathnum=holder.editText_bathnum.getText().toString();
                String stringsize=holder.editText_size.getText().toString();
                String stringprice=holder.editText_price.getText().toString();
                String stringgarden=holder.editText_garden.getText().toString();
                String stringdrive=holder.editText_drive.getText().toString();
                String stringlocal=holder.editText_local.getText().toString();
                String stringdes=holder.editText_des.getText().toString();

                if (stringname.isEmpty() || stringprotype.isEmpty() || stringleasetype.isEmpty() || stringbednum.isEmpty() || stringbathnum.isEmpty() || stringsize.isEmpty() || stringprice.isEmpty() || stringgarden.isEmpty() || stringdrive.isEmpty()){
                    Toast.makeText(context,"COMPLETE ALL 'REQUIRED DATA'", Toast.LENGTH_SHORT).show();

                }

                else{

                    db_controller.updateProperty(new PropertyModelClass(propertyModelClass.getId(),stringname,
                            stringprotype,stringleasetype,stringbednum,stringbathnum,stringsize,stringprice,
                            stringgarden,stringdrive,stringlocal,stringdes));
                    notifyDataSetChanged();
                    ((Activity) context).finish();
                    context.startActivity(((Activity) context).getIntent());

                }


            }
        });

        holder.button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db_controller.deleteProperty(propertyModelClass.getId());
                property.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.button_addreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context,AddReportActivity.class);
                context.startActivity(intent);
            }
        });

        holder.button_viewreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context,ViewReportActivity.class);
                context.startActivity(intent);

            }
        });



    }



    @Override
    public int getItemCount() {

        return property.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    property = mArrayList;
                }
                else {
                    ArrayList<PropertyModelClass> filteredList = new ArrayList<>();
                    for (PropertyModelClass propertyModelClass : mArrayList){
                        if (propertyModelClass.getName().toLowerCase().contains(charString) ||
                                propertyModelClass.getProtype().toLowerCase().contains(charSequence) ||
                                propertyModelClass.getLeasetype().toLowerCase().contains(charSequence)){
                            filteredList.add(propertyModelClass);
                        }
                    }
                    property = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values= property;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                property =(ArrayList<PropertyModelClass>) filterResults.values;
                notifyDataSetChanged();

            }
        };

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewID;
        EditText editText_name,editText_protype,editText_leasetype,editText_bednum,editText_bathnum,
                editText_size,editText_price,editText_garden,editText_drive,editText_local,editText_des;
        Button button_edit, button_delete, button_addreport,button_viewreport;

        Context context;

        WebView browser;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();

            textViewID = itemView.findViewById(R.id.text_id);

            editText_name = itemView.findViewById(R.id.edittext_name);
            editText_protype = itemView.findViewById(R.id.edittext_protype);
            editText_leasetype = itemView.findViewById(R.id.edittext_leasetype);
            editText_bednum = itemView.findViewById(R.id.edittext_bednum);
            editText_bathnum = itemView.findViewById(R.id.edittext_bathnum);
            editText_size = itemView.findViewById(R.id.edittext_size);
            editText_price = itemView.findViewById(R.id.edittext_price);
            editText_garden = itemView.findViewById(R.id.edittext_garden);
            editText_drive = itemView.findViewById(R.id.edittext_drive);
            editText_local = itemView.findViewById(R.id.edittext_local);
            editText_des = itemView.findViewById(R.id.edittext_des);

            button_edit = itemView.findViewById(R.id.button_edit);
            button_delete = itemView.findViewById(R.id.button_delete);

            button_addreport = itemView.findViewById(R.id.button_addreport);
            button_viewreport = itemView.findViewById(R.id.button_viewreport);

            browser = itemView.findViewById(R.id.webkit);


        }



    }


}
