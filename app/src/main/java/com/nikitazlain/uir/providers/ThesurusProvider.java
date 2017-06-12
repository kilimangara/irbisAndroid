package com.nikitazlain.uir.providers;

import com.nikitazlain.uir.entity.ThesaurusEntity;
import com.nikitazlain.uir.entity.ThesaurusItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nikitazlain on 26.05.17.
 */

public class ThesurusProvider {

    public static List<ThesaurusEntity> getThesurusEntity(){
        List<ThesaurusEntity> list = new ArrayList<>();
        List<String> scienceSynonyms = Arrays.asList("scientific discipline", "subject area", "discipline",
                "branch of knowledge","ability");
        ThesaurusItem science = new ThesaurusItem(scienceSynonyms, null, null, null);
        List<String> loveSynonyms = Arrays.asList("passion","dear","emotion");
        List<String> loveantonyms = Arrays.asList("hate");
        ThesaurusItem love = new ThesaurusItem(loveSynonyms, loveantonyms, null, null);
        list.add(new ThesaurusEntity(science));
        list.add(new ThesaurusEntity(love));
        return list;
    }

    public static ThesaurusEntity getThesaurusEntity(int i){
        if(i>1){
            return getThesurusEntity().get(1);
        } else {
            return getThesurusEntity().get(i);
        }
    }
}
