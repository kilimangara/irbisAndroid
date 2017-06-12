package com.nikitazlain.uir.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikitazlain on 25.05.17.
 */

public class ThesaurusItem {

    @SerializedName("syn")
    private List<String> synonyms;

    @SerializedName("ant")
    private List<String> antonyms;

    @SerializedName("rel")
    private List<String> related;

    @SerializedName("sim")
    private List<String> similar;

    public ThesaurusItem() {
        this.synonyms = new ArrayList<>();
        this.antonyms = new ArrayList<>();
        this.related = new ArrayList<>();
        this.similar = new ArrayList<>();
    }

    public ThesaurusItem(List<String> synonyms, List<String> antonyms, List<String> related, List<String> similar) {
        this.synonyms = synonyms;
        this.antonyms = antonyms;
        this.related = related;
        this.similar = similar;
    }

    public String[] getGroupNames(){
        List<String> strings =new ArrayList<>();
        if(!synonyms.isEmpty()){
            strings.add("Синонимы");
        }
        if(!antonyms.isEmpty()){
            strings.add("Антонимы");
        }
        if(!related.isEmpty()){
            strings.add("Похожие по значению");
        }
        if(!similar.isEmpty()){
            strings.add("Одинаковые по значению");
        }
        return strings.toArray(new String[strings.size()]);
    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }

    public List<String> getAntonyms() {
        return antonyms;
    }

    public List<String> getRelated() {
        return related;
    }

    public List<String> getSimilar() {
        return similar;
    }
}
