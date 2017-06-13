package com.nikitazlain.uir.providers;

import com.nikitazlain.uir.entity.SearchProtocol;

import java.util.ArrayList;
import java.util.List;


public class SearchProvider {

    private final static int COUNT_ON_PAGE=3;

    public static List<SearchProtocol> getSearchProtocols(){
        List<SearchProtocol> searchProtocols = new ArrayList<>();
        searchProtocols.add(new SearchProtocol("Мобиьные технологии", 10));
        searchProtocols.add(new SearchProtocol("(KW:(('Поисковые технологии')AND('Адаптация'))",34));
        searchProtocols.add(new SearchProtocol("Адаптация веб-приложения", 178));
        searchProtocols.add(new SearchProtocol("(KW:(('Графический интерфейс')OR('Психологический интерфейс'))", 877));
        searchProtocols.add(new SearchProtocol("Интеллектуальные системы", 310));
        return searchProtocols;
    }
}
