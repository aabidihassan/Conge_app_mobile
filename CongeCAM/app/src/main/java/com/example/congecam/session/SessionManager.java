package com.example.congecam.session;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.congecam.entity.User;

public class SessionManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String SHARED_PREF_NAME = "AndroidHivePref";
    private Context context;

    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void saveSession(User user){
        int id = user.getId();
        int id_service = user.getId_service();
        String name = user.getName();
        String email = user.getEmail();
        editor.putInt("id", id).commit();
        editor.putInt("id_service", id_service).commit();
        editor.putString("name", name).commit();
        editor.putString("email", email).commit();

    }
    public int getSession(){
        return sharedPreferences.getInt("id", 0);
    }
    public int service(){
        return sharedPreferences.getInt("id_service", 0);
    }
    public String getEmail(){
        return sharedPreferences.getString("email", "");
    }
    public void removeSession(){
        editor.putInt("id", 0).commit();
        editor.putInt("id_service", 0).commit();
        editor.putString("name", "").commit();
        editor.putString("email", "").commit();
    }
}
