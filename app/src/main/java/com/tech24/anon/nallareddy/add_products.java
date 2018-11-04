package com.tech24.anon.nallareddy;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class add_products extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ArrayList<String> arrayList;
    private String cat_name;
    Spinner spinner;
    EditText pname, sprice, bprice;
    Button pbutton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_products);
        spinner = (Spinner) findViewById(R.id.spinner);
        loadSpinnerData();
        pname = (EditText) findViewById(R.id.pname);
        sprice = (EditText) findViewById(R.id.sprice);
        bprice = (EditText) findViewById(R.id.bprice);
        pbutton = (Button) findViewById(R.id.pbutton);
        spinner.setOnItemSelectedListener(this);
        buttonClick();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView tv = (TextView)view;
        cat_name = tv.getText().toString();
        Log.d("TAG", "onItemSelected: " +cat_name);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        cat_name = "All";
    }

    private void buttonClick(){
        pbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prod_name = pname.getText().toString().trim();
                String a_price = bprice.getText().toString().trim();
                String s_price = sprice.getText().toString().trim();
                checkAlreadyExist(prod_name, a_price, s_price);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.Search_products) {
            Intent intent = new Intent(add_products.this, add_cat.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.delete_products) {
            return true;
        }
        if (id == R.id.update_products) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
    /**
     * Function to load the spinner data from SQLite database
     * */
    private void loadSpinnerData() {
        dbhelper db = new dbhelper(this);
        List<String> cats = db.getAllCats();
        if(cats.isEmpty()){
            Toast.makeText(this, "No Cats Available", Toast.LENGTH_SHORT).show();
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, cats);
        //Type of spinner
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    public boolean checkAlreadyExist(String pro_name, String a_price, String s_price)
    {
        if (pro_name.length() != 0) {
            dbhelper checkdb = new dbhelper(this);
            SQLiteDatabase db = checkdb.getReadableDatabase();
            String query = " SELECT name FROM products_list WHERE name = ?";
            Log.d("query", " " + query);
            Cursor cursor = db.rawQuery(query, new String[]{pro_name});
            if (cursor.getCount() > 0) {
                Toast.makeText(add_products.this, "" + pro_name + " already added", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                submitform(pro_name, a_price, s_price);
                return true;
            }
        }else {
            Toast.makeText(add_products.this, "Please Enter Category Name", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private void submitform(String pro_name, String a_price, String s_price){

        Log.d("tag", "submitform: " + cat_name);
        if ((pro_name.length() != 0) && (a_price.length() != 0) && (s_price.length() != 0)){
            dbhelper catdb = new dbhelper(this);
            SQLiteDatabase db = catdb.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("name", pro_name);
            values.put("category", cat_name);
            values.put("actual_price", a_price);
            values.put("selling_price", s_price);

            long newRowId = db.insert("products_list", null, values);
            if (newRowId == -1) {
                // If the row ID is -1, then there was an error with insertion.
                Toast.makeText(this, "Something Wrong", Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast with the row ID.
                Toast.makeText(this, "Category Added : " + pro_name + " at " + newRowId, Toast.LENGTH_SHORT).show();
                pname.getText().clear();
                sprice.getText().clear();
                bprice.getText().clear();
            }
        }else{
            Toast.makeText(add_products.this, "Please Enter Category Name", Toast.LENGTH_SHORT).show();
        }
    }


}
