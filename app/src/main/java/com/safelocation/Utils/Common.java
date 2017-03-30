package com.safelocation.Utils;

import com.google.gson.Gson;
import com.safelocation.HttpUtil.HttpUtil;

/**
 * Created by Juliet on 2017/2/23.
 */

public class Common {
    public static final Gson gson = new Gson();

    public static boolean checkNoNUll(String str){
        if(str!=null && !str.equals("")){
            return true;
        }
        return false;
    }

}
