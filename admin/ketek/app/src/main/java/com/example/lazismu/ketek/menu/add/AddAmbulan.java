package com.example.lazismu.ketek.menu.add;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lazismu.ketek.R;
import com.example.lazismu.ketek.model.DataAmbulan2Item;
import com.example.lazismu.ketek.model.ResponseInsert;
import com.example.lazismu.ketek.network.ApiService;
import com.example.lazismu.ketek.network.RetroClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.lazismu.ketek.helper.Constan.KEY_DATA;
import static com.example.lazismu.ketek.helper.ConvertDate.tglHariIni;
import static com.example.lazismu.ketek.helper.FunctionError.cekEditText;
import static com.example.lazismu.ketek.helper.FunctionError.getTextEditText;
import static com.example.lazismu.ketek.helper.FunctionError.getTextView;
import static com.example.lazismu.ketek.helper.FunctionError.setErrorEditText;

public class AddAmbulan extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.wrap)
    AppBarLayout wrap;
    @BindView(R.id.edt_nama)
    EditText edtNama;
    @BindView(R.id.edt_no_pol)
    EditText edtNoPol;
    @BindView(R.id.sp_kondisi)
    Spinner spKondisi;
    @BindView(R.id.sp_status)
    Spinner spStatus;

    DataAmbulan2Item data;
    @BindView(R.id.txt_kondisi)
    TextView txtKondisi;
    @BindView(R.id.txt_status)
    TextView txtStatus;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ambulan);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Tunggu Sebentar");

        //spiner kondisi
        final ArrayAdapter<CharSequence> adapterKondisi = ArrayAdapter.createFromResource(this, R.array.kondisi,
                R.layout.support_simple_spinner_dropdown_item);
        spKondisi.setAdapter(adapterKondisi);

        //spiner kondisi
        final ArrayAdapter<CharSequence> adapterStatus = ArrayAdapter.createFromResource(this, R.array.status_pinjam,
                R.layout.support_simple_spinner_dropdown_item);
        spStatus.setAdapter(adapterStatus);

        //select sp
        spKondisi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txtKondisi.setText(adapterKondisi.getItem(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //select sp
        spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txtStatus.setText(adapterStatus.getItem(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        data = getIntent().getParcelableExtra(KEY_DATA);
        if (data != null) {
            edtNama.setText(data.getNama());
            edtNoPol.setText(data.getNoPol());


            //ambil posisi sesuai isi string sp_kondisi
            int posisi = adapterKondisi.getPosition(data.getKondisi());
            //set select awal spiner
            spKondisi.setSelection(posisi);
            txtKondisi.setText(data.getKondisi());

            //ambil posisi sesuai isi string sp_status
            int posisi2 = adapterStatus.getPosition(data.getStatus());
            //set select awal spiner
            spStatus.setSelection(posisi2);
            txtStatus.setText(data.getStatus());

            getSupportActionBar().setTitle("Ubah Data");
        } else {
            getSupportActionBar().setTitle("Tambah Data");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_berita, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add) {
            if (data == null) {
                simpan(getTextEditText(edtNama), getTextEditText(edtNoPol), getTextView(txtKondisi), getTextView(txtStatus));
            } else {
                update(data.getIdAmbulan(), getTextEditText(edtNama), getTextEditText(edtNoPol),
                        getTextView(txtKondisi), getTextView(txtStatus));
                finish();
            }
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void simpan(String nama, String no_pol, String kondisi, String status) {
        progressDialog.show();
        ApiService service = RetroClient.getApiService();
        Call<ResponseInsert> call = service.insertDataAmbulan(nama, no_pol, kondisi, status);
        call.enqueue(new Callback<ResponseInsert>() {
            @Override
            public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                progressDialog.dismiss();
                if (response.body().getCode() == 200) {
                    Toast.makeText(AddAmbulan.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    kosongkan();
                } else {
                    Toast.makeText(AddAmbulan.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseInsert> call, Throwable t) {
                Toast.makeText(AddAmbulan.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void update(String id, String nama, String no_pol, String kondisi, String status) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Tunggu Sebentar");
        progressDialog.show();
        ApiService service = RetroClient.getApiService();
        Call<ResponseInsert> call = service.updateDataAmbulan(id, nama, no_pol, kondisi, status);
        call.enqueue(new Callback<ResponseInsert>() {
            @Override
            public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
//                progressDialog.dismiss();
                if (response.body().getCode() == 200) {
                    Toast.makeText(AddAmbulan.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddAmbulan.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseInsert> call, Throwable t) {
                Toast.makeText(AddAmbulan.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
            }
        });
    }

    private void kosongkan() {
        edtNoPol.setText("");
        edtNama.setText("");
    }
}