package com.nikitazlain.uir.presenters;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.nikitazlain.uir.entity.SearchContatiner;
import com.nikitazlain.uir.models.DocumentsModel;
import com.nikitazlain.uir.ui.activity.NavigationActivity;
import com.nikitazlain.uir.view.DocumentsView;
import com.nikitazlain.uir.viewholders.DocumentViewHolder;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class DocumentsPresenter extends BasePresenter<DocumentsView, DocumentsModel, DocumentViewHolder> {

    private CompositeDisposable compositeDisposable;

    public DocumentsPresenter(DocumentsView view, DocumentsModel model) {
        super(view, model);
    }

    @Override
    public void onCreate(Activity activity, Bundle savedInstance) {
        compositeDisposable = new CompositeDisposable();
        bindQuery(((NavigationActivity)activity).getQueryObservable());
        bindScroll();
    }

    @Override
    public void onResume() {
        hideKeyboard();
    }

    @Override
    public void onDestroy() {
        if(compositeDisposable!=null && !compositeDisposable.isDisposed()) compositeDisposable.dispose();
    }

    private void bindScroll(){
        model.scrollList().debounce(500, TimeUnit.MILLISECONDS).switchMap(new Function<DocumentsModel.RelevantDocs, ObservableSource<SearchContatiner>>() {
            @Override
            public ObservableSource<SearchContatiner> apply(@NonNull DocumentsModel.RelevantDocs docs) throws Exception {
                return model.searchDocumentsByPage(docs);
            }
        }).onErrorResumeNext(new Function<Throwable, ObservableSource<? extends SearchContatiner>>() {
            @Override
            public ObservableSource<? extends SearchContatiner> apply(@NonNull Throwable throwable) throws Exception {
                Log.d("test","onErrorResume "+throwable.getClass());
                return Observable.empty();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<SearchContatiner>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                compositeDisposable.add(d);
                Log.d("test","OnSubscribe "+Thread.currentThread().getName());
            }

            @Override
            public void onNext(@NonNull SearchContatiner searchContatiner) {
                Log.d("test","onNext "+Thread.currentThread().getName());
                model.getAdapter().addPage(searchContatiner.getResults());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                showError();
            }

            @Override
            public void onComplete() {
                Log.d("test","onComplete");
            }
        });
    }

    public void bindQuery(Observable<String> queyObservable){
        queyObservable
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        Log.d("test","doOnNextBeforeSwitchMap "+Thread.currentThread().getName());
                        startSearching();
                    }
                }).observeOn(Schedulers.newThread())
                .switchMap(new Function<String, ObservableSource<SearchContatiner>>() {
                    @Override
                    public ObservableSource<SearchContatiner> apply(@NonNull String s) throws Exception {
                        Log.d("test","switchMap "+Thread.currentThread().getName());
                        return model.searchDocuments(s);
                    }
                }).retry(3)
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends SearchContatiner>>() {
                    @Override
                    public ObservableSource<? extends SearchContatiner> apply(@NonNull Throwable throwable) throws Exception {
                        Log.d("test","onErrorResume "+throwable.getClass());
                        return Observable.empty();
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchContatiner>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d("test","OnSubscribe"+Thread.currentThread().getName());
                DocumentsPresenter.this.compositeDisposable.add(d);
            }

            @Override
            public void onNext(@NonNull SearchContatiner searchContatiner) {
                Log.d("test","OnSubscribe"+Thread.currentThread().getName());
                if(searchContatiner!=null) {
                    if (searchContatiner.getTotal() != 0) {
                        setResults(searchContatiner);
                    } else {
                        showNotFound();
                    }
                } else {
                    showError();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("test","OnError "+e.getLocalizedMessage()+" "+e.getClass());
                resetPresenter();
                showError();
            }

            @Override
            public void onComplete() {
                Log.d("test","onComplete");
                //if(disposable!=null && !disposable.isDisposed()) disposable.dispose();
            }
        });
    }

    public void setResults(SearchContatiner results){
        model.getAdapter().setResults(results.getResults());
        model.setActualSearch(results);
        view.endSerching();
    }

    public void startSearching(){
        hideKeyboard();
        view.startSearching();
    }

    public void resetPresenter(){
        model.resetModel();
    }

    public void showNotFound(){
        view.showToast("Ничего не найдено");
    }

    public void showError(){
        view.showToast("Ошибка поиска");
    }
}
