package com.example.lazismu.ketek.menu.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lazismu.ketek.R;
import com.example.lazismu.ketek.adapter.AmbulanAdapter;
import com.example.lazismu.ketek.menu.Laporan;
import com.example.lazismu.ketek.model.AmbulanItem;
import com.example.lazismu.ketek.model.ResponseAmbulan;
import com.example.lazismu.ketek.model.ResponseInsert;
import com.example.lazismu.ketek.network.ApiService;
import com.example.lazismu.ketek.network.RetroClient;
import com.example.lazismu.ketek.shared.SharedLogin;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.lazismu.ketek.helper.CariBulan.cariNamaBulan;
import static com.example.lazismu.ketek.helper.CariBulan.cariNomerBulan;
import static com.example.lazismu.ketek.helper.ConvertDate.customTanggal;
import static com.example.lazismu.ketek.helper.FunctionError.cekEditText;
import static com.example.lazismu.ketek.helper.FunctionError.getTextEditText;
import static com.example.lazismu.ketek.helper.FunctionError.getTextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class LapAmbulan extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    @BindView(R.id.pb)
    ProgressBar pb;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    Unbinder unbinder;
    SharedLogin sharedLogin;
    @BindView(R.id.txt_bulan)
    TextView txtBulan;
    @BindView(R.id.txt_tahun)
    TextView txtTahun;
    @BindView(R.id.sp_bulan)
    Spinner spBulan;
    @BindView(R.id.sp_tahun)
    Spinner spTahun;
    @BindView(R.id.cari)
    ImageButton cari;
    @BindView(R.id.txt_notif)
    TextView txtNotif;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    EditText edtPesan;
    Spinner spStatus;
    String year, bulan, status;
    ArrayAdapter<String> adapterSP, adapterSPBulan, adapterStatus;
    ArrayList<AmbulanItem> proSearch = new ArrayList<>();

    public LapAmbulan() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_lap_ambulan, container, false);
        unbinder = ButterKnife.bind(this, view);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        sharedLogin = new SharedLogin(getActivity());
        getAmbulan();
        spTahun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = adapterSP.getItem(position).toString();
                if (!year.isEmpty()) {
                    //getYear(year, sharedLogin.getNip());
                    txtTahun.setText(year);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spBulan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bulan = adapterSPBulan.getItem(position).toString();
                if (!bulan.isEmpty()) {
                    //getYear(year, sharedLogin.getNip());
                    //Toast.makeText(KinerjaProd.this, "" + cariNomerBulan(bulan), Toast.LENGTH_SHORT).show();
                    txtBulan.setText(cariNomerBulan(bulan));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getAmbulan() {
        pb.setVisibility(View.VISIBLE);
        ApiService apiService = RetroClient.getApiService();
        Call<ResponseAmbulan> call = apiService.getAmbulan();
        call.enqueue(new Callback<ResponseAmbulan>() {
            @Override
            public void onResponse(Call<ResponseAmbulan> call, Response<ResponseAmbulan> response) {
                final List<AmbulanItem> hasilPesan = response.body().getAmbulan();
                Log.e("Tag", "Hasil List :" + new Gson().toJson(hasilPesan));

                if (response.body().getResponse().equalsIgnoreCase("true")) {
                    try {
                        rvList.setVisibility(View.VISIBLE);
                        final ArrayList<AmbulanItem> list = new ArrayList<>();
                        list.addAll(hasilPesan);
                        ArrayList<String> listBulan = new ArrayList<String>();
                        proSearch.addAll(hasilPesan);

                        for (int j = 0; j < hasilPesan.size(); j++) {
                            Log.d("bulan", "bulan list A -> " + hasilPesan);
                            String thn = cariNamaBulan(customTanggal(hasilPesan.get(j).getTglPinjam(), "yyyy-MM-dd", "MM"));
                            //jika data tidak sama (contains) maka masukan data
                            if (!listBulan.contains(thn)) {
                                listBulan.add(thn);
                                Log.d("bulan", "bulan list B -> " + listBulan);
                            }
                        }
                        //spinner
                        adapterSPBulan = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item,
                                listBulan);
                        //adapterSP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //set adapter
                        spBulan.setAdapter(adapterSPBulan);
                        //load jika ada data baru
                        adapterSPBulan.notifyDataSetChanged();

                        ArrayList<String> listTahun = new ArrayList<String>();

                        for (int j = 0; j < hasilPesan.size(); j++) {
                            Log.d("Tahun", "tahun list A -> " + hasilPesan);
                            String thn = customTanggal(hasilPesan.get(j).getTglPinjam(), "yyyy-MM-dd", "yyyy");
                            //jika data tidak sama (contains) maka masukan data
                            if (!listTahun.contains(thn)) {
                                listTahun.add(thn);
                                Log.d("Tahun", "tahun list B -> " + listTahun);
                            }
                        }
                        //spinner
                        adapterSP = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item,
                                listTahun);
                        //adapterSP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //set adapter
                        spTahun.setAdapter(adapterSP);
                        //load jika ada data baru
                        adapterSP.notifyDataSetChanged();

                        AmbulanAdapter adapterPesan1 = new AmbulanAdapter(getContext(), list, new AmbulanAdapter.OnFunction() {
                            @Override
                            public void onDelete(final AmbulanItem data) {
                                AlertDialog.Builder aleBuilder = new AlertDialog.Builder(getContext());
                                //settting judul dan pesan
                                aleBuilder.setTitle("Hapus Data");
                                aleBuilder
                                        .setMessage("Apakah anda yakin ingin menghapus data ini?")
                                        .setCancelable(false)
                                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                hapusData(data.getIdAmbulan(), "pinjam_ambulan", "id_ambulan");
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
                            public void onEdit(final AmbulanItem data) {
                                dialog = new AlertDialog.Builder(getActivity());
                                //buat layout
                                inflater = getLayoutInflater();
                                dialogView = inflater.inflate(R.layout.edit_ambulan, null);
                                dialog.setView(dialogView);
                                dialog.setCancelable(true);
                                dialog.setIcon(R.drawable.ic_ambulance);
                                dialog.setTitle("Ubah Data");

                                //inisialisasi
                                edtPesan = dialogView.findViewById(R.id.edt_pesan);
                                spStatus = dialogView.findViewById(R.id.sp_status);

                                //create spiner
                                adapterStatus = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item,
                                        getActivity().getResources().getStringArray(R.array.status_ambulan));
                                spStatus.setAdapter(adapterStatus);

                                spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        //Toast.makeText(SKP.this, kuantitasItems.get(position).getKuantitas(), Toast.LENGTH_SHORT).show();
                                        status = adapterStatus.getItem(position);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                                dialog.setPositiveButton("UBAH", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (cekEditText(edtPesan)) {
                                            edtPesan.setText(data.getPesan());
                                            updateWithField(data.getIdAmbulan(), data.getNamaDriver(), data.getTglPinjam(),
                                                    data.getTglKembali(), data.getTujuan(), getTextEditText(edtPesan), status,
                                                    data.getIdAmbulan2(), data.getSim());
                                            if (status.equalsIgnoreCase("Diijinkan")){
                                                updateStatusAmbulan(data.getIdAmbulan2(), "Dipinjam");
                                            }
                                        } else {
                                            updateWithField(data.getIdAmbulan(), data.getNamaDriver(), data.getTglPinjam(),
                                                    data.getTglKembali(), data.getTujuan(), getTextEditText(edtPesan), status,
                                                    data.getIdAmbulan2(), data.getSim());
                                            if (status.equalsIgnoreCase("Diijinkan")){
                                                updateStatusAmbulan(data.getIdAmbulan2(), "Dipinjam");
                                            }
                                        }
                                        dialog.dismiss();
                                    }
                                });

                                //setting button
                                dialog.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        //keluar
                                        dialog.dismiss();
                                        //kosong();
                                    }
                                });
                                //show dialog
                                dialog.show();
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
            public void onFailure(Call<ResponseAmbulan> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                pb.setVisibility(View.GONE);
                rvList.setVisibility(View.GONE);
            }
        });
    }

    private void getAmbulan(String bulan, String tahun) {
        pb.setVisibility(View.VISIBLE);
        ApiService apiService = RetroClient.getApiService();
        Call<ResponseAmbulan> call = apiService.getAmbulan(bulan, tahun);
        call.enqueue(new Callback<ResponseAmbulan>() {
            @Override
            public void onResponse(Call<ResponseAmbulan> call, Response<ResponseAmbulan> response) {
                final List<AmbulanItem> hasilPesan = response.body().getAmbulan();
                Log.e("Tag", "Hasil List :" + new Gson().toJson(hasilPesan));

                if (response.body().getResponse().equalsIgnoreCase("true")) {
                    try {
                        rvList.setVisibility(View.VISIBLE);
                        final ArrayList<AmbulanItem> list = new ArrayList<>();
                        list.addAll(hasilPesan);

                        AmbulanAdapter adapterPesan1 = new AmbulanAdapter(getContext(), list, new AmbulanAdapter.OnFunction() {
                            @Override
                            public void onDelete(final AmbulanItem data) {
                                AlertDialog.Builder aleBuilder = new AlertDialog.Builder(getContext());
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
                            public void onEdit(final AmbulanItem data) {
                                dialog = new AlertDialog.Builder(getActivity());
                                //buat layout
                                inflater = getLayoutInflater();
                                dialogView = inflater.inflate(R.layout.edit_ambulan, null);
                                dialog.setView(dialogView);
                                dialog.setCancelable(true);
                                dialog.setIcon(R.drawable.ic_ambulance);
                                dialog.setTitle("Ubah Data");

                                //inisialisasi
                                edtPesan = dialogView.findViewById(R.id.edt_pesan);
                                spStatus = dialogView.findViewById(R.id.sp_status);

                                //create spiner
                                adapterStatus = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item,
                                        getActivity().getResources().getStringArray(R.array.status_ambulan));
                                spStatus.setAdapter(adapterStatus);

                                spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        //Toast.makeText(SKP.this, kuantitasItems.get(position).getKuantitas(), Toast.LENGTH_SHORT).show();
                                        status = adapterStatus.getItem(position);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                                dialog.setPositiveButton("UBAH", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (cekEditText(edtPesan)) {
                                            edtPesan.setText(data.getPesan());
                                            updateWithField(data.getIdAmbulan(), data.getNamaDriver(), data.getTglPinjam(),
                                                    data.getTglKembali(), data.getTujuan(), getTextEditText(edtPesan), status,
                                                    data.getIdAmbulan2(), data.getSim());
                                            if (status.equalsIgnoreCase("Diijinkan")){
                                                updateStatusAmbulan(data.getIdAmbulan2(), "Dipinjam");
                                            }
                                        } else {
                                            updateWithField(data.getIdAmbulan(), data.getNamaDriver(), data.getTglPinjam(),
                                                    data.getTglKembali(), data.getTujuan(), getTextEditText(edtPesan), status,
                                                    data.getIdAmbulan2(), data.getSim());
                                            if (status.equalsIgnoreCase("Diijinkan")){
                                                updateStatusAmbulan(data.getIdAmbulan2(), "Dipinjam");
                                            }
                                        }
                                        dialog.dismiss();
                                    }
                                });

                                //setting button
                                dialog.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        //keluar
                                        dialog.dismiss();
                                        //kosong();
                                    }
                                });
                                //show dialog
                                dialog.show();
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
            public void onFailure(Call<ResponseAmbulan> call, Throwable t) {
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
                        getAmbulan();
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

    public void updateWithField(String id, String nama, String tglPinjam, String tglKembali,
                                String tujuan, String pesan, String status, String idAmbulan, String sim) {
        ApiService service = RetroClient.getApiService();
        Call<ResponseInsert> call = service.updateAmbulan(Integer.parseInt(id), nama, tglPinjam, tglKembali, tujuan, pesan,
                status, idAmbulan, sim);

        call.enqueue(new Callback<ResponseInsert>() {
            @Override
            public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                if (response.body().getCode() == 200) {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    getActivity().startActivity(new Intent(getActivity(), Laporan.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                    //hancurkan activity
                } else {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseInsert> call, Throwable t) {
                Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.cari)
    public void onViewClicked() {
        getAmbulan(getTextView(txtBulan), getTextView(txtTahun));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Cari Nama");

        super.onCreateOptionsMenu(menu, inflater);
    }

    //implement all methode
    @Override
    public boolean onQueryTextSubmit(String s) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText == null || newText.trim().isEmpty()) {
            resetSearch();
            return false;
        }

        List<AmbulanItem> filteredValues = new ArrayList<>(proSearch);
        for (AmbulanItem value : proSearch) {
            if (!value.getNama().toLowerCase().contains(newText.toLowerCase())) {
                filteredValues.remove(value);
            }
        }

        AmbulanAdapter adapterPesan1 = new AmbulanAdapter(getContext(), filteredValues, new AmbulanAdapter.OnFunction() {
            @Override
            public void onDelete(final AmbulanItem data) {
                AlertDialog.Builder aleBuilder = new AlertDialog.Builder(getContext());
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
            public void onEdit(final AmbulanItem data) {
                dialog = new AlertDialog.Builder(getActivity());
                //buat layout
                inflater = getLayoutInflater();
                dialogView = inflater.inflate(R.layout.edit_ambulan, null);
                dialog.setView(dialogView);
                dialog.setCancelable(true);
                dialog.setIcon(R.drawable.ic_ambulance);
                dialog.setTitle("Ubah Data");

                //inisialisasi
                edtPesan = dialogView.findViewById(R.id.edt_pesan);
                spStatus = dialogView.findViewById(R.id.sp_status);

                //create spiner
                adapterStatus = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item,
                        getActivity().getResources().getStringArray(R.array.status_ambulan));
                spStatus.setAdapter(adapterStatus);

                spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //Toast.makeText(SKP.this, kuantitasItems.get(position).getKuantitas(), Toast.LENGTH_SHORT).show();
                        status = adapterStatus.getItem(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                dialog.setPositiveButton("UBAH", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (cekEditText(edtPesan)) {
                            edtPesan.setText(data.getPesan());
                            updateWithField(data.getIdAmbulan(), data.getNamaDriver(), data.getTglPinjam(),
                                    data.getTglKembali(), data.getTujuan(), getTextEditText(edtPesan), status,
                                    data.getIdAmbulan2(), data.getSim());
                            if (status.equalsIgnoreCase("Diijinkan")){
                                updateStatusAmbulan(data.getIdAmbulan2(), "Dipinjam");
                            }
                        } else {
                            updateWithField(data.getIdAmbulan(), data.getNamaDriver(), data.getTglPinjam(),
                                    data.getTglKembali(), data.getTujuan(), getTextEditText(edtPesan), status,
                                    data.getIdAmbulan2(), data.getSim());
                            if (status.equalsIgnoreCase("Diijinkan")){
                                updateStatusAmbulan(data.getIdAmbulan2(), "Dipinjam");
                            }
                        }
                        dialog.dismiss();
                    }
                });

                //setting button
                dialog.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        //keluar
                        dialog.dismiss();
                        //kosong();
                    }
                });
                //show dialog
                dialog.show();
            }
        });
        rvList.setAdapter(adapterPesan1);
        return false;
    }

    public void resetSearch() {
        AmbulanAdapter adapterPesan1 = new AmbulanAdapter(getContext(), proSearch, new AmbulanAdapter.OnFunction() {
            @Override
            public void onDelete(final AmbulanItem data) {
                AlertDialog.Builder aleBuilder = new AlertDialog.Builder(getContext());
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
            public void onEdit(final AmbulanItem data) {
                dialog = new AlertDialog.Builder(getActivity());
                //buat layout
                inflater = getLayoutInflater();
                dialogView = inflater.inflate(R.layout.edit_ambulan, null);
                dialog.setView(dialogView);
                dialog.setCancelable(true);
                dialog.setIcon(R.drawable.ic_ambulance);
                dialog.setTitle("Ubah Data");

                //inisialisasi
                edtPesan = dialogView.findViewById(R.id.edt_pesan);
                spStatus = dialogView.findViewById(R.id.sp_status);

                //create spiner
                adapterStatus = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item,
                        getActivity().getResources().getStringArray(R.array.status_ambulan));
                spStatus.setAdapter(adapterStatus);

                spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //Toast.makeText(SKP.this, kuantitasItems.get(position).getKuantitas(), Toast.LENGTH_SHORT).show();
                        status = adapterStatus.getItem(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                dialog.setPositiveButton("UBAH", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (cekEditText(edtPesan)) {
                            edtPesan.setText(data.getPesan());
                            updateWithField(data.getIdAmbulan(), data.getNamaDriver(), data.getTglPinjam(),
                                    data.getTglKembali(), data.getTujuan(), getTextEditText(edtPesan), status,
                                    data.getIdAmbulan2(), data.getSim());
                            if (status.equalsIgnoreCase("Diijinkan")){
                                updateStatusAmbulan(data.getIdAmbulan2(), "Dipinjam");
                            }
                        } else {
                            updateWithField(data.getIdAmbulan(), data.getNamaDriver(), data.getTglPinjam(),
                                    data.getTglKembali(), data.getTujuan(), getTextEditText(edtPesan), status,
                                    data.getIdAmbulan2(), data.getSim());
                            if (status.equalsIgnoreCase("Diijinkan")){
                                updateStatusAmbulan(data.getIdAmbulan2(), "Dipinjam");
                            }
                        }
                        dialog.dismiss();
                    }
                });

                //setting button
                dialog.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        //keluar
                        dialog.dismiss();
                        //kosong();
                    }
                });
                //show dialog
                dialog.show();
            }
        });
        rvList.setAdapter(adapterPesan1);
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem menuItem) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
        return true;
    }

    public void updateStatusAmbulan(String id, String status) {
        ApiService service = RetroClient.getApiService();
        Call<ResponseInsert> call = service.updateDataAmbulan(id, status);

        call.enqueue(new Callback<ResponseInsert>() {
            @Override
            public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                if (response.body().getCode() == 200) {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    getActivity().startActivity(new Intent(getActivity(), Laporan.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                    //hancurkan activity
                } else {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseInsert> call, Throwable t) {
                Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
