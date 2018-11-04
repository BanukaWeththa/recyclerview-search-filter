package com.tech24.anon.nallareddy;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class dbhelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = dbhelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "nallareddy_products.db";

    private static final String CAT_TABLE = "category";
    private static final String CAT_NAME = "name";
    private static final String CAT_ID = "id";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    public dbhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_TABLE =  "CREATE TABLE " + db_contract.proEntry.TABLE_NAME + " ("
                + db_contract.proEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + db_contract.proEntry.PRO_NAME + " TEXT NOT NULL, "
                + db_contract.proEntry.PRO_CAT + " TEXT NOT NULL, "
                + db_contract.proEntry.PRO_ACT_PRICE + " INTEGER NOT NULL, "
                + db_contract.proEntry.PRO_SEL_PRICE + " INTEGER NOT NULL );";

        String SQL_CREATE_CAT =  "CREATE TABLE " + CAT_TABLE + " ("
                + CAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CAT_NAME + " TEXT NOT NULL );";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_TABLE);
        db.execSQL(SQL_CREATE_CAT);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }

    public List<String> getAllCats()
    {
        List<String> catslist =new ArrayList<>();
        //get readable database
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT name FROM category",null);
        if(cursor.moveToFirst())
        {
            do {
                catslist.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        //close the cursor
        cursor.close();
        //close the database
        db.close();
        return catslist;
    }

    public ArrayList<getdata> getAllProds()
    {

        String[] columns = {
                db_contract.proEntry._ID,
                db_contract.proEntry.PRO_NAME,
                db_contract.proEntry.PRO_CAT,
                db_contract.proEntry.PRO_ACT_PRICE,
                db_contract.proEntry.PRO_SEL_PRICE,
        };
        ArrayList<getdata> prodslist =new ArrayList<getdata>();
        //get readable database
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor = db.query(db_contract.proEntry.TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order

        if (cursor.moveToFirst()) {
            do {
                getdata beneficiary = new getdata();
                beneficiary.setIds(cursor.getString(cursor.getColumnIndex(db_contract.proEntry._ID)));
                beneficiary.setProduct_name(cursor.getString(cursor.getColumnIndex(db_contract.proEntry.PRO_NAME)));
                beneficiary.setProduct_category(cursor.getString(cursor.getColumnIndex(db_contract.proEntry.PRO_CAT)));
                beneficiary.setActual_price(cursor.getString(cursor.getColumnIndex(db_contract.proEntry.PRO_ACT_PRICE)));
                beneficiary.setSelling_price(cursor.getString(cursor.getColumnIndex(db_contract.proEntry.PRO_SEL_PRICE)));
                // Adding user record to list
                prodslist.add(beneficiary);
            } while (cursor.moveToNext());
        }
        //close the cursor
        cursor.close();
        //close the database
        db.close();
        return prodslist;
    }

    public ArrayList<String> getAllPods()
    {
        ArrayList<String> podslist =new ArrayList<>();
        //get readable database
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT name FROM products_list",null);
        if(cursor.moveToFirst())
        {
            do {
                podslist.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        //close the cursor
        cursor.close();
        //close the database
        db.close();
        return podslist;
    }

    public ArrayList<String> getProCats()
    {
        ArrayList<String> catslist =new ArrayList<>();
        //get readable database
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT category FROM products_list",null);
        if(cursor.moveToFirst())
        {
            do {
                catslist.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        //close the cursor
        cursor.close();
        //close the database
        db.close();
        return catslist;
    }

    public ArrayList<String> get_act_price()
    {
        ArrayList<String> act_price =new ArrayList<>();
        //get readable database
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT actual_price FROM products_list",null);
        if(cursor.moveToFirst())
        {
            do {
                act_price.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        //close the cursor
        cursor.close();
        //close the database
        db.close();
        return act_price;
    }

    public ArrayList<String> get_sel_price()
    {
        ArrayList<String> sel_price =new ArrayList<>();
        //get readable database
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT selling_price FROM products_list",null);
        if(cursor.moveToFirst())
        {
            do {
                sel_price.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        //close the cursor
        cursor.close();
        //close the database
        db.close();
        return sel_price;
    }

    public ArrayList<String> getIds()
    {
        ArrayList<String> ids =new ArrayList<>();
        //get readable database
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT _ID FROM products_list",null);
        if(cursor.moveToFirst())
        {
            do {
                ids.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        //close the cursor
        cursor.close();
        //close the database
        db.close();
        return ids;
    }
}
