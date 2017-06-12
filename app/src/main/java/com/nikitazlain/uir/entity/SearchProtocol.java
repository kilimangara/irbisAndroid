package com.nikitazlain.uir.entity;

/**
 * Created by nikitazlain on 26.05.17.
 */

public class SearchProtocol {

    private String requestField;

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
