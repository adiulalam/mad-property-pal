package com.example.madpropertypal_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Property;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DB_Controller mydb;

    RadioGroup radiogarden,radiodrive;
    RadioButton radiobgarden,radiobdrive;

    EditText name,size,price,local,des;

    Spinner protype,leasetype,bednum,bathnum;

    Button btn_add,btn_list,btn_deleteall,btn_json;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mydb = new DB_Controller(this);

        btn_add= (Button) findViewById(R.id.add);
        btn_list= (Button) findViewById(R.id.list);
        btn_deleteall=(Button) findViewById(R.id.deleteall);
        btn_json=(Button) findViewById(R.id.json);

        name= (EditText) findViewById(R.id.name);
        size= (EditText) findViewById(R.id.size);
        price= (EditText) findViewById(R.id.price);
        local = (EditText) findViewById(R.id.local);
        des = (EditText) findViewById(R.id.des);

        protype = (Spinner) findViewById(R.id.protype);
        leasetype = (Spinner) findViewById(R.id.leasetype);
        bednum = (Spinner)  findViewById(R.id.bednum);
        bathnum = (Spinner) findViewById(R.id.bathnum);

        radiogarden = (RadioGroup) findViewById(R.id.radiogarden);

        radiodrive = (RadioGroup) findViewById(R.id.radiodrive);


        // Property type dropdown
        String[] protypeitems = new String[]{"Property Type - Required", "Apartment", "House", "Bungalow"};
        ArrayAdapter<String> protypeadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, protypeitems);
        protype.setAdapter(protypeadapter);

        // Property lease dropdown
        String[] leasetypeitems = new String[]{"Property Lease - Required", "Freehold", "Leasehold", "Short Let", "Long Let"};
        ArrayAdapter<String> leasetypeadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, leasetypeitems);
        leasetype.setAdapter(leasetypeadapter);

        // Property bedroom dropdown
        String[] bednumitems = new String[]{"Number of Bedroom - Required", "0", "1", "2", "3", "4", "5", "6", "7+"};
        ArrayAdapter<String> bednumadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, bednumitems);
        bednum.setAdapter(bednumadapter);

        //Property bathroom dropdown
        String[] bathnumitems = new String[]{"Number of Bathroom - Required", "0", "1", "2", "3+"};
        ArrayAdapter<String> bathnumadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, bathnumitems);
        bathnum.setAdapter(bathnumadapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedIdG = radiogarden.getCheckedRadioButtonId();
                radiobgarden = (RadioButton) findViewById(selectedIdG);

                int selectedIdD = radiodrive.getCheckedRadioButtonId();
                radiobdrive = (RadioButton) findViewById(selectedIdD);

                //validation and confirm
                String vname = name.getText().toString();
                String vprotype = protype.getSelectedItem().toString();
                String vleasetype = leasetype.getSelectedItem().toString();
                String vbednum = bednum.getSelectedItem().toString();
                String vbathnum = bathnum.getSelectedItem().toString();
                String vsize = size.getText().toString();
                String vprice= price.getText().toString();
                String vlocal= local.getText().toString();
                String vdes= des.getText().toString();



                if (vname.isEmpty() || vprotype == "Property Type - Required" ||
                        vleasetype == "Property Lease - Required" || vbednum == "Number of Bedroom - Required" ||
                        vbathnum == "Number of Bathroom - Required" || vsize.isEmpty() || vprice.isEmpty() ||
                        selectedIdG==-1 || selectedIdD==-1) {
                    Toast.makeText(MainActivity.this,"COMPLETE ALL 'REQUIRED DATA'", Toast.LENGTH_SHORT).show();
                }
                else{
                    ///Alert Dialog
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("ADD entry?")
                            .setMessage("Name: "+vname+
                                    "\nProperty Type: "+vprotype+
                                    "\nLease Type: "+vleasetype+
                                    "\nNumber of Bedroom: "+vbednum+
                                    "\nNumber of Bathroom: "+vbathnum+
                                    "\nProperty Size: "+vsize+
                                    "\nProperty Price: "+vprice+
                                    "\nGarden?: "+radiobgarden.getText().toString()+
                                    "\nDriveway?: "+radiobdrive.getText().toString()+
                                    "\nLocal Amenities: "+vlocal+
                                    "\nDescription: "+vdes)

                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    try {
                                        PropertyModelClass propertyModelClass = new PropertyModelClass( name.getText().toString(),
                                                protype.getSelectedItem().toString(),
                                                leasetype.getSelectedItem().toString(),
                                                bednum.getSelectedItem().toString(),
                                                bathnum.getSelectedItem().toString(),
                                                size.getText().toString(),
                                                price.getText().toString(),
                                                radiobgarden.getText().toString(),
                                                radiobdrive.getText().toString(),
                                                local.getText().toString(),
                                                des.getText().toString());
                                        mydb.addProperty(propertyModelClass);
                                        Toast.makeText(MainActivity.this,"Data Inserted", Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(getIntent());
                                    }
                                    catch (SQLException e){
                                        Toast.makeText(MainActivity.this,"Data NOT Inserted", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            })

                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }

            }
        });

        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,ViewPropertyActivity.class);
                startActivity(intent);
            }
        });

        btn_deleteall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete All")
                        .setMessage("Are you sure you want to delete all property list?")

                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                try {
                                    mydb.deleteAllProperty();
                                    Toast.makeText(MainActivity.this,"All Data Deleted", Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(getIntent());
                                }
                                catch (SQLException e){
                                    Toast.makeText(MainActivity.this,"Data NOT Deleted", Toast.LENGTH_SHORT).show();
                                }


                            }
                        })

                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });

        btn_json.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,json.class);
                startActivity(intent);

            }
        });



    }




}