package com.tech24.anon.nallareddy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class catslist extends RecyclerView.Adapter<catslist.ViewHolder>{
    private  ArrayList<String> catslist;
    private int clickCount;
    private String name;
    private Context ncontext;


    public catslist(Context ncontext, ArrayList<String> catslist) {
        this.catslist = catslist;
        this.ncontext = ncontext;
    }


    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, final int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_list, parent, false);
        catslist.ViewHolder holder = new catslist.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, final int position) {
        holder.catsview.setText(catslist.get(position));
        holder.catsview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    name = catslist.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(ncontext);
                builder.setTitle("Delete");
                builder.setMessage("Do you want to Delete : " + name + " Category?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        delete();

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return catslist.size();

    }



    private void delete(){

        Log.d("tag", "delete: " + name);
        dbhelper catdb = new dbhelper(ncontext);
        SQLiteDatabase db = catdb.getWritableDatabase();

        String where = "name=?";
        String[] whereArgs = new String[] {name};
        long newRowId = db.delete("category", where, whereArgs);
        if (newRowId == 1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(ncontext, "Deleted", Toast.LENGTH_SHORT).show();
            Intent mainintent = new Intent(ncontext, del_cat.class);
            ncontext.startActivity(mainintent);
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(ncontext, "Something Wrong at " + newRowId, Toast.LENGTH_SHORT).show();

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView catsview;

        public ViewHolder(View itemView) {
            super(itemView);
           catsview = itemView.findViewById(R.id.cat_text);
        }
    }
}
