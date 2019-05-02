package com.example.lazismu.ketek.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lazismu.ketek.MainMenu;
import com.example.lazismu.ketek.login.Login;
import com.example.lazismu.ketek.menu.Laporan;
import com.example.lazismu.ketek.menu.item.Beasiswa2;
import com.example.lazismu.ketek.model.ResponseInsert;
import com.example.lazismu.ketek.network.ApiService;
import com.example.lazismu.ketek.network.RetroClient;
import com.example.lazismu.ketek.shared.SharedLogin;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.lazismu.ketek.helper.Notif.notif;

public class UpdateZIS {
    private Context context;

    public UpdateZIS(Context context) {
        this.context = context;
    }

    public void updateWithImage(String part_image, String id, String nominal, String tanggal, String status, String hapus) {
        File imageFile = new File(part_image);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"), imageFile);
        //parm 1 samakan dengan parameter di backend
        MultipartBody.Part partImage = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody);
        //eksekusi
        ApiService service = RetroClient.getApiService();
        Call<ResponseInsert> call = service.updateZIS1(Integer.parseInt(id), partImage, nominal, tanggal, hapus, status);
        call.enqueue(new Callback<ResponseInsert>() {
            @Override
            public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                if (response.body().getCode() == 200) {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, Laporan.class)
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

    public void updateWithField(String id, String nominal, String tanggal, String status) {
        ApiService service = RetroClient.getApiService();
        Call<ResponseInsert> call = service.updateZIS2(Integer.parseInt(id), nominal, tanggal, status);

        call.enqueue(new Callback<ResponseInsert>() {
            @Override
            public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                if (response.body().getCode() == 200) {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, Laporan.class)
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
    public void simpan(String part_image, String id, String nominal, String tgl,
                        final ProgressDialog progressDialog, final EditText edtNominal, String kategori) {
        File imageFile = new File(part_image);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"), imageFile);
        //parm 1 samakan dengan parameter di backend
        MultipartBody.Part partImage = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody);
        //eksekusi
        ApiService service = RetroClient.getApiService();
        progressDialog.show();
        Call<ResponseInsert> call = service.insertZis(id, partImage,
                nominal, tgl, kategori);
        call.enqueue(new Callback<ResponseInsert>() {
            @Override
            public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                if (response.body().getCode() == 200) {
                    //Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    edtNominal.setText("");
                    progressDialog.dismiss();
                    notif(context);
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
}
