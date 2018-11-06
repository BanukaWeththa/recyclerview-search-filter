package com.tech24.anon.nallareddy;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class edit_view extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText edit_pro;
    int clickCount;
    private String id;
    EditText edit_s_price;
    EditText edit_a_price;
    Spinner spinner;
    private String cat_name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_text);
        getSupportActionBar().setTitle("Edit Products");
        getIncomingIntent();
        final Button delete = (Button) findViewById(R.id.edit_del_button);
        Button edit = (Button) findViewById(R.id.edit_pbutton);
        edit_pro = (EditText) findViewById(R.id.edit_pname);
        edit_a_price = (EditText) findViewById(R.id.edit_bprice);
        edit_s_price = (EditText)findViewById(R.id.edit_sprice);
        spinner = (Spinner) findViewById(R.id.edit_spinner);
        loadSpinnerData();
        spinner.setOnItemSelectedListener(this);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount++;
                if(clickCount > 2) {
                    delete();
                }
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prod_name = edit_pro.getText().toString().trim();
                String a_price = edit_a_price.getText().toString().trim();
                String s_price = edit_s_price.getText().toString().trim();
                submitform(prod_name, a_price, s_price);
            }
        });
    }

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


    private void getIncomingIntent(){
        if(getIntent().hasExtra("pro_name") && getIntent().hasExtra("cat_name") && getIntent().hasExtra("a_price") && getIntent().hasExtra("s_price")&& getIntent().hasExtra("id")){
            String pro_name = getIntent().getStringExtra("pro_name");
            String cat_name = getIntent().getStringExtra("cat_name");
            String a_price = getIntent().getStringExtra("a_price");
            String s_price = getIntent().getStringExtra("s_price");
            id = getIntent().getStringExtra("id");
            setEditText(pro_name, cat_name, a_price, s_price);
        }
    }

    private void setEditText(String pro_name, String cat_name, String a_price, String s_price){
        EditText p_name = (EditText) findViewById(R.id.edit_pname);
        p_name.setText(pro_name);

        EditText aprice = (EditText) findViewById(R.id.edit_bprice);
        aprice.setText(a_price);

        EditText sprice = (EditText) findViewById(R.id.edit_sprice);
        sprice.setText(s_price);

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
            String where = "_ID=?";
            String[] whereArgs = new String[] {String.valueOf(id)};
            long newRowId = db.update("products_list", values, where, whereArgs);
            if (newRowId == -1) {
                // If the row ID is -1, then there was an error with insertion.
                Toast.makeText(this, "Something Wrong", Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast with the row ID.
                Toast.makeText(this, "Product Updated : " + pro_name + " at " + newRowId, Toast.LENGTH_SHORT).show();
                edit_pro.getText().clear();
                edit_s_price.getText().clear();
                edit_a_price.getText().clear();
                Intent mainintent = new Intent(edit_view.this, MainActivity.class);
                startActivity(mainintent);
            }
        }else{
            Toast.makeText(edit_view.this, "Please Enter Product Details", Toast.LENGTH_SHORT).show();
        }
    }

    private void delete(){

        Log.d("tag", "delete: " + id);
            dbhelper catdb = new dbhelper(this);
            SQLiteDatabase db = catdb.getWritableDatabase();

            String where = "_ID=?";
            String[] whereArgs = new String[] {String.valueOf(id)};
            long newRowId = db.delete("products_list", where, whereArgs);
            if (newRowId == 1) {
                // If the row ID is -1, then there was an error with insertion.
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                Intent mainintent = new Intent(edit_view.this, MainActivity.class);
                startActivity(mainintent);
            } else {
                // Otherwise, the insertion was successful and we can display a toast with the row ID.
                Toast.makeText(this, "Something Wrong at " + newRowId, Toast.LENGTH_SHORT).show();
                edit_pro.getText().clear();
                edit_s_price.getText().clear();
                edit_a_price.getText().clear();
            }
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
}
