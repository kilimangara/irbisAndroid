package com.nikitazlain.uir.persistance;

import android.content.Context;

import com.orhanobut.hawk.Hawk;

public class SharedPreferencesWork {

    private static SharedPreferencesWork instance;

    public static SharedPreferencesWork getInstance(){
        if(instance == null){
            instance =  new SharedPreferencesWork();
        }
        return instance;
    }

    public void init(Context context){
        Hawk.init(context).build();
    }

    public void saveToken(String token){
        Hawk.put(SharedPreferencesConstants.IS_AUTH, true);
        Hawk.put(SharedPreferencesConstants.PREF_TOKEN, token);
    }

    public String getToken(){
        return Hawk.get(SharedPreferencesConstants.PREF_TOKEN);
    }

    public boolean isAuth(){
        return Hawk.get(SharedPreferencesConstants.IS_AUTH, false);
    }

    public void saveDB(int index){
        Hawk.put(SharedPreferencesConstants.DATABASE, index);
    }

    public int getDB(){
        return Hawk.get(SharedPreferencesConstants.DATABASE, 0);
    }

    public void saveName(String name){
        Hawk.put(SharedPreferencesConstants.NAME, name);
    }

    public String getName(){
        return Hawk.get(SharedPreferencesConstants.NAME,"Гость");
    }

}
