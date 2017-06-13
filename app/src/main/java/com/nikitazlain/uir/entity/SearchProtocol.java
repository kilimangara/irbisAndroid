package com.nikitazlain.uir.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nikitazlain on 26.05.17.
 */

public class SearchProtocol {

    @SerializedName("QueryText")
    private String requestField;

    @SerializedName("Count")
    private int countOfDocs;

    public SearchProtocol(String requestField, int countOfDocs){
        this.requestField = requestField;
        this.countOfDocs = countOfDocs;
    }

    public String getRequestField() {
        return requestField;
    }

    public int getCountOfDocs() {
        return countOfDocs;
    }
}
