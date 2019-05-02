package com.example.lazismu.ketek.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.lazismu.ketek.menu.add.Berita;
import com.example.lazismu.ketek.model.ResponseInsert;
import com.example.lazismu.ketek.network.ApiService;
import com.example.lazismu.ketek.network.RetroClient;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CRUDBerita {
    private Context context;
    private OnReset listener;

    public CRUDBerita(Context context, OnReset listener) {
        this.context = context;
        this.listener = listener;
    }

    public void updateWithImage(String part_image,String id, String judul, String artikel, String tgl, String hapus) {
        File imageFile = new File(part_image);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"), imageFile);
        //parm 1 samakan dengan parameter di backend
        MultipartBody.Part partImage = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody);
        //eksekusi
        ApiService service = RetroClient.getApiService();
        Call<ResponseInsert> call = service.updateBerita1(Integer.parseInt(id), judul, partImage, artikel, hapus, tgl);
        call.enqueue(new Callback<ResponseInsert>() {
            @Override
            public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                if (response.body().getCode() == 200) {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, Berita.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                } else {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseInsert> call, Throwable t) {
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateWithField(String id, String judul, String artikel, String tgl) {
        ApiService service = RetroClient.getApiService();
        Call<ResponseInsert> call = service.updateBerita2(Integer.parseInt(id), judul, artikel, tgl);

        call.enqueue(new Callback<ResponseInsert>() {
            @Override
            public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                if (response.body().getCode() == 200) {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, Berita.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                    //hancurkan activity
                } else {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseInsert> call, Throwable t) {
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //insert
    public void simpan(String part_image, String judul, String artikel, String tgl,
                        final ProgressDialog progressDialog) {
        File imageFile = new File(part_image);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"), imageFile);
        //parm 1 samakan dengan parameter di backend
        MultipartBody.Part partImage = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody);
        //eksekusi
        ApiService service = RetroClient.getApiService();
        progressDialog.show();
        Call<ResponseInsert> call = service.insertBerta(judul, partImage, artikel, tgl);
        call.enqueue(new Callback<ResponseInsert>() {
            @Override
            public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                if (response.body().getCode() == 200) {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    listener.onReset();
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseInsert> call, Throwable t) {
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    public interface OnReset{
        public void onReset();
    }
}
