package com.nikitazlain.uir.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nikitazlain.uir.R;
import com.nikitazlain.uir.entity.SearchProtocol;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nikitazlain on 26.05.17.
 */

public class SearchProtocolsAdapter extends RecyclerView.Adapter<SearchProtocolsAdapter.SearchProtocolViewHolder> {


    private List<SearchProtocol> list;

    public SearchProtocolsAdapter(List<SearchProtocol> res){
        list = res;
    }

    @Override
    public SearchProtocolViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_protocol, parent, false);
        return new SearchProtocolViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchProtocolViewHolder holder, int position) {
        SearchProtocol protocol = list.get(position);
        holder.count.setText(protocol.getCountOfDocs()+" документов");
        holder.request.setText(protocol.getRequestField());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected class SearchProtocolViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.txt_request_text)
        TextView request;

        @BindView(R.id.txt_count_of_docs)
        TextView count;

        public SearchProtocolViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
