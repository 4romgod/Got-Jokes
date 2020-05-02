package com.template.got_jokes.utils;

import android.content.Context;

import com.template.got_jokes.R;

import java.util.Random;

public class Util {

    public static String getRandType(Context context){
        String[] types = {context.getString(R.string.single), context.getString(R.string.twopart)};
        int indexType = (new Random()).nextInt(2);
        String type = types[indexType];

        return type;
    }


}
