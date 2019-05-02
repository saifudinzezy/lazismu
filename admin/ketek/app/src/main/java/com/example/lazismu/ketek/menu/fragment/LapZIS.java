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
import com.example.lazismu.ketek.adapter.ZISAdapter;
import com.example.lazismu.ketek.menu.Laporan;
import com.example.lazismu.ketek.model.AmbulanItem;
import com.example.lazismu.ketek.model.ResponseInsert;
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
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.lazismu.ketek.helper.ConvertDate.customTanggal;
import static com.example.lazismu.ketek.helper.FunctionError.getTextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class LapZIS extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener{
    @BindView(R.id.pb)
    ProgressBar pb;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    Unbinder unbinder;
    SharedLogin sharedLogin;
    @BindView(R.id.txt_tahun)
    TextView txtTahun;
    @BindView(R.id.txt_zis)
    TextView txtZis;
    @BindView(R.id.sp_tahun)
    Spinner spTahun;
    @BindView(R.id.sp_zis)
    Spinner spZis;
    @BindView(R.id.cari)
    ImageButton cari;
    @BindView(R.id.txt_notif)
    TextView txtNotif;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    EditText edtPesan;
    Spinner spStatus;
    String year, zis, status;
    ArrayAdapter<String> adapterSP, adapterSPZIS, adapterStatus;
    ArrayList<ZisItem> listPencarian;

    public LapZIS() {
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
        View view = inflater.inflate(R.layout.activity_lap_zis, container, false);
        unbinder = ButterKnife.bind(this, view);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        sharedLogin = new SharedLogin(getActivity());
        
        getZIS();

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

        spZis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                zis = adapterSPZIS.getItem(position).toString();
                if (!zis.isEmpty()) {
                    //getYear(year, sharedLogin.getNip());
                    //Toast.makeText(KinerjaProd.this, "" + cariNomerBulan(bulan), Toast.LENGTH_SHORT).show();
                    txtZis.setText(zis);
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

    private void getZIS() {
        pb.setVisibility(View.VISIBLE);
        ApiService apiService = RetroClient.getApiService();
        Call<ResponseZIS> call = apiService.getZIS();
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

                        listPencarian = new ArrayList<>();
                        listPencarian.addAll(hasilPesan);

                        ArrayList<String> listTahun = new ArrayList<String>();

                        for (int j = 0; j < hasilPesan.size(); j++) {
                            Log.d("Tahun", "tahun list A -> " + hasilPesan);
                            String thn = customTanggal(hasilPesan.get(j).getTanggal(), "yyyy-MM-dd", "yyyy");
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

                        ArrayList<String> listZIS = new ArrayList<String>();

                        for (int j = 0; j < hasilPesan.size(); j++) {
                            Log.d("Tahun", "tahun list A -> " + hasilPesan);
                            String thn = hasilPesan.get(j).getKategori();
                            //jika data tidak sama (contains) maka masukan data
                            if (!listZIS.contains(thn)) {
                                listZIS.add(thn);
                                Log.d("Tahun", "tahun list B -> " + listZIS);
                            }
                        }
                        //spinner
                        adapterSPZIS = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item,
                                listZIS);
                        //adapterSP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //set adapter
                        spZis.setAdapter(adapterSPZIS);
                        //load jika ada data baru
                        adapterSPZIS.notifyDataSetChanged();

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

                            @Override
                            public void onEdit(final ZisItem data) {
                                dialog = new AlertDialog.Builder(getActivity());
                                //buat layout
                                inflater = getLayoutInflater();
                                dialogView = inflater.inflate(R.layout.edit_zis, null);
                                dialog.setView(dialogView);
                                dialog.setCancelable(true);
                                dialog.setIcon(R.drawable.ic_zakat1);
                                dialog.setTitle("Ubah Data");

                                //inisialisasi
                                edtPesan = dialogView.findViewById(R.id.edt_pesan);
                                spStatus = dialogView.findViewById(R.id.sp_status);

                                //create spiner
                                adapterStatus = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item,
                                        getActivity().getResources().getStringArray(R.array.status_zis));
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
                                            updateWithField(data.getIdZis(), status);
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
            public void onFailure(Call<ResponseZIS> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                pb.setVisibility(View.GONE);
                rvList.setVisibility(View.GONE);
            }
        });
    }

    private void getZIS(String tahun, String zis) {
        pb.setVisibility(View.VISIBLE);
        ApiService apiService = RetroClient.getApiService();
        Call<ResponseZIS> call = apiService.getZIS(tahun, zis);
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

                            @Override
                            public void onEdit(final ZisItem data) {
                                dialog = new AlertDialog.Builder(getActivity());
                                //buat layout
                                inflater = getLayoutInflater();
                                dialogView = inflater.inflate(R.layout.edit_zis, null);
                                dialog.setView(dialogView);
                                dialog.setCancelable(true);
                                dialog.setIcon(R.drawable.ic_zakat1);
                                dialog.setTitle("Ubah Data");

                                //inisialisasi
                                edtPesan = dialogView.findViewById(R.id.edt_pesan);
                                spStatus = dialogView.findViewById(R.id.sp_status);

                                //create spiner
                                adapterStatus = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item,
                                        getActivity().getResources().getStringArray(R.array.status_zis));
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
                                        updateWithField(data.getIdZis(), status);
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

    @OnClick(R.id.cari)
    public void onViewClicked() {
        getZIS(getTextView(txtTahun), getTextView(txtZis));
    }

    public void updateWithField(String id, String nama) {
        ApiService service = RetroClient.getApiService();
        Call<ResponseInsert> call = service.updateZIS(id, status);

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Cari Name");

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText == null || newText.trim().isEmpty()) {
            resetSearch();
            return false;
        }

        List<ZisItem> filteredValues = new ArrayList<>(listPencarian);
        for (ZisItem value : listPencarian) {
            if (!value.getStatus().toLowerCase().contains(newText.toLowerCase())) {
                filteredValues.remove(value);
            }
        }

        ZISAdapter zisAdapter = new ZISAdapter(getContext(), filteredValues, new ZISAdapter.OnFunction() {
            @Override
            public void onDelete(ZisItem data) {

            }

            @Override
            public void onEdit(ZisItem data) {

            }
        });
        rvList.setAdapter(zisAdapter);
        return false;
    }

    private void resetSearch() {
        ZISAdapter zisAdapter = new ZISAdapter(getContext(), listPencarian, new ZISAdapter.OnFunction() {
            @Override
            public void onDelete(ZisItem data) {

            }

            @Override
            public void onEdit(ZisItem data) {

            }
        });
        rvList.setAdapter(zisAdapter);
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return true;
    }
}
