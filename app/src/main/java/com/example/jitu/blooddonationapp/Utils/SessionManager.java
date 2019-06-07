package com.example.jitu.blooddonationapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by yogiii on 4/13/2018.
 */

public class SessionManager  {

    private static String TAG = SessionManager.class.getSimpleName();
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "BelajarLogin";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    public static final String KEY_USERNAME="user_id";
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public  void createLoginSession(String username)
    {
        editor.putBoolean(KEY_IS_LOGGEDIN,true);
        editor.putString(KEY_USERNAME,username);
        editor.commit();
    }


    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.commit();
        Log.d(TAG, "User login session modified!");
    }

    HashMap<String, String> getUserDetails()
    {
        HashMap<String, String>user=new HashMap<>();
        user.put(KEY_USERNAME,pref.getString(KEY_USERNAME,null));
        return  user;
    }


    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

}
