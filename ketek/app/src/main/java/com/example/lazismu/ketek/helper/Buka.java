package com.example.lazismu.ketek.helper;

import android.content.Context;
import android.content.Intent;

/**
 * Created by user on 23/07/2018.
 */

public class Buka {
    private static Intent intent;

    public static void buka(Context context, Class<?> link){
        intent =  new Intent(context.getApplicationContext(), link);
        context.startActivity(intent);
    }
}
