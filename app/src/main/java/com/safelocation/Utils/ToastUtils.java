package com.safelocation.Utils;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;

/**
 * Created by Juliet on 2017/4/18.
 */

public class ToastUtils {
    public static void snackbar_short(View view,String tips){
        Snackbar.make(view,tips,Snackbar.LENGTH_SHORT).show();
    }
    public static void snackbar_long(View view,String tips){
        Snackbar.make(view,tips,Snackbar.LENGTH_LONG).show();
    }
}
