package com.stoffe.gym.Helpers;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class Utils {

    public static void showSnackbar(String message, View view){
        Snackbar.make(view,message,Snackbar.LENGTH_LONG).show();
    }

    public static float calculateBMI(float weight, float height){
        return weight / (float )(Math.pow((height/100),2));
    }
}
