package com.nikitazlain.uir.providers;

import com.nikitazlain.uir.entity.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikitazlain on 23.05.17.
 */

public class SettingsProvider {

    public static List<Database> provideDatabases(){
        List<Database> databases = new ArrayList<>();
        databases.add(new Database(" inis/INIS (1970-2008) "));
        databases.add(new Database(" ikn/ИК НИР (ВНТИЦ 1984-2008) "));
        databases.add(new Database(" vinmif Рефераты ВИНИТИ (1998-2007) "));
        return databases;
    }

    public static Database getDatabase(int index){
        return provideDatabases().get(index);
    }
}
