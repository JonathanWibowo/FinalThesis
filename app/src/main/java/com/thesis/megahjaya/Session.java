// reference from: https://www.androidhive.info/2012/08/android-session-management-using-shared-preferences/

package com.thesis.megahjaya;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    Context context;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private static final String session = "session";

    public Session(SharedPreferences sharedPreferences, SharedPreferences.Editor editor) {

        sharedPreferences = context.getSharedPreferences(session, context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void makeSessionLogin(String username, String token){
        editor.putBoolean("checkLoginStatus", true);
        editor.putString("username", username);
        editor.putString("token", token);
        editor.commit();
    }

    public void checkValidation(){
        // Go to login page when not
        if(isLoggedIn()){

        }
        else{

        }

    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean("checkLoginStatus", false);
    }
}
