package com.example.lazismu.ketek.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lazismu.ketek.R;
import com.example.lazismu.ketek.helper.FunctionError;
import com.example.lazismu.ketek.model.ResponseInsert;
import com.example.lazismu.ketek.model.ResponseNoUrut;
import com.example.lazismu.ketek.network.ApiService;
import com.example.lazismu.ketek.network.RetroClient;
import com.example.lazismu.ketek.shared.SharedLogin;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.lazismu.ketek.helper.FunctionError.cekEditText;
import static com.example.lazismu.ketek.helper.FunctionError.getTextEditText;
import static com.example.lazismu.ketek.helper.FunctionError.getTextView;
import static com.example.lazismu.ketek.helper.FunctionError.setErrorEditText;
import static com.example.lazismu.ketek.shared.SharedLogin.SP_ALAMAT;
import static com.example.lazismu.ketek.shared.SharedLogin.SP_EMAIL;
import static com.example.lazismu.ketek.shared.SharedLogin.SP_ID;
import static com.example.lazismu.ketek.shared.SharedLogin.SP_NAMA;
import static com.example.lazismu.ketek.shared.SharedLogin.SP_PASSWORD;

public class SignUp extends AppCompatActivity {

    @BindView(R.id.edt_nama)
    EditText edtNama;
    @BindView(R.id.edt_alamat)
    EditText edtAlamat;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_pass)
    EditText edtPass;
    @BindView(R.id.btn_daftar)
    Button btnDaftar;
    @BindView(R.id.txt_login)
    TextView txtLogin;
    @BindView(R.id.txt_no_urut)
    TextView txtNoUrut;
    ProgressDialog progressDialog;
    SharedLogin sharedLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        ButterKnife.bind(this);

        sharedLogin = new SharedLogin(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Tunggu Sebentar...");

        getUser();
    }

    @OnClick({R.id.btn_daftar, R.id.txt_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_daftar:
                if (cekEditText(edtNama)) {
                    setErrorEditText(edtNama, "Nama Kosong");
                } else if (cekEditText(edtAlamat)) {
                    setErrorEditText(edtAlamat, "Alamat Kosong");
                } else if (cekEditText(edtEmail)) {
                    setErrorEditText(edtEmail, "Email Kosong");
                } else if (cekEditText(edtPass)) {
                    setErrorEditText(edtPass, "Password Kosong");
                } else {
                    simpan(getTextView(txtNoUrut), getTextEditText(edtNama), getTextEditText(edtEmail),
                            getTextEditText(edtAlamat), getTextEditText(edtPass));
                }
                break;
            case R.id.txt_login:
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), Login.class);
                //startActivityForResult(intent, REQUEST_SIGNUP);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
        }
    }

    private void getUser() {
        progressDialog.show();
        ApiService apiService = RetroClient.getApiService();
        Call<ResponseNoUrut> call = apiService.getNoUrut();
        call.enqueue(new Callback<ResponseNoUrut>() {
            @Override
            public void onResponse(Call<ResponseNoUrut> call, Response<ResponseNoUrut> response) {
                //Log.e("Tag", "Hasil List :" + new Gson().toJson(hasilPesan));
                if (response.body().getSukses().equalsIgnoreCase("true")) {
                    try {
                        txtNoUrut.setText(response.body().getNoUrut());
                        progressDialog.dismiss();
                    } catch (Exception e) {
                        progressDialog.dismiss();
                    }
                } else {
                    Log.e("Tag", "Gagal req data ");
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseNoUrut> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void simpan(final String id, final String nama, final String email, final String alamat, final String password) {
        //eksekusi
        progressDialog.show();
        ApiService service = RetroClient.getApiService();
        Call<ResponseInsert> call = service.insertUser(id, nama, alamat, password, email);
        call.enqueue(new Callback<ResponseInsert>() {
            @Override
            public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                progressDialog.dismiss();
                if (response.body().getCode() == 200) {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    //simpan di sharef pref
                    //simpan id_user, nama_user, email dan password
                    sharedLogin.saveSharedString(SP_ID, id);
                    sharedLogin.saveSharedString(SP_NAMA, nama);
                    sharedLogin.saveSharedString(SP_PASSWORD, password);
                    sharedLogin.saveSharedString(SP_ALAMAT, alamat);
                    sharedLogin.saveSharedString(SP_EMAIL, email);

                    getUser();
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
        FunctionError.kosongkan(edtPass);
        FunctionError.kosongkan(edtNama);
        FunctionError.kosongkan(edtAlamat);
        FunctionError.kosongkan(edtEmail);
    }
}
