package com.example.madpropertypal_v3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Property;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewPropertyActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    DB_Controller db_controller;
    ArrayList<PropertyModelClass> allProperty = new ArrayList<>();

    PropertyAdapterClass propertyadapterclass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_property);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        db_controller = new DB_Controller(this);
        allProperty= db_controller.getPropertyList();


        if (allProperty.size() > 0){
            propertyadapterclass = new PropertyAdapterClass (allProperty,ViewPropertyActivity.this);
            recyclerView.setAdapter(propertyadapterclass);

        }
        else{
            Toast.makeText(this,"There is no property in the database", Toast.LENGTH_SHORT).show();

        }
        
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem search =menu.findItem(R.id.search);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(search);
        searchView.setQueryHint("Name, Property Type OR Lease Type");
        searchView.setMaxWidth(Integer.MAX_VALUE);
        search(searchView);
        return true;
    }

    private void search(SearchView searchView){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (propertyadapterclass!=null)
                    propertyadapterclass.getFilter().filter(newText);

                return true;
            }
        });
    }



}