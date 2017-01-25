package com.nkdroid.apidemo.custom;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.nkdroid.apidemo.model.User;


public class PrefUtils {

    public static void setUser(User currentUser, Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_pref", 0);
        complexPreferences.putObject("user_pref_value", currentUser);
        complexPreferences.commit();
    }

    public static void clearCurrentUser( Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_pref", 0);
        complexPreferences.clearObject();
        complexPreferences.commit();
    }


    public static User getUser(Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_pref", 0);
        User currentUser = complexPreferences.getObject("user_pref_value", User.class);
        return currentUser;
    }



}
