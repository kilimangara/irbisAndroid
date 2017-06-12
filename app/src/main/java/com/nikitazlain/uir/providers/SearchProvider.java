package com.nikitazlain.uir.providers;

import com.nikitazlain.uir.entity.SearchProtocol;
import com.nikitazlain.uir.entity.SearchResult;

import java.util.ArrayList;
import java.util.List;


public class SearchProvider {

    private final static int COUNT_ON_PAGE=3;

    public static List<SearchResult> getSearchResults(){
        List<SearchResult> searchResults =  new ArrayList<>();
        searchResults.add(new SearchResult("Все предельно просто: на повестке дня обсуждение мобильно" + "      платформы Samsung модели X20 CV02. При первом взгляде на \"блок-нотник\"" +
                "      автор пришел к такому выводу: несмотря на обычный набор интерфейсов и " +
                "      слотов расширения, находящихся на бортах аппарата, складывается  " +
                "      впечатление, будто их нет и в помине.","Ноутбуки. Samsung X20 CV02. мобильная платформа. дизайн"));
        searchResults.add(new SearchResult("Корпорацией Хитати сэйсакусе, Япония предложены технологии" +
                "      планирования процесса выполнения работ на установках энергосистем при  " +
                "      выходе из рабочего режима. Обеспечен учет ограничивающих условий по ","Электроэнергетические системы. поисковые технологии "));
        searchResults.add(new SearchResult("Для поиска информации в сети используется интеллектуальная      \n" +
                "      поисковая машина. Это программа, которая запрашивает информацию из      \n" +
                "      удаленных участков Интернета, используя машины поиска.","Информационные сети. Интернет. Информационный поиск." +
                "      интеллектуальные поисковые машины. Information networks. Internet." +
                "      Information retrieval. intelligent search engines "));
        searchResults.add(new SearchResult(" Приведен сравнительный анализ 8 известных поисковых систем,     \n" +
                "      ориентированных на поиск информации в сети intranet (внутренняя сеть    \n" +
                "      предприятия). Для сравнения систем выбран набор следующих критериев:    \n" +
                "      поддерживаемые файловые форматы, поддерживаемые языки, возможнос","Информационные сети. intranet. информационный поиск." +
                "      поисковые машины. критерии выбора. операционные системы." +
                "      Information networks. intranet. information retrieval. search engines." +
                "      selection criteria. operating systems "));
        searchResults.add(new SearchResult("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took " +
                "a galley of type and scrambled it to make a type specimen book","KW1.KW2.KW3"));
        return searchResults;
    }

    public static List<SearchResult> getSearchResult(int page){
        List<SearchResult> searchResults = new ArrayList<>();
        List<SearchResult> allResults = getSearchResults();
        try {
        for(int i=COUNT_ON_PAGE*page; i<COUNT_ON_PAGE*(page+1);i++){
            searchResults.add(allResults.get(i));
        }
        } catch (IndexOutOfBoundsException ignored){

        }
        return searchResults;
    }

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
