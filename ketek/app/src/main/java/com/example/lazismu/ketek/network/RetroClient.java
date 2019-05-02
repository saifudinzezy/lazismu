package com.example.lazismu.ketek.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.lazismu.ketek.helper.Constan.BASE_URL;

public class RetroClient {
    private static Retrofit retrofit = null;

    private static Retrofit getClient(){
        //cek jika retrofit null
        if (retrofit == null){
            //maka buat object dari retrofit
            retrofit = new Retrofit.Builder()
                    //ubah sesuai urlnya
                    .baseUrl(BASE_URL)
//                    .baseUrl("http://dishubpkl.000webhostapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ApiService getApiService(){
        return getClient().create(ApiService.class);
    }
}