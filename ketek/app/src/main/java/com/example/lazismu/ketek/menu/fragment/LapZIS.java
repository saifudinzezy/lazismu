package com.example.lazismu.ketek.menu.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.lazismu.ketek.R;
import com.example.lazismu.ketek.adapter.BeritaAdapter;
import com.example.lazismu.ketek.adapter.ZISAdapter;
import com.example.lazismu.ketek.menu.Berita;
import com.example.lazismu.ketek.model.ResponseInsert;
import com.example.lazismu.ketek.model.ZisItem;
import com.example.lazismu.ketek.model.ResponseZIS;
import com.example.lazismu.ketek.model.ResponseZIS;
import com.example.lazismu.ketek.model.ZisItem;
import com.example.lazismu.ketek.network.ApiService;
import com.example.lazismu.ketek.network.RetroClient;
import com.example.lazismu.ketek.shared.SharedLogin;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LapZIS extends Fragment{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.wrap)
    AppBarLayout wrap;
    @BindView(R.id.pb)
    ProgressBar pb;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    Unbinder unbinder;
    SharedLogin sharedLogin;

    public LapZIS() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_berita2, container, false);
        unbinder = ButterKnife.bind(this, view);
        toolbar.setVisibility(View.GONE);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        sharedLogin = new SharedLogin(getActivity());

        getZIS();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getZIS() {
        pb.setVisibility(View.VISIBLE);
        ApiService apiService = RetroClient.getApiService();
        Call<ResponseZIS> call = apiService.getZIS(sharedLogin.getSharedString(SharedLogin.SP_ID));
        call.enqueue(new Callback<ResponseZIS>() {
            @Override
            public void onResponse(Call<ResponseZIS> call, Response<ResponseZIS> response) {
                final List<ZisItem> hasilPesan = response.body().getZis();
                Log.e("Tag", "Hasil List :" + new Gson().toJson(hasilPesan));

                if (response.body().getResponse().equalsIgnoreCase("true")) {
                    try {
                        rvList.setVisibility(View.VISIBLE);
                        final ArrayList<ZisItem> list = new ArrayList<>();
                        list.addAll(hasilPesan);

                        ZISAdapter adapterPesan1 = new ZISAdapter(getContext(), list, new ZISAdapter.OnFunction() {
                            @Override
                            public void onDelete(final ZisItem data) {
                                AlertDialog.Builder aleBuilder = new AlertDialog.Builder(getContext());
                                //settting judul dan pesan
                                aleBuilder.setTitle("Hapus Data");
                                aleBuilder
                                        .setMessage("Apakah anda yakin ingin menghapus data ini?")
                                        .setCancelable(false)
                                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                hapusData(data.getIdZis(), "zis", "id_zis");
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
                        });
                        //  swipeMain.setRefreshing(false);
                        rvList.setAdapter(adapterPesan1);
                        adapterPesan1.notifyDataSetChanged();
                        pb.setVisibility(View.GONE);
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        pb.setVisibility(View.GONE);
                    }
                } else {
                    Log.e("Tag", "Gagal req data ");
                    Toast.makeText(getContext(), "Data Kosong", Toast.LENGTH_SHORT).show();
                    pb.setVisibility(View.GONE);
                    rvList.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseZIS> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                pb.setVisibility(View.GONE);
                rvList.setVisibility(View.GONE);
            }
        });
    }

    public void hapusData(final String id, final String tabel, final String cari) {
        ApiService apiService = RetroClient.getApiService();
        Call<ResponseInsert> call = apiService.delete(tabel, cari, id);
        call.enqueue(new Callback<ResponseInsert>() {
            @Override
            public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                try {
                    if (response.body().getCode() == 200) {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        getZIS();
                    } else {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseInsert> call, Throwable t) {

            }
        });
    }
}
