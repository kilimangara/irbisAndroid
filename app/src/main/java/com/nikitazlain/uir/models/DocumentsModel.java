package com.nikitazlain.uir.models;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nikitazlain.uir.R;
import com.nikitazlain.uir.controller.RecyclerPagination;
import com.nikitazlain.uir.controller.SwipeBehavior;
import com.nikitazlain.uir.entity.SearchContatiner;
import com.nikitazlain.uir.entity.SearchResultAlt;
import com.nikitazlain.uir.netcore.HttpSender;
import com.nikitazlain.uir.ui.activity.SearchItemActivity;
import com.nikitazlain.uir.ui.adapter.RecyclerViewClick;
import com.nikitazlain.uir.ui.adapter.SearchResultsAdapter;
import com.nikitazlain.uir.view.DocumentsView;
import com.nikitazlain.uir.viewholders.DocumentViewHolder;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class DocumentsModel extends BaseModel<DocumentsView, DocumentViewHolder> implements RecyclerViewClick {

    private final SearchResultsAdapter adapter;

    private SearchContatiner actualSearch;

    public DocumentsModel(DocumentsView view) {
        super(view);
        adapter = new SearchResultsAdapter();
        view.viewHolder.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL ));
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        view.viewHolder.recyclerView.setLayoutManager(manager);
        adapter.setListener(this);
        view.viewHolder.recyclerView.setAdapter(adapter);
        view.viewHolder.recyclerView.addOnScrollListener(new RecyclerPagination(manager) {
            @Override
            protected void loadNextPage() {
                adapter.addPage();
            }

            @Override
            public int getCurrentPage() {
                return adapter.getCurrentPage();
            }

            @Override
            public boolean isLastPage() {
                return adapter.getIsLastPage();
            }
        });
        setSwipeBehavior();
    }

    public void resetModel(){
        adapter.reset();
        view.reset();
    }

    public Observable<SearchContatiner> searchDocuments(String request){
        Log.d("test","searchDocuments inner "+Thread.currentThread().getName());
        return Observable.fromCallable(HttpSender.searchDocuments(request))
                .map(new Function<String, SearchContatiner>() {
                    @Override
                    public SearchContatiner apply(@NonNull String s) throws Exception {
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        Log.d("test","response "+s);
                        return gson.fromJson(s, SearchContatiner.class);
                    }
                });
    }

    public SearchResultsAdapter getAdapter(){
       return adapter;
    }

    public SearchContatiner getActualSearch() {
        return actualSearch;
    }

    public void setActualSearch(SearchContatiner actualSearch) {
        this.actualSearch = actualSearch;
    }

    private void setSwipeBehavior(){
        SwipeBehavior behavior = new SwipeBehavior(getContext(), 0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                switch (direction){
                    case ItemTouchHelper.RIGHT:
                        adapter.pendingAdd(position);
                        break;
                    case ItemTouchHelper.LEFT:
                        adapter.pendingRemoval(position);
                        break;
                }
            }
        };

        ItemTouchHelper touchHelper = new ItemTouchHelper(behavior);
        touchHelper.attachToRecyclerView(view.viewHolder.recyclerView);
        behavior.setLeftcolorCode(ContextCompat.getColor(getContext(), R.color.colorAccentLight));
        behavior.setLeftSwipeLable("Удалить");
        behavior.setRightColorSwipe(ContextCompat.getColor(getContext(), R.color.green));
        behavior.setRightSwipeLabel("Релевантное");
    }

    @Override
    public void click(int pos) {
        Log.d("test","clickcallback "+pos);
        SearchResultAlt resut = adapter.getItem(pos);
        Intent intent = new Intent(getContext(), SearchItemActivity.class);
        intent.putExtra(SearchItemActivity.ANNOTATIONS, resut.getFullDescription());
        getContext().startActivity(intent);
    }
}
