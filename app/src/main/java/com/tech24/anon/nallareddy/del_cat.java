package com.tech24.anon.nallareddy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class del_cat extends AppCompatActivity {
    ArrayList<String> catslist;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_cat);
        getSupportActionBar().setTitle("Delete Categories");
        initcat();
    }
    private void initcat(){
        dbhelper getcats = new dbhelper(del_cat.this);
        catslist = getcats.getProCats();
        initlist(catslist);
    }

    private void initlist(ArrayList catslist){
        RecyclerView catsslist = findViewById(R.id.del_cat_recycler);
        RecyclerView.Adapter adapter = new catslist(this, catslist);
        catsslist.setAdapter(adapter);
        catsslist.setLayoutManager(new LinearLayoutManager(this));
    }
}
