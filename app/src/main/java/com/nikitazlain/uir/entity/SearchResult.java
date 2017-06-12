package com.nikitazlain.uir.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nikitazlain on 21.05.17.
 */

public class SearchResult {

    private int typeOfDoc;

    private String language;

    private String origin;

    private List<String> authors;

    private String heading;

    private String outputData;

    private String originDistr;

    private int year;

    private String annotaions;

    private List<String> keyWords;

    public SearchResult(String annotaions, String keyWords){
        this.annotaions = annotaions;
        String[] keyWordsSplit = keyWords.split("\\.");
        this.keyWords = new ArrayList<>();
        for(String item: keyWordsSplit){
            String trimmedItem = item.trim();
            this.keyWords.add(trimmedItem);
        }
    }

    public String getAnnotaions() {
        return annotaions;
    }

    public List<String> getKeyWords() {
        return keyWords;
    }
}
