package com.nikitazlain.uir.netcore;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nikitazlain.uir.entity.ThesaurusEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpSender {

    private static final String UTID = "20170322115559";

    private static final String LIMIT = "10";

    private static final int LIMIT_INT = 10;

    private static final String SEARCH_TYPE = "Поисковое условие";

    public BehaviorSubject<ThesaurusEntity> subject;

    public HttpSender(Observer<ThesaurusEntity> context){
        subject = BehaviorSubject.create();
        subject.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(context);
    }

    public void getThesaurusByWord(String word){
        OkHttpClient client = new OkHttpClient();
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Request request = new Request.Builder().url(RequestUrlBuilder.buildThesaurusUrl(word)).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                subject.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resp = response.body().string();
                subject.onNext(gson.fromJson(resp,ThesaurusEntity.class));
                subject.onComplete();
            }
        });
    }


    public static Callable<String> searchDocuments(String reques){
        final OkHttpClient client = new OkHttpClient();
        FormBody formBody = new FormBody.Builder().add("utid", UTID)
                .add("limit",LIMIT).add("field1",SEARCH_TYPE).add("sent1",reques).build();
        Log.d("test", "request search documents "+RequestUrlBuilder.buildFindDocuments());
        final Request request = new Request.Builder().url(RequestUrlBuilder.buildFindDocuments().toString())
                .post(formBody).build();
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                return client.newCall(request).execute().body().string();
            }
        } ;
    }

    public static Callable<String> searchDocumentsByPage(String isent, int page, Map<Integer,Boolean> relevantDocs){
        final OkHttpClient client = new OkHttpClient();
        FormBody formBody = new FormBody.Builder().add("utid",UTID).add("limit",LIMIT)
                .add("offset",String.valueOf(page*LIMIT_INT)).add("isent",isent)
                .add("relevantDocs", formatHashMap(relevantDocs))
                .build();
        final Request request = new Request.Builder().url(RequestUrlBuilder.buildFindDocumentsByPage().toString())
                .post(formBody).build();
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                return client.newCall(request).execute().body().string();
            }
        };
    }

    private static String formatHashMap(Map<Integer, Boolean> map){
        return map.toString().replace("{","").replace("}","").replace("=",":");
    }



}
