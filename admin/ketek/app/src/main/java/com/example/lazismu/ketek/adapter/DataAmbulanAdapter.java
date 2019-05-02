package com.example.lazismu.ketek.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lazismu.ketek.R;
import com.example.lazismu.ketek.model.DataAmbulan2Item;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.lazismu.ketek.helper.ConvertDate.ubahTanggal2;

public class DataAmbulanAdapter extends RecyclerView.Adapter<DataAmbulanAdapter.ViewHolder> {
    Context mContext;
    List<DataAmbulan2Item> mList;
    private OnFunction listener;

    public DataAmbulanAdapter(Context mContext, List<DataAmbulan2Item> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public DataAmbulanAdapter(Context mContext, List<DataAmbulan2Item> mList, OnFunction listener) {
        this.mContext = mContext;
        this.mList = mList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_data_ambulan, parent, false);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.nama.setText(mList.get(position).getNama());
        holder.txtNoPol.setText(mList.get(position).getNoPol());
        holder.txtKondisi.setText(mList.get(position).getKondisi());
        holder.txtStatus.setText(mList.get(position).getStatus());

        holder.cv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final CharSequence[] items = {"Edit", "Delete"};

                new AlertDialog.Builder(mContext).setTitle("Data Ambulan")
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
        @BindView(R.id.nama)
        TextView nama;
        @BindView(R.id.txt_no_pol)
        TextView txtNoPol;
        @BindView(R.id.txt_kondisi)
        TextView txtKondisi;
        @BindView(R.id.txt_status)
        TextView txtStatus;
        @BindView(R.id.cv)
        CardView cv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnFunction {
        void onDelete(DataAmbulan2Item data);

        void onEdit(DataAmbulan2Item data);
    }
}
