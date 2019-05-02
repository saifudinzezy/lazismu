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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lazismu.ketek.R;
import com.example.lazismu.ketek.model.AmbulanItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.lazismu.ketek.helper.ConvertDate.ubahTanggal2;

public class AmbulanAdapter extends RecyclerView.Adapter<AmbulanAdapter.ViewHolder> {
    Context mContext;
    List<AmbulanItem> mList;
    private OnFunction listener;

    public AmbulanAdapter(Context mContext, List<AmbulanItem> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public AmbulanAdapter(Context mContext, List<AmbulanItem> mList, OnFunction listener) {
        this.mContext = mContext;
        this.mList = mList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_ambulan, parent, false);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.txtTanggal.setText(ubahTanggal2(mList.get(position).getTglPinjam()) + " s/d " +
                ubahTanggal2(mList.get(position).getTglKembali()));
        holder.nama.setText(mList.get(position).getNama());
        holder.txtSupir.setText(mList.get(position).getNamaDriver());
        holder.txtTujuan.setText(mList.get(position).getTujuan());
        holder.txtPesan.setText(mList.get(position).getPesan());
        holder.txtStatus.setText(mList.get(position).getStatus());
        holder.txtMobil.setText(mList.get(position).getNamaAmbulan());
        if (mList.get(position).getStatus().toString().equalsIgnoreCase("Menunggu")) {
            //yellow
            holder.background.setBackgroundColor(Color.parseColor("#ffb300"));
        } else if (mList.get(position).getStatus().toString().equalsIgnoreCase("Diijinkan")) {
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
        @BindView(R.id.txt_tanggal)
        TextView txtTanggal;
        @BindView(R.id.txt_tujuan)
        TextView txtTujuan;
        @BindView(R.id.txt_pesan)
        TextView txtPesan;
        @BindView(R.id.txt_status)
        TextView txtStatus;
        @BindView(R.id.background)
        LinearLayout background;
        @BindView(R.id.cv)
        CardView cv;
        @BindView(R.id.txt_supir)
        TextView txtSupir;
        @BindView(R.id.nama)
        TextView nama;
        @BindView(R.id.wrapper_pesan)
        LinearLayout wrapperPesan;
        @BindView(R.id.txt_mobil)
        TextView txtMobil;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnFunction {
        void onDelete(AmbulanItem data);

        void onEdit(AmbulanItem data);
    }
}
