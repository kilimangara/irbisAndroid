package com.nikitazlain.uir.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.nikitazlain.uir.R;
import com.nikitazlain.uir.entity.ThesaurusEntity;
import com.nikitazlain.uir.netcore.HttpSender;
import com.nikitazlain.uir.providers.ThesurusProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


public class ThesurusFragment extends Fragment implements Observer<ThesaurusEntity> {

    private final int STATE_IN_SEARCH =0;

    private final int STATE_WITH_LIST =1;

    @BindView(R.id.linear_search_thesaurus)
    LinearLayout searchLinear;

    @BindView(R.id.expand_list_view)
    ExpandableListView listView;

    @BindView(R.id.search_field)
    EditText request;

    @BindView(R.id.button_search)
    Button search;

    @BindView(R.id.progress_data)
    ProgressBar progressBar;

    @BindView(R.id.placeholder_error_searching)
    TextView errorTxt;

    private int currentState;

    private Disposable sub;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thesaurus, container, false);
        ButterKnife.bind(this, view);
        listView.setVisibility(View.GONE);
        currentState = STATE_IN_SEARCH;

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpSender sender = new HttpSender(ThesurusFragment.this);
                sender.getThesaurusByWord(request.getText().toString());
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setUpListView(ThesaurusEntity entity){
        listView.setVisibility(View.VISIBLE);
        currentState = STATE_WITH_LIST;
        Map<String, String> map;
        ArrayList<Map<String,String>> groups = new ArrayList<>();
        if(!entity.allNull()) {
            for (String group : entity.getItem().getGroupNames()) {
                map = new HashMap<>();
                map.put("groupName", group);
                groups.add(map);
            }
        }

        String groupFrom[] = new String[] { "groupName" };
        int groupTo[] = new int[] { android.R.id.text1 };

        ArrayList<ArrayList<Map<String, String>>> сhildDataList = new ArrayList<>();
        ArrayList<Map<String, String>> сhildDataItemList = new ArrayList<>();

        if(entity.getItem().getSynonyms() != null){
            for(String synonyms: entity.getItem().getSynonyms()){
                map = new HashMap<>();
                map.put("itemName",synonyms);
                сhildDataItemList.add(map);
            }
            сhildDataList.add(сhildDataItemList);

            сhildDataItemList = new ArrayList<>();
        }
        if(entity.getItem().getAntonyms() != null){
            for(String synonyms: entity.getItem().getAntonyms()){
                map = new HashMap<>();
                map.put("itemName",synonyms);
                сhildDataItemList.add(map);
            }
            сhildDataList.add(сhildDataItemList);
            сhildDataItemList = new ArrayList<>();
        }
        if(entity.getItem().getRelated() != null){
            for(String synonyms: entity.getItem().getAntonyms()){
                map = new HashMap<>();
                map.put("itemName",synonyms);
                сhildDataItemList.add(map);
            }
            сhildDataList.add(сhildDataItemList);
            сhildDataItemList = new ArrayList<>();
        }
        if(entity.getItem().getSimilar() != null){
            for(String synonyms: entity.getItem().getAntonyms()){
                map = new HashMap<>();
                map.put("itemName",synonyms);
                сhildDataItemList.add(map);
            }
            сhildDataList.add(сhildDataItemList);
            сhildDataItemList = new ArrayList<>();
        }

        String childFrom[] = new String[] { "itemName" };
        int childTo[] = new int[] { android.R.id.text1 };

        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                getActivity(), groups,
                android.R.layout.simple_expandable_list_item_1, groupFrom,
                groupTo, сhildDataList, android.R.layout.simple_list_item_1,
                childFrom, childTo);
        listView.setAdapter(adapter);
    }

    public void backBehavior(boolean withError){
        listView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        searchLinear.setVisibility(View.VISIBLE);
        errorTxt.setVisibility(withError ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        sub = d;
        searchLinear.setVisibility(View.GONE);
        errorTxt.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNext(@NonNull ThesaurusEntity thesaurusEntity) {
        progressBar.setVisibility(View.GONE);
        setUpListView(thesaurusEntity);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        backBehavior(true);
    }

    @Override
    public void onComplete() {
        sub.dispose();
    }
}
