package com.example.lazismu.ketek.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lazismu.ketek.R;
import com.example.lazismu.ketek.model.ZisItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.lazismu.ketek.helper.Constan.URL_IMAGE;
import static com.example.lazismu.ketek.helper.ConvertDate.ubahTanggal2;

public class ZISAdapter extends RecyclerView.Adapter<ZISAdapter.ViewHolder> {
    Context mContext;
    List<ZisItem> mList;
    private OnFunction listener;

    public ZISAdapter(Context mContext, List<ZisItem> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public ZISAdapter(Context mContext, List<ZisItem> mList, OnFunction listener) {
        this.mContext = mContext;
        this.mList = mList;
        this.listener = listener;
    }

    public List<ZisItem> getmList() {
        return mList;
    }

    public void setmList(List<ZisItem> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_zis, parent, false);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //ini link untuk gambar
        String linkGambarMovie = mList.get(position).getGambar();
        Glide.with(mContext)
                .load(URL_IMAGE + linkGambarMovie)
                .into(holder.imageView);

        holder.nama.setText(mList.get(position).getNama());
        holder.txtNominal.setText(mList.get(position).getNominal());
        holder.kategori.setText(mList.get(position).getKategori());
        holder.txtTanggal.setText(ubahTanggal2(mList.get(position).getTanggal()));
        holder.selesai.setText(mList.get(position).getStatus());
        if (mList.get(position).getStatus().toString().equalsIgnoreCase("Menunggu")) {
            //yellow
            holder.background.setBackgroundColor(Color.parseColor("#ffb300"));
        } else if (mList.get(position).getStatus().toString().equalsIgnoreCase("Diterima")) {
            //green
            holder.background.setBackgroundColor(Color.parseColor("#00796B"));
        } else {
            //red
            holder.background.setBackgroundColor(Color.parseColor("#E43F3F"));
        }

        holder.cv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final CharSequence[] items = {"Edit", "Delete"};

                new AlertDialog.Builder(mContext).setTitle("Laporan")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                if (item == 0) {
                                    listener.onEdit(mList.get(position));
                                } else {
                                    listener.onDelete(mList.get(position));
                                }

                                dialog.dismiss();
                            }
                        }).show();

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.textView)
        TextView textView;
        @BindView(R.id.txt_judul)
        TextView txtJudul;
        @BindView(R.id.txt_tanggal)
        TextView txtTanggal;
        @BindView(R.id.txt_nominal)
        TextView txtNominal;
        @BindView(R.id.kategori)
        TextView kategori;
        @BindView(R.id.selesai)
        TextView selesai;
        @BindView(R.id.nama)
        TextView nama;
        @BindView(R.id.background)
        LinearLayout background;
        @BindView(R.id.cv)
        CardView cv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnFunction {
        void onDelete(ZisItem data);

        void onEdit(ZisItem data);
    }
}
