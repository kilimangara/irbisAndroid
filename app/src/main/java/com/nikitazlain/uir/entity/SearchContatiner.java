package com.nikitazlain.uir.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchContatiner {

    @SerializedName("rows")
    private List<SearchResultAlt> results;

    @SerializedName("total")
    private int total;

    @SerializedName("isent")
    private long isent;

    public List<SearchResultAlt> getResults() {
        return results;
    }

    public int getTotal() {
        return total;
    }

    public long getIsent() {
        return isent;
    }
}
