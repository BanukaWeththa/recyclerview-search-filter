package com.tech24.anon.nallareddy;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import static android.icu.text.MessagePattern.ArgType.SELECT;
import static com.tech24.anon.nallareddy.db_contract.proEntry._ID;

public class add_cat extends AppCompatActivity {

    Button editbutton;
    EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_category);
        AddDefaultCat();
        editbutton = (Button) findViewById(R.id.edit_cat_button);
        editText = (EditText) findViewById(R.id.edit_cat1);
        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cat_name = editText.getText().toString().trim();
               checkAlreadyExist(cat_name);
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
            Intent intent = new Intent(add_cat.this, add_cat.class);
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


    public boolean checkAlreadyExist(String cat_name)
    {
        if (cat_name.length() != 0) {
            dbhelper checkdb = new dbhelper(this);
            SQLiteDatabase db = checkdb.getReadableDatabase();
            String query = " SELECT name FROM category WHERE name = ?";
            Log.d("query", " " + query);
            Cursor cursor = db.rawQuery(query, new String[]{cat_name});
            if (cursor.getCount() > 0) {
                Toast.makeText(add_cat.this, "" + cat_name + " already added", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                submitform(cat_name);
                editText.getText().clear();
                return true;
            }
        }else {
            Toast.makeText(add_cat.this, "Please Enter Category Name", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private void AddDefaultCat(){
        dbhelper defdb = new dbhelper(add_cat.this);
        List<String> cats = defdb.getAllCats();
        if(cats.isEmpty()){
            Toast.makeText(this, "No Cats Available", Toast.LENGTH_SHORT).show();
            dbhelper defcatdb = new dbhelper(this);
            SQLiteDatabase defcatadddb = defcatdb.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("name", "All");

            long newRowId = defcatadddb.insert("category", null, values);
            if (newRowId == -1) {
                // If the row ID is -1, then there was an error with insertion.
                Toast.makeText(this, "Something Wrong", Toast.LENGTH_SHORT).show();
                editText.setText(" ");
            } else {
                // Otherwise, the insertion was successful and we can display a toast with the row ID.
                Toast.makeText(this, "Default Category Added : " +
                        " at " + newRowId, Toast.LENGTH_SHORT).show();
            }
        }

        }

    private void submitform(String cat_name){

        Log.d("tag", "submitform: " + cat_name);
       if (cat_name.length() != 0){
           dbhelper catdb = new dbhelper(this);
           SQLiteDatabase db = catdb.getWritableDatabase();

           ContentValues values = new ContentValues();
           values.put("name", cat_name);

           long newRowId = db.insert("category", null, values);
           if (newRowId == -1) {
               // If the row ID is -1, then there was an error with insertion.
               Toast.makeText(this, "Something Wrong", Toast.LENGTH_SHORT).show();
               editText.setText(" ");
           } else {
               // Otherwise, the insertion was successful and we can display a toast with the row ID.
               Toast.makeText(this, "Category Added : " + cat_name + " at " + newRowId, Toast.LENGTH_SHORT).show();
           }
       }else{
           Toast.makeText(add_cat.this, "Please Enter Category Name", Toast.LENGTH_SHORT).show();
       }
    }
}
