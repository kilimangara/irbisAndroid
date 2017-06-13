package com.nikitazlain.uir.netcore;

import android.net.Uri;

/**
 * Created by nikitazlain on 30.05.17.
 */

public class RequestUrlBuilder {

    private static final String BASE_URL = "http://irbis.monankov.com";

    private static final String MAIN_SEGMENT = "Main";

    private static final String THESURUS_SEGMENT = "Thesaurus";

    private static final String RUBRICATOR_SEGMENT = "Rubricator";

    private static final String FIND_DOCUMENTS = "FindDocuments";

    private static final String GET_SEARCH_RESULT_BY_PAGE = "GetSerchResultByPage";

    private static final String GET_CHILDREN = "GetChildren";

    private static final String GET_ROOT = "GetRoot";

    private static final String GET_THESAURUSES = "GetThesauruses";

    private static final String GET_RUBRICATORS = "GetRubricators";

    private static final String GET_PROTOCOL = "GetProtocol";

    private static final String SEARCH_HERUISTIC = "SearchHeruistic";

    private static final String SEARCH_FROM_DICTIONARY = "SearchFromDictionary";

    public static String buildThesaurusUrl(String word){
        String pattern = "http://words.bighugelabs.com/api/2/409d5bdec9f7ddf2eb1814e7e90affa4/%s/json";
        return String.format(pattern, word);
    }

    private static String getBaseUrl(){
        return BASE_URL;
    }

    public static Uri buildSearchUrl(){
        return Uri.parse(getBaseUrl()).buildUpon().appendPath(MAIN_SEGMENT)
                .appendPath(FIND_DOCUMENTS).build();
    }

    public static Uri buildThesaurusList(){
        return Uri.parse(getBaseUrl()).buildUpon().appendPath(THESURUS_SEGMENT)
                .appendPath(GET_THESAURUSES).build();
    }

    public static Uri buildFindDocuments(){
        return Uri.parse(getBaseUrl()).buildUpon().appendPath(MAIN_SEGMENT)
                .appendPath(FIND_DOCUMENTS).build();
    }

    public static Uri buildFindDocumentsByPage(){
        return Uri.parse(getBaseUrl()).buildUpon().appendPath(MAIN_SEGMENT)
                .appendPath(GET_SEARCH_RESULT_BY_PAGE).build();
    }

    public static Uri buildGetProtocol(){
        return Uri.parse(getBaseUrl()).buildUpon().appendPath(MAIN_SEGMENT)
                .appendPath(GET_PROTOCOL).build();
    }

    public static Uri buildSearchHeruistic(){
        return Uri.parse(getBaseUrl()).buildUpon().appendPath(MAIN_SEGMENT)
                .appendPath(SEARCH_HERUISTIC).build();
    }


}
