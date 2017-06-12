package com.nikitazlain.uir.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nikitazlain on 25.05.17.
 */

public class ThesaurusEntity {

    @SerializedName(value = "verb")
    private ThesaurusItem verb;

    @SerializedName(value = "noun")
    private ThesaurusItem noun;

    @SerializedName(value = "adjective")
    private ThesaurusItem adjective;

    public ThesaurusItem getVerb() {
        return verb;
    }

    public ThesaurusItem getNoun() {
        return noun;
    }

    public ThesaurusItem getAdjective() {
        return adjective;
    }

    public boolean allNull(){
        return verb == null && noun == null && adjective == null;
    }

    public ThesaurusItem getItem(){
        if(verb !=null){
            return verb;
        }
        if(noun !=null){
            return noun;
        }
        if(adjective !=null){
            return adjective;
        }
        return null;
    }

    public ThesaurusEntity(ThesaurusItem item) {
        this.verb = item;
    }
}
