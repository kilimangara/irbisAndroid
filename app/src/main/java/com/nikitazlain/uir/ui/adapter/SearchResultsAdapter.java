package com.nikitazlain.uir.ui.adapter;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikitazlain.uir.R;
import com.nikitazlain.uir.entity.SearchResultAlt;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.SearchViewHolder> {

    private List<SearchResultAlt> searchResults;

    private RecyclerViewClick clickListener;

    private int page;

    private boolean isLastPage = false;

    public SearchResultsAdapter(){
        this.searchResults = new ArrayList<>();
        page = 0;
    }

    public void setResults(List<SearchResultAlt> results){
        this.searchResults = results;
        isLastPage=false;
        notifyDataSetChanged();
    }

    public List<Integer> relevantDocs(){
        List<Integer> rel = new ArrayList<>();
        for(SearchResultAlt searchResultAlt: searchResults){
            if(searchResultAlt.isRelelevant()){
                rel.add(searchResultAlt.getNum());
            }
        }
        return rel;
    }

    public void addPage(List<SearchResultAlt> results){
        if(results.size()==0){
            isLastPage = true;
        } else {
            page++;
            int i =results.size();
            int j =searchResults.size();
            this.searchResults.addAll(results);
            notifyItemRangeChanged(j-1, i);
        }
    }

    public void pendingRemoval(final int position){
        Runnable removale = new Runnable() {
            @Override
            public void run() {
                remove(position);
            }
        };
        new Handler().postDelayed(removale, 1500);
    }

    public SearchResultAlt getItem(int pos){
        return searchResults.get(pos);
    }

    public void remove(int pos){
        searchResults.remove(pos);
        notifyItemRemoved(pos);
    }

    public void pendingAdd(final int position){
        Runnable add = new Runnable() {
            @Override
            public void run() {
                addToRelated(position);
            }
        };
        new Handler().postDelayed(add, 1500);
    }

    public void addToRelated(int pos){
        searchResults.get(pos).setRelelevant(true);
        notifyItemChanged(pos);
    }

    public void setListener(RecyclerViewClick clickListener){
        this.clickListener = clickListener;
    }

    public int getCurrentPage(){
        return page;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_regular_row, parent, false);
        return new SearchViewHolder(view);
    }

    public void reset(){
        searchResults = new ArrayList<>();
        isLastPage =false;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        final int clickPos = holder.getAdapterPosition();
        SearchResultAlt result = searchResults.get(position);
        holder.annotations.setText(result.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test","clicked "+clickPos);
                if(clickListener != null){
                    clickListener.click(clickPos);
                }
            }
        });
        if(result.isRelelevant()) holder.relatedImg.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public boolean getIsLastPage(){
        return isLastPage;
    }

    protected class SearchViewHolder extends RecyclerView.ViewHolder{

        ImageView relatedImg;

        TextView annotations;

        public SearchViewHolder(View itemView) {
            super(itemView);
            annotations = (TextView) itemView.findViewById(R.id.placeholder_annotation);
            relatedImg = (ImageView) itemView.findViewById(R.id.img_related);
        }

    }
}
