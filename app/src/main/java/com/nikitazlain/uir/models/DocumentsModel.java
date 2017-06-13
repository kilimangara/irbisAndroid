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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class DocumentsModel extends BaseModel<DocumentsView, DocumentViewHolder> implements RecyclerViewClick {

    private final SearchResultsAdapter adapter;

    private SearchContatiner actualSearch;

    private Observable<RelevantDocs> eventObservable;

    public DocumentsModel(final DocumentsView view) {
        super(view);
        adapter = new SearchResultsAdapter();
        view.viewHolder.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL ));
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        view.viewHolder.recyclerView.setLayoutManager(manager);
        adapter.setListener(this);
        view.viewHolder.recyclerView.setAdapter(adapter);
        eventObservable=Observable.create(new ObservableOnSubscribe<RelevantDocs>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<RelevantDocs> e) throws Exception {
                view.viewHolder.recyclerView.addOnScrollListener(new RecyclerPagination(manager) {
                    @Override
                    protected void loadNextPage() {
                        e.onNext(new RelevantDocs(getActualSearch().getIsent(), adapter.relevantDocs(), adapter.getCurrentPage()+1));
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

    public Observable<SearchContatiner> searchDocumentsByPage(RelevantDocs docs){
        Log.d("test","searchDocumentsByPage inner "+Thread.currentThread().getName() +" page "+docs.page);
        return Observable.fromCallable(HttpSender.searchDocumentsByPage(String.valueOf(docs.getIsent()),
                docs.getPage(), docs.getRelevantDocs())).map(new Function<String, SearchContatiner>() {
            @Override
            public SearchContatiner apply(@NonNull String s) throws Exception {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Log.d("test","response "+s);
                return gson.fromJson(s, SearchContatiner.class);
            }
        });
    }

    public Observable<RelevantDocs> scrollList(){
        return eventObservable;
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

    public class RelevantDocs{

        List<Integer> relevantDocs;

        long isent;

        int page;

        protected RelevantDocs(long isent, List<Integer> relevantDocs, int page){
            this.relevantDocs = relevantDocs;
            this.isent = isent;
            this.page =page;
        }

        public Map<Integer,Boolean> getRelevantDocs() {
            Map<Integer, Boolean> map = new HashMap<>();
            for(Integer integer: relevantDocs){
                map.put(integer, true);
            }
            return map;
        }

        public long getIsent() {
            return isent;
        }

        public int getPage() {
            return page;
        }
    }
}
