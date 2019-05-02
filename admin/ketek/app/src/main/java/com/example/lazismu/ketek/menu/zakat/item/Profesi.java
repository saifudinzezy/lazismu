package com.example.lazismu.ketek.menu.zakat.item;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.example.lazismu.ketek.model.ZisItem;
import com.example.lazismu.ketek.shared.SharedLogin;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.lazismu.ketek.helper.Constan.KEY_DATA;
import static com.example.lazismu.ketek.helper.Constan.URL_IMAGE;
import static com.example.lazismu.ketek.helper.ConvertDate.tglHariIni;
import static com.example.lazismu.ketek.helper.FunctionError.cekEditText;
import static com.example.lazismu.ketek.helper.FunctionError.getTextEditText;
import static com.example.lazismu.ketek.helper.FunctionError.setErrorEditText;
import static com.example.lazismu.ketek.helper.PlayMP3.play;
import static com.example.lazismu.ketek.helper.PlayMP3.stopPLay;
import static com.example.lazismu.ketek.helper.RealPathUtil.setTextViews;

public class Profesi extends AppCompatActivity {

    TextView kalkuza;
    @BindView(R.id.txt_nama)
    TextView txtNama;
    @BindView(R.id.edhasil)
    EditText edhasil;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.txt_insert)
    TextView txtInsert;
    @BindView(R.id.txt_place)
    TextView txtPlace;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.btn_upload)
    Button btnUpload;
    //var img
    int PICK_IMAGE_REQUEST = 1;
    int bitmap_size = 60; // range 1 - 100
    Bitmap bitmap, decoded;
    String part_image;
    SharedLogin sharedLogin;
    ProgressDialog progressDialog;
    ZisItem data;
    UpdateZIS updateZIS;
    @BindView(R.id.txt_view)
    TextView txtView;
    double nilai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kirim);
        ButterKnife.bind(this);
        kalkuza = findViewById(R.id.txtkal1);

        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            nilai = intent.getDouble("jumlah");
            Log.v("Nilai", "" + nilai);
            Locale localeID = new Locale("in", "ID");
            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
            edhasil.setText(formatRupiah.format(nilai));
        }

        sharedLogin = new SharedLogin(this);
        btnUpload.setEnabled(false);
        txtNama.setText(sharedLogin.getSharedString(SharedLogin.SP_NAMA));
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Tunggu Sebentar...");
        updateZIS = new UpdateZIS(Profesi.this);
        data = getIntent().getParcelableExtra(KEY_DATA);

        if (data != null) {
            Glide.with(this)
                    .load(URL_IMAGE + data.getGambar())
                    .into(img);
            nilai = Double.valueOf(data.getNominal());
            Locale localeID = new Locale("in", "ID");
            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
            edhasil.setText(formatRupiah.format(nilai));
            txtPlace.setText(data.getGambar());
        }

        kalkuza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profesi.this, com.example.lazismu.ketek.menu.zakat.kalkuza.kalkuza.class));
                finish();
            }
        });
    }

    @OnClick({R.id.txt_insert, R.id.checkbox, R.id.btn_upload, R.id.txt_view})
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
            case R.id.checkbox:
                if (checkbox.isChecked()) { //cek it
                    stopPLay();
                    play(Profesi.this);
                    btnUpload.setEnabled(true);
                } else { // no cek it
                    btnUpload.setEnabled(false);
                    stopPLay();
                }
                break;
            case R.id.btn_upload:
                if (cekEditText(edhasil)) {
                    setErrorEditText(edhasil, "Nominal Kosong");
                } else {
                    if (data == null) {
                        int value = (int) nilai;
                        updateZIS.simpan(part_image, sharedLogin.getSharedString(SharedLogin.SP_ID), String.valueOf(value),
                                tglHariIni(), progressDialog, edhasil, "Zakat Profesi");
                    } else {
                        //jika place sama dengan nama imagenya
                        if (data.getGambar() == txtPlace.getText().toString()) {
                            updateZIS.updateWithField(data.getIdZis(), String.valueOf(nilai),
                                    data.getTanggal(), data.getStatus());
                        } else {
                            updateZIS.updateWithImage(part_image, data.getIdZis(), getTextEditText(edhasil),
                                    data.getTanggal(), data.getStatus(), data.getGambar());
                        }
                    }
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


            setTextViews(Profesi.this, Build.VERSION.SDK_INT, data.getData().getPath(), realPath, txtPlace, img);
            part_image = realPath;
        }
    }
}
