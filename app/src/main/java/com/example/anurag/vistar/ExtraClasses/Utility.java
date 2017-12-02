package com.example.anurag.vistar.ExtraClasses;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by anurag on 12/2/2017.
 */

public class Utility  {
    //Email Validation pattern
    public static final String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";

    //Fragments Tags
    public static final String Login_Fragment = "Login_Fragment";
    public static final String SignUp_Fragment = "SignUp_Fragment";
    public static final String ForgotPassword_Fragment = "ForgotPassword_Fragment";


    public static Boolean isConnected(Context context){
        ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cManager.getActiveNetworkInfo();
        if (nInfo != null && nInfo.isConnected() && nInfo.isAvailable()) {
            return true;
        }
        else{
            return false;
        }

    }
}
