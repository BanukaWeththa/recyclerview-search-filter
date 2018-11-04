package com.tech24.anon.nallareddy;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class recyclerviewadapter extends RecyclerView.Adapter<recyclerviewadapter.ViewHolder> implements Filterable {
    private ArrayList<getdata> products = new ArrayList<>();
    private ArrayList<String> pro_names = new ArrayList<>();
    private ArrayList<String> cat_names = new ArrayList<>();
    private ArrayList<String> a_price = new ArrayList<>();
    private ArrayList<String> s_price = new ArrayList<>();
    private ArrayList<String> ids = new ArrayList<>();
    private ArrayList<getdata> filteredlist = new ArrayList<>();
    private ArrayList<String> filter_cat_names = new ArrayList<>();
    private ArrayList<String> filter_a_price = new ArrayList<>();
    private ArrayList<String> filter_s_price = new ArrayList<>();
    private ArrayList<String> filter_ids = new ArrayList<>();
    private Context mcontext;

    public recyclerviewadapter(Context mcontext, ArrayList<String> pro_names, ArrayList<String> cat_names, ArrayList<String> a_price, ArrayList<String> s_price, ArrayList<String> ids, ArrayList<getdata> products) {
        this.products = products;
        this.filteredlist = products;
        this.pro_names = pro_names;
        this.cat_names = cat_names;
        this.a_price = a_price;
        this.s_price = s_price;
        this.ids = ids;
        this.mcontext = mcontext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: Called on");
        holder.p_name.setText(filteredlist.get(position).getProduct_name());
        holder.c_name.setText(filteredlist.get(position).getProduct_category());
        holder.sprice.setText(filteredlist.get(position).getSelling_price());
        holder.aprice.setText(filteredlist.get(position).getActual_price());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, edit_view.class);
                intent.putExtra("pro_name", filteredlist.get(position).getProduct_name());
                intent.putExtra("cat_name", filteredlist.get(position).getProduct_category());
                intent.putExtra("a_price", filteredlist.get(position).getActual_price());
                intent.putExtra("s_price", filteredlist.get(position).getSelling_price());
                intent.putExtra("id", filteredlist.get(position).getIds());
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredlist.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                   filteredlist = products;
                } else {
                    ArrayList<getdata> ProductsfilteredList = new ArrayList<>();
                    for (getdata row : products) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getProduct_name().toLowerCase().contains(charString.toLowerCase())) {
                            ProductsfilteredList.add(row);
                        }
                    }

                    filteredlist = ProductsfilteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredlist;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredlist = (ArrayList<getdata>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
       TextView p_name;
       TextView c_name;
       TextView aprice;
       TextView sprice;
       CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            p_name = itemView.findViewById(R.id.pro_name);
            c_name = itemView.findViewById(R.id.pro_cat);
            aprice = itemView.findViewById(R.id.sprice);
            sprice = itemView.findViewById(R.id.bprice);
            cardView = itemView.findViewById(R.id.card);

        }
    }
}
