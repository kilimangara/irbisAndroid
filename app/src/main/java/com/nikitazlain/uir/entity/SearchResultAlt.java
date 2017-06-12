package com.nikitazlain.uir.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nikitazlain on 11.06.17.
 */

public class SearchResultAlt {

    @SerializedName("Id")
    private long id;

    @SerializedName("Num")
    private int num;

    @SerializedName("IsRelevant")
    private boolean isRelelevant;

    @SerializedName("IsViewed")
    private boolean isViewed;

    @SerializedName("Title")
    private String title;

    @SerializedName("FullDescription")
    private String fullDescription;

    public boolean isRelelevant() {
        return isRelelevant;
    }

    public boolean isViewed() {
        return isViewed;
    }

    public String getTitle() {
        return title;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setRelelevant(boolean relelevant) {
        isRelelevant = relelevant;
    }

    public void setViewed(boolean viewed) {
        isViewed = viewed;
    }
}
