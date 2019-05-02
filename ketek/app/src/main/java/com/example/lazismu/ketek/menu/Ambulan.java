package com.example.lazismu.ketek.menu;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lazismu.ketek.R;
import com.example.lazismu.ketek.adapter.AmbulanAdapter;
import com.example.lazismu.ketek.adapter.spiner.CustomAdapter;
import com.example.lazismu.ketek.helper.FunctionError;
import com.example.lazismu.ketek.model.AmbulanItem;
import com.example.lazismu.ketek.model.DataAmbulan2Item;
import com.example.lazismu.ketek.model.ResponseAmbulan;
import com.example.lazismu.ketek.model.ResponseAmbulan2;
import com.example.lazismu.ketek.model.ResponseInsert;
import com.example.lazismu.ketek.network.ApiService;
import com.example.lazismu.ketek.network.RetroClient;
import com.example.lazismu.ketek.shared.SharedLogin;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.lazismu.ketek.helper.CariTigaTanggal.cariTigaTanggal;
import static com.example.lazismu.ketek.helper.Constan.KEY_DATA;
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
    @BindView(R.id.sp_mobil)
    Spinner spMobil;
    @BindView(R.id.txt_kosong)
    TextView txtKosong;
    @BindView(R.id.sp_sim)
    Spinner spSim;
    @BindView(R.id.txt_sim)
    TextView txtSim;
    @BindView(R.id.txt_mobil)
    TextView txtMobil;
    private Calendar myCalendar;
    AmbulanItem data;
    CustomAdapter customAdapter;
    List<DataAmbulan2Item> hasilPesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coba);
        ButterKnife.bind(this);

        sharedLogin = new SharedLogin(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Tunggu Sebentar...");
        myCalendar = Calendar.getInstance();

        //get data ambulan
        getDataAmbula();

        edtId.setText(sharedLogin.getSharedString(SharedLogin.SP_NAMA));

        //spiner sim
        final ArrayAdapter<CharSequence> adapterStatus = ArrayAdapter.createFromResource(this, R.array.sim,
                R.layout.support_simple_spinner_dropdown_item);
        spSim.setAdapter(adapterStatus);

        //select sp
        spSim.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txtSim.setText(adapterStatus.getItem(position).toString());
                if (adapterStatus.getItem(position).toString().equalsIgnoreCase("Ya")){
                    btnkonfirm.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spMobil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(), hasilPesan.get(position).getJudul(), Toast.LENGTH_SHORT).show();
                try {
                    txtMobil.setText(hasilPesan.get(position).getIdAmbulan());
                } catch (Exception e) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        data = getIntent().getParcelableExtra(KEY_DATA);
        if (data != null) {
            edtSopir.setText(data.getNamaDriver());
            txtPinjam.setText(ubahTanggal2(data.getTglPinjam()));
            txtKembali.setText(ubahTanggal2(data.getTglKembali()));
            edtTujuan.setText(data.getTujuan());

            //set nilai awal spiner sim
            //ambil posisi sesuai isi string
            int posisi = adapterStatus.getPosition(data.getSim());
            //set select awal spiner
            spSim.setSelection(posisi);
            txtSim.setText(data.getSim());

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
                        if (cariTigaTanggal(getTextView(txtPinjam), getTextView(txtKembali))){
                            simpan(getTextEditText(edtId), getTextEditText(edtSopir), ubahTanggal3(getTextView(txtPinjam)),
                                    ubahTanggal3(getTextView(txtKembali)), getTextEditText(edtTujuan), getTextView(txtMobil),
                                    getTextView(txtSim));
                        }else {
                            Toast.makeText(Ambulan.this, "Tanggal pinjam tidak boleh lebih dari tiga hari", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        updateWithField(data.getIdAmbulan(), getTextEditText(edtSopir), ubahTanggal3(getTextView(txtPinjam)),
                                ubahTanggal3(getTextView(txtKembali)), getTextEditText(edtTujuan), data.getPesan(), data.getStatus(),
                                getTextView(txtMobil), getTextView(txtSim));
                    }
                }
                break;
        }
    }

    private void simpan(String id, String nama, String tglPinjam, String tglKembali, String tujuan, String idAmbulan, String sim) {
        //eksekusi
        progressDialog.show();
        ApiService service = RetroClient.getApiService();
        Call<ResponseInsert> call = service.insertAmbulan(id, nama, tglPinjam, tglKembali, tujuan, sim, idAmbulan);
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

    public void updateWithField(String id, String nama, String tglPinjam, String tglKembali,
                                String tujuan, String pesan, String status, String idAmbulan, String sim) {
        ApiService service = RetroClient.getApiService();
        Call<ResponseInsert> call = service.updateAmbulan(Integer.parseInt(id), nama, tglPinjam, tglKembali, tujuan, pesan,
                status, idAmbulan, sim);

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
    }

    private void getDataAmbula() {
        ApiService apiService = RetroClient.getApiService();
        Call<ResponseAmbulan2> call = apiService.getDataAmbulan();
        final ProgressDialog pd = new ProgressDialog(Ambulan.this);
        pd.setMessage("Tunggu Sebentar...");
        pd.show();

        call.enqueue(new Callback<ResponseAmbulan2>() {
            @Override
            public void onResponse(Call<ResponseAmbulan2> call, Response<ResponseAmbulan2> response) {
                hasilPesan = response.body().getDataAmbulan2();
                Log.e("Tag", "Hasil List :" + new Gson().toJson(hasilPesan));
                if (response.body().getResponse().equalsIgnoreCase("true")) {
                    try {
                        pd.dismiss();
                        spMobil.setVisibility(View.VISIBLE);
                        txtKosong.setVisibility(View.GONE);

                        //load jika ada data baru
                        customAdapter = new CustomAdapter(Ambulan.this, hasilPesan);
                        spMobil.setAdapter(customAdapter);
                        customAdapter.notifyDataSetChanged();

                        if (data != null) {
                            //select default spinner skp tahunan
                            for (int i = 0; i < hasilPesan.size(); i++) {
                                if (hasilPesan.get(i).getIdAmbulan() == data.getId_ambulan2()) {
                                    //get position
                                    spMobil.setSelection(i);
                                }
                            }
                            txtMobil.setText(data.getId_ambulan2());
                        }
                    } catch (Exception e) {
                        pd.dismiss();
                    }
                } else {
                    Log.e("Tag", "Gagal req data ");
                    pd.dismiss();
                    spMobil.setVisibility(View.GONE);
                    txtKosong.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseAmbulan2> call, Throwable t) {
                pd.dismiss();
            }
        });
    }
}