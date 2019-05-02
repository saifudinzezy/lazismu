package com.example.lazismu.ketek.menu.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lazismu.ketek.R;
import com.example.lazismu.ketek.adapter.DataAmbulanAdapter;
import com.example.lazismu.ketek.menu.add.AddAmbulan;
import com.example.lazismu.ketek.model.DataAmbulan2Item;
import com.example.lazismu.ketek.model.ResponseAmbulan2;
import com.example.lazismu.ketek.model.ResponseInsert;
import com.example.lazismu.ketek.network.ApiService;
import com.example.lazismu.ketek.network.RetroClient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarAmbulan extends AppCompatActivity implements DataAmbulanAdapter.OnFunction {

    @BindView(R.id.pb)
    ProgressBar pb;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.txt_notif)
    TextView txtNotif;
    @BindView(R.id.tambah)
    FloatingActionButton tambah;
    ArrayList<DataAmbulan2Item> listPencarian;
    DataAmbulanAdapter adapterPesan1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.wrap)
    AppBarLayout wrap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_ambulan);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Data Ambulan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvList.setLayoutManager(new LinearLayoutManager(DaftarAmbulan.this));
        // hide fab
        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && tambah.getVisibility() == View.VISIBLE) {
                    tambah.hide();
                    txtNotif.setVisibility(View.GONE);
                } else if (dy < 0 && tambah.getVisibility() != View.VISIBLE) {
                    tambah.show();
                    txtNotif.setVisibility(View.VISIBLE);
                }
            }
        });

        getDataAmbulan();
    }

    private void getDataAmbulan() {
        pb.setVisibility(View.VISIBLE);
        ApiService apiService = RetroClient.getApiService();
        Call<ResponseAmbulan2> call = apiService.getDataAmbulan();
        call.enqueue(new Callback<ResponseAmbulan2>() {
            @Override
            public void onResponse(Call<ResponseAmbulan2> call, Response<ResponseAmbulan2> response) {
                final List<DataAmbulan2Item> hasilPesan = response.body().getDataAmbulan2();
                Log.e("Tag", "Hasil List :" + new Gson().toJson(hasilPesan));

                if (response.body().getResponse().equalsIgnoreCase("true")) {
                    try {
                        rvList.setVisibility(View.VISIBLE);
                        final ArrayList<DataAmbulan2Item> list = new ArrayList<>();
                        list.addAll(hasilPesan);

                        listPencarian = new ArrayList<>();
                        listPencarian.addAll(hasilPesan);

                        //  swipeMain.setRefreshing(false);
                        adapterPesan1 = new DataAmbulanAdapter(DaftarAmbulan.this, hasilPesan, DaftarAmbulan.this);
                        rvList.setAdapter(adapterPesan1);
                        adapterPesan1.notifyDataSetChanged();
                        pb.setVisibility(View.GONE);
                    } catch (Exception e) {
                        Toast.makeText(DaftarAmbulan.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        pb.setVisibility(View.GONE);
                    }
                } else {
                    Log.e("Tag", "Gagal req data ");
                    Toast.makeText(DaftarAmbulan.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    pb.setVisibility(View.GONE);
                    rvList.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseAmbulan2> call, Throwable t) {
                Toast.makeText(DaftarAmbulan.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                pb.setVisibility(View.GONE);
                rvList.setVisibility(View.GONE);
            }
        });
    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return false;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDelete(final DataAmbulan2Item data) {
        AlertDialog.Builder aleBuilder = new AlertDialog.Builder(DaftarAmbulan.this);
        //settting judul dan pesan
        aleBuilder.setTitle("Hapus Data");
        aleBuilder
                .setMessage("Apakah anda yakin ingin menghapus data ini?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        hapusData(data.getIdAmbulan(), "ambulan", "id_ambulan");
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //cancel
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = aleBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onEdit(DataAmbulan2Item data) {
        //Toast.makeText(this, data.getNama(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(DaftarAmbulan.this, AddAmbulan.class);
        intent.putExtra("data", data);
        startActivity(intent);
    }

    public void hapusData(final String id, final String tabel, final String cari) {
        ApiService apiService = RetroClient.getApiService();
        Call<ResponseInsert> call = apiService.delete(tabel, cari, id);
        call.enqueue(new Callback<ResponseInsert>() {
            @Override
            public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                try {
                    if (response.body().getCode() == 200) {
                        Toast.makeText(DaftarAmbulan.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        getDataAmbulan();
                    } else {
                        Toast.makeText(DaftarAmbulan.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(DaftarAmbulan.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseInsert> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.tambah)
    public void onViewClicked() {
        startActivity(new Intent(DaftarAmbulan.this, AddAmbulan.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        getDataAmbulan();
    }
}