package com.example.lazismu.ketek.menu.item;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lazismu.ketek.R;
import com.example.lazismu.ketek.helper.RealPathUtil;
import com.example.lazismu.ketek.helper.UpdateZIS;
import com.example.lazismu.ketek.model.ResponseInsert;
import com.example.lazismu.ketek.model.ZisItem;
import com.example.lazismu.ketek.network.ApiService;
import com.example.lazismu.ketek.network.RetroClient;
import com.example.lazismu.ketek.shared.SharedLogin;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.lazismu.ketek.helper.Constan.KEY_DATA;
import static com.example.lazismu.ketek.helper.Constan.URL_IMAGE;
import static com.example.lazismu.ketek.helper.ConvertDate.tglHariIni;
import static com.example.lazismu.ketek.helper.FunctionError.cekEditText;
import static com.example.lazismu.ketek.helper.FunctionError.getTextEditText;
import static com.example.lazismu.ketek.helper.FunctionError.setErrorEditText;
import static com.example.lazismu.ketek.helper.Notif.notif;
import static com.example.lazismu.ketek.helper.PlayMP3.play;
import static com.example.lazismu.ketek.helper.PlayMP3.stopPLay;
import static com.example.lazismu.ketek.helper.RealPathUtil.setTextViews;

public class Bedahrumah extends AppCompatActivity {
    @BindView(R.id.editText)
    TextView editText;
    @BindView(R.id.edt_nominal)
    EditText edtNominal;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.upload)
    Button upload;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.txt_insert)
    TextView txtInsert;
    @BindView(R.id.txt_place)
    TextView txtPlace;
    @BindView(R.id.txt_view)
    TextView txtView;
    //var img
    int PICK_IMAGE_REQUEST = 1;
    int bitmap_size = 60; // range 1 - 100
    Bitmap bitmap, decoded;
    String part_image;
    SharedLogin sharedLogin;
    ProgressDialog progressDialog;
    ZisItem data;
    UpdateZIS updateZIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bedahrumah);
        ButterKnife.bind(this);
        sharedLogin = new SharedLogin(this);
        upload.setEnabled(false);
        editText.setText(sharedLogin.getSharedString(SharedLogin.SP_NAMA));
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Tunggu Sebentar...");
        updateZIS = new UpdateZIS(Bedahrumah.this);

        data = getIntent().getParcelableExtra(KEY_DATA);
        if (data != null) {
            Glide.with(this)
                    .load(URL_IMAGE + data.getGambar())
                    .into(img);
            edtNominal.setText(data.getNominal());
            txtPlace.setText(data.getGambar());
        }
    }

    @OnClick({R.id.txt_insert, R.id.txt_view, R.id.upload, R.id.checkbox})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_insert:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                break;
            case R.id.txt_view:
                if (img.getVisibility() == View.GONE) {
                    img.setVisibility(View.VISIBLE);
                    Drawable img = getResources().getDrawable(R.drawable.ic_visibility_grey_900_24dp);
                    //sesuaikan image sesuai isian bisa di start/awal, top/atas,end/akhir, bottom/bawah
                    txtView.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, img, null);
                } else {
                    img.setVisibility(View.GONE);
                    Drawable img = getResources().getDrawable(R.drawable.ic_visibility_off_black_24dp
                    );
                    //sesuaikan image sesuai isian bisa di start/awal, top/atas,end/akhir, bottom/bawah
                    txtView.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, img, null);
                }
                break;
            case R.id.upload:
                if (cekEditText(edtNominal)) {
                    setErrorEditText(edtNominal, "Nominal Kosong");
                } else {
                    if (data == null) {
                        simpan(getTextEditText(edtNominal), tglHariIni());
                    } else {
                        //jika place sama dengan nama imagenya
                        if (data.getGambar() == txtPlace.getText().toString()) {
                            updateZIS.updateWithField(data.getIdZis(), getTextEditText(edtNominal),
                                    data.getTanggal(), data.getStatus());
                        } else {
                            updateZIS.updateWithImage(part_image, data.getIdZis(), getTextEditText(edtNominal),
                                    data.getTanggal(), data.getStatus(), data.getGambar());
                        }
                    }
                }
                break;
            case R.id.checkbox:
                if (checkbox.isChecked()){ //cek it
                    stopPLay();
                    play(Bedahrumah.this);
                    upload.setEnabled(true);
                }else { // no cek it
                    upload.setEnabled(false);
                    stopPLay();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            String realPath;
            // SDK < API11
            if (Build.VERSION.SDK_INT < 11)
                realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, data.getData());

                // SDK >= 11 && SDK < 19
            else if (Build.VERSION.SDK_INT < 19)
                realPath = RealPathUtil.getRealPathFromURI_API11to18(this, data.getData());

                // SDK > 19 (Android 4.4)
            else
                realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());


            setTextViews(Bedahrumah.this, Build.VERSION.SDK_INT, data.getData().getPath(), realPath, txtPlace, img);
            part_image = realPath;
        }
    }

    //insert
    private void simpan(String nominal, String tgl) {
        File imageFile = new File(part_image);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"), imageFile);
        //parm 1 samakan dengan parameter di backend
        MultipartBody.Part partImage = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody);
        //eksekusi
        ApiService service = RetroClient.getApiService();
        progressDialog.show();
        Call<ResponseInsert> call = service.insertZis(sharedLogin.getSharedString(SharedLogin.SP_ID), partImage,
                nominal, tgl, "Bedah Rumah");
        call.enqueue(new Callback<ResponseInsert>() {
            @Override
            public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                if (response.body().getCode() == 200) {
                    //Toast.makeText(Bedahrumah.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    edtNominal.setText("");
                    progressDialog.dismiss();
                    notif(Bedahrumah.this);
                } else {
                    Toast.makeText(Bedahrumah.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseInsert> call, Throwable t) {
                Toast.makeText(Bedahrumah.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}