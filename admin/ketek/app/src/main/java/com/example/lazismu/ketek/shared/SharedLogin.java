package com.example.lazismu.ketek.shared;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedLogin {
    //key
    public static final String SP_LEY = "Login";

    //key value
    public static final String SP_ID = "id";
    public static final String SP_NAMA = "nama";
    public static final String SP_EMAIL = "email";
    public static final String SP_PASSWORD = "password";
    public static final String SP_SUDAH_LOGIN = "sudah_login";
    public static final String SP_SUDAH_LOGIN2 = "sudah_login2";
    public static final String SP_ALAMAT = "alamat";

    Context context;
    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    //buat sharefpref dari class lain
    public SharedLogin(Context context) {
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

    public Boolean getSharedSudahLogin() {
        return sp.getBoolean(SP_SUDAH_LOGIN, false);
    }

    public Boolean getSharedSudahLogin2() {
        return sp.getBoolean(SP_SUDAH_LOGIN2, false);
    }

    public String getSharedString(String key) {
        return sp.getString(key, "");
    }

}