package com.example.madpropertypal_v3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.style.AlignmentSpan;

import java.util.ArrayList;
import java.util.List;


public class DB_Controller extends SQLiteOpenHelper {

    private static final String db_name = "Property.db";
    private static final String db_table = "property_table";
    private static final String db_report_table = "property_report_table";


    //cols
    private static final String id = "id";
    private static final String name = "Property_Name";
    private static final String protype = "Property_Type";
    private static final String leasetype = "Lease_Type";
    private static final String bednum = "Property_Bedroom";
    private static final String bathnum = "Property_Bathroom";
    private static final String size = "Property_Size";
    private static final String price = "Property_Asking_Price";
    private static final String garden = "Garden";
    private static final String drive = "Driveway";
    private static final String local = "Local_Amenities"; //optional choice
    private static final String des = "Description"; //optional choice

    //report cols
    private static final String rid = "id";
    private static final String pid = "Property_id";
    private static final String rdate = "Report_Date";
    private static final String rinterest = "Interest";
    private static final String rprice = "Offer_Price";
    private static final String rexpiry = "Offer_Expiry_Date";
    private static final String rconditions = "Offer_Conditions";
    private static final String rcomments = "Viewing_Comments";



    //creating table
        private static final String create_table = "CREATE TABLE "+db_table+" ("+
            id+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            name+" TEXT NOT NULL, "+
            protype+" TEXT NOT NULL, "+
            leasetype+" TEXT NOT NULL, "+
            bednum+" TEXT NOT NULL, "+
            bathnum+" TEXT NOT NULL, "+
            size+" TEXT NOT NULL, "+
            price+" TEXT NOT NULL, "+
            garden+" TEXT NOT NULL, "+
            drive+" TEXT NOT NULL, "+
            local+" TEXT, "+
            des+" TEXT "+")";

    //Creating report table
        private static final String create_report_table = "CREATE TABLE "+db_report_table+" ("+
            rid+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            rdate+" TEXT NOT NULL, "+
            rinterest+" TEXT NOT NULL, "+
            rprice+" TEXT, "+
            rexpiry+" TEXT, "+
            rconditions+" TEXT, "+
            rcomments+" TEXT "+")";


    public DB_Controller(Context context) {
        super(context, db_name, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_table);
        sqLiteDatabase.execSQL(create_report_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+db_table);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+db_report_table);
        onCreate(sqLiteDatabase);

    }


    public Cursor getListContents(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor data = sqLiteDatabase.rawQuery("SELECT * FROM "+db_table,null);
        return data;
    }

    public void addReport(ReportModelClass reportModelClass){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_Controller.rdate, reportModelClass.getRdate());
        contentValues.put(DB_Controller.rinterest, reportModelClass.getRinterest());
        contentValues.put(DB_Controller.rprice, reportModelClass.getRprice());
        contentValues.put(DB_Controller.rexpiry, reportModelClass.getRexpiry());
        contentValues.put(DB_Controller.rconditions, reportModelClass.getRconditions());
        contentValues.put(DB_Controller.rcomments, reportModelClass.getRcomments());

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert(DB_Controller.db_report_table, null,contentValues);
    }

    public void addProperty(PropertyModelClass propertyModelClass){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_Controller.name, propertyModelClass.getName());
        contentValues.put(DB_Controller.protype, propertyModelClass.getProtype());
        contentValues.put(DB_Controller.leasetype, propertyModelClass.getLeasetype());
        contentValues.put(DB_Controller.bednum, propertyModelClass.getBednum());
        contentValues.put(DB_Controller.bathnum, propertyModelClass.getBathnum());
        contentValues.put(DB_Controller.size, propertyModelClass.getSize());
        contentValues.put(DB_Controller.price, propertyModelClass.getPrice());
        contentValues.put(DB_Controller.garden, propertyModelClass.getGarden());
        contentValues.put(DB_Controller.drive, propertyModelClass.getDrive());
        contentValues.put(DB_Controller.local, propertyModelClass.getLocal());
        contentValues.put(DB_Controller.des, propertyModelClass.getDes());

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert(DB_Controller.db_table, null,contentValues);
    }

    public ArrayList<ReportModelClass> getReportList(){

        String sql = "SELECT * FROM "+db_report_table;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ArrayList<ReportModelClass>storeReport=new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        if (cursor.moveToFirst()) {
            do {
                int rid= Integer.parseInt(cursor.getString(0));
                String rdate = cursor.getString(1);
                String rinterest= cursor.getString(2);
                String rprice= cursor.getString(3);
                String rexpiry= cursor.getString(4);
                String rconditions= cursor.getString(5);
                String rcomments= cursor.getString(6);

                storeReport.add(new ReportModelClass(rid,rdate,rinterest,rprice,rexpiry,rconditions,
                        rcomments));

            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeReport;

    }


    public ArrayList<PropertyModelClass> getPropertyList(){

        String sql = "SELECT * FROM "+db_table;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ArrayList<PropertyModelClass>storeProperty=new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        if (cursor.moveToFirst()) {
            do {
                int id= Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String protype= cursor.getString(2);
                String leasetype= cursor.getString(3);
                String bednum= cursor.getString(4);
                String bathnum= cursor.getString(5);
                String size= cursor.getString(6);
                String price= cursor.getString(7);
                String garden= cursor.getString(8);
                String drive= cursor.getString(9);
                String local= cursor.getString(10);
                String des= cursor.getString(11);

                storeProperty.add(new PropertyModelClass(id,name,protype,leasetype,bednum,bathnum,
                        size,price,garden,drive,local,des));

            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeProperty;

    }

    public void updateProperty(PropertyModelClass propertyModelClass){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_Controller.name, propertyModelClass.getName());
        contentValues.put(DB_Controller.protype, propertyModelClass.getProtype());
        contentValues.put(DB_Controller.leasetype, propertyModelClass.getLeasetype());
        contentValues.put(DB_Controller.bednum, propertyModelClass.getBednum());
        contentValues.put(DB_Controller.bathnum, propertyModelClass.getBathnum());
        contentValues.put(DB_Controller.size, propertyModelClass.getSize());
        contentValues.put(DB_Controller.price, propertyModelClass.getPrice());
        contentValues.put(DB_Controller.garden, propertyModelClass.getGarden());
        contentValues.put(DB_Controller.drive, propertyModelClass.getDrive());
        contentValues.put(DB_Controller.local, propertyModelClass.getLocal());
        contentValues.put(DB_Controller.des, propertyModelClass.getDes());

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.update(db_table,contentValues,id+" = ?",new String[]
                {String.valueOf(propertyModelClass.getId())});

    }

    public void deleteAllProperty()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM "+ db_table);
        sqLiteDatabase.execSQL("DELETE FROM "+ db_report_table);
        sqLiteDatabase.close();
    }

    public void deleteProperty(int ID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(db_table,id+" = ? ",new String[]
                {String.valueOf(ID)});
    }

    public void deleteReport(int ID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(db_report_table,rid+" = ? ",new String[]
                {String.valueOf(ID)});
    }

    public PropertyModelClass findProperty(String name, String protype, String leasetype){
        String query ="SELECT * FROM "+db_table+" WHERE "+name+" = "+"name"+" OR "+protype+" = "+"protype"+" OR "+leasetype+" = "+"leasetype";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        PropertyModelClass propertyModelClass = null;
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            int id= Integer.parseInt(cursor.getString(0));
            String fname = cursor.getString(1);
            String fprotype= cursor.getString(2);
            String fleasetype= cursor.getString(3);
            String fbednum= cursor.getString(4);
            String fbathnum= cursor.getString(5);
            String fsize= cursor.getString(6);
            String fprice= cursor.getString(7);
            String fgarden= cursor.getString(8);
            String fdrive= cursor.getString(9);
            String flocal= cursor.getString(10);
            String fdes= cursor.getString(11);

            propertyModelClass = new PropertyModelClass(id,fname,fprotype,fleasetype,fbednum,fbathnum,
                    fsize,fprice,fgarden,fdrive,flocal,fdes);

        }
        cursor.close();
        return propertyModelClass;
    }




}
