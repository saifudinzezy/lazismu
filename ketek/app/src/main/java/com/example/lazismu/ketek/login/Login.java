package com.example.lazismu.ketek.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lazismu.ketek.MainMenu;
import com.example.lazismu.ketek.R;
import com.example.lazismu.ketek.model.ResponseUser;
import com.example.lazismu.ketek.model.UserItem;
import com.example.lazismu.ketek.network.ApiService;
import com.example.lazismu.ketek.network.RetroClient;
import com.example.lazismu.ketek.shared.SharedLogin;
import com.example.lazismu.ketek.sp.Splashscreen;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.lazismu.ketek.helper.FunctionError.cekEditText;
import static com.example.lazismu.ketek.helper.FunctionError.setErrorEditText;
import static com.example.lazismu.ketek.shared.SharedLogin.SP_ALAMAT;
import static com.example.lazismu.ketek.shared.SharedLogin.SP_EMAIL;
import static com.example.lazismu.ketek.shared.SharedLogin.SP_ID;
import static com.example.lazismu.ketek.shared.SharedLogin.SP_NAMA;
import static com.example.lazismu.ketek.shared.SharedLogin.SP_PASSWORD;
import static com.example.lazismu.ketek.shared.SharedLogin.SP_SUDAH_LOGIN;
import static com.example.lazismu.ketek.shared.SharedLogin.SP_SUDAH_LOGIN2;

public class Login extends AppCompatActivity {

    @BindView(R.id.edt_no)
    EditText edtNo;
    @BindView(R.id.edt_pass)
    EditText edtPass;
    @BindView(R.id.buttonLogin)
    Button buttonLogin;
    @BindView(R.id.textViews)
    TextView textViews;
    ProgressDialog progressDialog;
    SharedLogin sharedLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        sharedLogin = new SharedLogin(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");

        //cek apakah user sudah login
        if (sharedLogin.getSharedSudahLogin()) {
            if (sharedLogin.getSharedSudahLogin2()) {
                //cek login kedua
                startActivity(new Intent(Login.this, MainMenu.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
            //cache
            edtNo.setText(sharedLogin.getSharedString(SP_ID));
            edtPass.setText(sharedLogin.getSharedString(SP_PASSWORD));
        }
    }

    @OnClick({R.id.buttonLogin, R.id.textViews})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.buttonLogin:
                if (cekEditText(edtNo)) {
                    edtNo.setError("ID Kosong");
                } else if (TextUtils.isEmpty(edtPass.getText().toString())) {
                    setErrorEditText(edtPass, "Masukan ID");
                } else {
                    login(edtNo.getText().toString(), edtPass.getText().toString());
                }
                break;
            case R.id.textViews:
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                //startActivityForResult(intent, REQUEST_SIGNUP);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
        }
    }

    private void login(String id, final String password) {
        //eksekusi
        progressDialog.show();
        ApiService service = RetroClient.getApiService();
        Call<ResponseUser> login = service.login(id, password);
        login.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                //cek code apakah sukses atau tidak
                progressDialog.dismiss();
                if (response.body().getCode() == 200) {
                    Toast.makeText(Login.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    List<UserItem> dataUser = response.body().getUser();
                    //simpan di sharef pref
                    //simpan id_user, nama_user, email dan password
                    sharedLogin.saveSharedString(SP_ID, dataUser.get(0).getIdUser());
                    sharedLogin.saveSharedString(SP_NAMA, dataUser.get(0).getNama());
                    sharedLogin.saveSharedString(SP_EMAIL, dataUser.get(0).getEmail());
                    sharedLogin.saveSharedString(SP_ALAMAT, dataUser.get(0).getAlamat());
                    sharedLogin.saveSharedString(SP_PASSWORD, edtPass.getText().toString());

                    //buat cache
                    sharedLogin.saveSharedBoolean(SP_SUDAH_LOGIN, true);
                    //cek login
                    sharedLogin.saveSharedBoolean(SP_SUDAH_LOGIN2, true);
                    //buka home jika berhasil login
                    startActivity(new Intent(Login.this, Splashscreen.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                    //hancurkan activity
                    finish();
                } else {
                    Toast.makeText(Login.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                //hilangkan loading
                progressDialog.dismiss();
                Toast.makeText(Login.this, "Koneksi Gagal Keserver", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }
}