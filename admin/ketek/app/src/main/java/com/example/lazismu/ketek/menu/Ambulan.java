package com.example.lazismu.ketek.menu;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lazismu.ketek.R;
import com.example.lazismu.ketek.helper.FunctionError;
import com.example.lazismu.ketek.model.AmbulanItem;
import com.example.lazismu.ketek.model.ResponseInsert;
import com.example.lazismu.ketek.model.ZisItem;
import com.example.lazismu.ketek.network.ApiService;
import com.example.lazismu.ketek.network.RetroClient;
import com.example.lazismu.ketek.shared.SharedLogin;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.lazismu.ketek.helper.Constan.KEY_DATA;
import static com.example.lazismu.ketek.helper.Constan.URL_IMAGE;
import static com.example.lazismu.ketek.helper.ConvertDate.tglHariIni;
import static com.example.lazismu.ketek.helper.ConvertDate.ubahTanggal2;
import static com.example.lazismu.ketek.helper.ConvertDate.ubahTanggal3;
import static com.example.lazismu.ketek.helper.FunctionError.cekEditText;
import static com.example.lazismu.ketek.helper.FunctionError.cekTextView;
import static com.example.lazismu.ketek.helper.FunctionError.getTextEditText;
import static com.example.lazismu.ketek.helper.FunctionError.getTextView;
import static com.example.lazismu.ketek.helper.FunctionError.setErrorEditText;


public class Ambulan extends AppCompatActivity {

    @BindView(R.id.edt_id)
    EditText edtId;
    @BindView(R.id.edt_sopir)
    EditText edtSopir;
    @BindView(R.id.txt_pinjam)
    TextView txtPinjam;
    @BindView(R.id.txt_kembali)
    TextView txtKembali;
    @BindView(R.id.edt_tujuan)
    EditText edtTujuan;
    @BindView(R.id.btnkonfirm)
    Button btnkonfirm;
    SharedLogin sharedLogin;
    ProgressDialog progressDialog;
    private Calendar myCalendar;
    AmbulanItem data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulan);
        ButterKnife.bind(this);

        sharedLogin = new SharedLogin(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Tunggu Sebentar...");
        myCalendar = Calendar.getInstance();

        edtId.setText(sharedLogin.getSharedString(SharedLogin.SP_ID));

        data = getIntent().getParcelableExtra(KEY_DATA);
        if (data != null) {
            edtSopir.setText(data.getNama());
            txtPinjam.setText(ubahTanggal2(data.getTglPinjam()));
            txtKembali.setText(ubahTanggal2(data.getTglKembali()));
            edtTujuan.setText(data.getTujuan());
        } else {
            txtPinjam.setText(ubahTanggal2(tglHariIni()));
            txtKembali.setText(ubahTanggal2(tglHariIni()));
        }
    }

    @OnClick({R.id.txt_pinjam, R.id.txt_kembali, R.id.btnkonfirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_pinjam:
                new DatePickerDialog(Ambulan.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String formatTanggal = "yyyy-MM-dd";
                        SimpleDateFormat sdf = new SimpleDateFormat(formatTanggal);
                        txtPinjam.setText(ubahTanggal2(sdf.format(myCalendar.getTime())));
                    }
                },
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.txt_kembali:
                new DatePickerDialog(Ambulan.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String formatTanggal = "yyyy-MM-dd";
                        SimpleDateFormat sdf = new SimpleDateFormat(formatTanggal);
                        txtKembali.setText(ubahTanggal2(sdf.format(myCalendar.getTime())));
                    }
                },
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.btnkonfirm:
                if (cekEditText(edtId)) {
                    setErrorEditText(edtId, "ID Kosong");
                } else if (cekEditText(edtSopir)) {
                    setErrorEditText(edtSopir, "Nama Sopir Kosong");
                } else if (cekEditText(edtTujuan)) {
                    setErrorEditText(edtTujuan, "Tujuan Kosong");
                } else if (cekTextView(txtPinjam)) {
                    Toast.makeText(this, "Tanggal Pinjam Kosong", Toast.LENGTH_SHORT).show();
                } else if (cekTextView(txtKembali)) {
                    Toast.makeText(this, "Tanggal Pinjam Kosong", Toast.LENGTH_SHORT).show();
                } else {
                    if (data == null) {
                        simpan(getTextEditText(edtId), getTextEditText(edtSopir), ubahTanggal3(getTextView(txtPinjam)),
                                ubahTanggal3(getTextView(txtKembali)), getTextEditText(edtTujuan));
                    } else {
                        /*updateWithField(data.getIdAmbulan(), getTextEditText(edtSopir), ubahTanggal3(getTextView(txtPinjam)),
                                ubahTanggal3(getTextView(txtKembali)), getTextEditText(edtTujuan), data.getPesan(), data.getStatus());*/
                    }
                }
                break;
        }
    }

    private void simpan(String id, String nama, String tglPinjam, String tglKembali, String tujuan) {
        //eksekusi
        progressDialog.show();
        ApiService service = RetroClient.getApiService();
        Call<ResponseInsert> call = service.insertAmbulan(id, nama, tglPinjam, tglKembali, tujuan);
        call.enqueue(new Callback<ResponseInsert>() {
            @Override
            public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                progressDialog.dismiss();
                if (response.body().getCode() == 200) {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    kosongkan();
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseInsert> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void kosongkan() {
        FunctionError.kosongkan(edtTujuan);
        FunctionError.kosongkan(edtSopir);
    }

    /*public void updateWithField(String id, String nama, String tglPinjam, String tglKembali,
                                String tujuan, String pesan, String status) {
        ApiService service = RetroClient.getApiService();
        Call<ResponseInsert> call = service.updateAmbulan(Integer.parseInt(id), nama, tglPinjam, tglKembali, tujuan, pesan, status);

        call.enqueue(new Callback<ResponseInsert>() {
            @Override
            public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                if (response.body().getCode() == 200) {
                    Toast.makeText(Ambulan.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Ambulan.this.startActivity(new Intent(Ambulan.this, Laporan.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                    //hancurkan activity
                } else {
                    Toast.makeText(Ambulan.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseInsert> call, Throwable t) {
                Toast.makeText(Ambulan.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}
