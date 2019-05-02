package com.example.lazismu.ketek.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lazismu.ketek.R;
import com.example.lazismu.ketek.menu.add.AddBerita;
import com.example.lazismu.ketek.model.BeritaItem;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.lazismu.ketek.helper.Constan.KEY_DATA;
import static com.example.lazismu.ketek.helper.Constan.URL_IMAGE;

public class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.ViewHolder> {
    Context mContext;
    List<BeritaItem> mList;
    private OnFunction listener;

    public BeritaAdapter(Context mContext, List<BeritaItem> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public BeritaAdapter(Context mContext, List<BeritaItem> mList, OnFunction listener) {
        this.mContext = mContext;
        this.mList = mList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_berita, parent, false);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //ini link untuk gambar
        String linkGambarMovie = mList.get(position).getGambar();
        Glide.with(mContext)
                .load(URL_IMAGE + linkGambarMovie)
                .into(holder.imageView);

        holder.txtJudul.setText(mList.get(position).getJudul());
        holder.txtBerita.setHtml(mList.get(position).getArtikel());

        holder.cv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final CharSequence[] items = {"Edit", "Delete"};

                new AlertDialog.Builder(mContext).setTitle("Laporan")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                if (item == 0) {
                                    BeritaItem data = mList.get(position);
                                    Intent intent = new Intent(mContext, AddBerita.class);
                                    intent.putExtra(KEY_DATA, data);
                                    mContext.startActivity(intent);
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
        @BindView(R.id.txt_judul)
        TextView txtJudul;
        @BindView(R.id.txt_berita)
        HtmlTextView txtBerita;
        @BindView(R.id.cv)
        CardView cv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnFunction {
        void onDelete(BeritaItem data);
    }
}
