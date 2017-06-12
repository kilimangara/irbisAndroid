package com.nikitazlain.uir.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikitazlain.uir.R;
import com.nikitazlain.uir.entity.Database;
import com.nikitazlain.uir.persistance.SharedPreferencesWork;

import java.util.List;


public class DatabasesAdapter extends RecyclerView.Adapter<DatabasesAdapter.DatabaseViewHolder> {

    private List<Database> databases;

    private RecyclerViewClick clickListener;

    public DatabasesAdapter(List<Database> databases){
        this.databases = databases;
    }

    @Override
    public DatabaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_database, parent, false);
        return new DatabaseViewHolder(view);
    }

    public void setListener(RecyclerViewClick listener){
        this.clickListener = listener;
    }

    @Override
    public void onBindViewHolder(DatabaseViewHolder holder, int position) {
        final int clposition = holder.getAdapterPosition();
        if(position == SharedPreferencesWork.getInstance().getDB()){
            holder.imageView.setVisibility(View.VISIBLE);
        } else {
            holder.imageView.setVisibility(View.GONE);
        }
        holder.database.setText(databases.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(clickListener!=null){
                    clickListener.click(clposition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return databases.size();
    }

    protected class DatabaseViewHolder extends RecyclerView.ViewHolder{

        TextView database;
        ImageView imageView;

        public DatabaseViewHolder(View itemView) {
            super(itemView);
            database = (TextView) itemView.findViewById(R.id.txt_database);
            imageView = (ImageView) itemView.findViewById(R.id.img_checked);
        }
    }
}
