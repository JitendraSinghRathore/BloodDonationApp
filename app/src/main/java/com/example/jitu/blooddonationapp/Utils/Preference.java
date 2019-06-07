package com.example.jitu.blooddonationapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by amit on 12/19/2017.
 */

public class Preference {
    private static final Preference ourInstance = new Preference();
    private static SharedPreferences sharedPreferences;

    public static Preference getInstance(Context context)
    {
       // sharedPreferences = context.getSharedPreferences("mobile", Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences("user_id", Context.MODE_PRIVATE);
        return ourInstance;
    }

    private Preference() {
    }

    /*public void setID(String ID) {
        sharedPreferences.edit().putString("user_id", ID).apply();

    }*/


    public void setuser_id(String id) {
        sharedPreferences.edit().putString("user_id", id).apply();

    }

    public String getuser_id() {
        //sharedPreferences.edit().putString("user_id", id).apply();

        Log.d("shared pref",sharedPreferences.getAll().toString());

        return sharedPreferences.getString("user_id", "-1");
    }



    public String getreqid() {
        //sharedPreferences.edit().putString("user_id", id).apply();

        Log.d("shared pref",sharedPreferences.getAll().toString());

        return sharedPreferences.getString("request_id", "-1");
    }

    public void setLogged(boolean logged) {
        sharedPreferences.edit().putBoolean("loggedin", logged).apply();

    }



    public void setClearData() {
        sharedPreferences.edit().remove("user_id").apply();
        //sharedPreferences.edit().remove("mobile").apply();
        sharedPreferences.edit().remove("loggedin").apply();
    }

    public String getID() {
        return sharedPreferences.getString("user_id", null);
    }

    public Boolean getLogged() {
        return sharedPreferences.getBoolean("loggedin", false);
    }
 }

