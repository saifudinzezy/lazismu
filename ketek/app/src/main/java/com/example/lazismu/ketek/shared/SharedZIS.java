package com.example.lazismu.ketek.shared;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedZIS {
    //key
    public static final String SP_LEY = "zis";

    //key value
    public static final String SP_ID = "id";
    public static final String SP_NOMINAL = "nominal";
    public static final String SP_GAMBAR = "gambar";
    public static final String SP_TANGGAL = "tanggal";
    public static final String SP_KATEGORI = "kategori";
    public static final String SP_STATUS = "status";

    Context context;
    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    //buat sharefpref dari class lain
    public SharedZIS(Context context) {
        //dengan key sampahku
        sp = context.getSharedPreferences(SP_LEY, Context.MODE_PRIVATE);
        this.context = context;
        spEditor = sp.edit();
    }

    public void saveSharedString(String keySP, String value) {
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSharedBoolean(String keySP, boolean value) {
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSharedString(String key) {
        return sp.getString(key, "");
    }

}