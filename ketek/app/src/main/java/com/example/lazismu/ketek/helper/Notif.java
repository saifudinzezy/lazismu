package com.example.lazismu.ketek.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.lazismu.ketek.R;

public class Notif {

    public static void notif(Context context){
        //buat object alarm
        AlertDialog.Builder aleBuilder = new AlertDialog.Builder(context);
        //settting judul dan pesan
        aleBuilder.setTitle("Jazakumullah Khoir!!!");
        aleBuilder
                .setMessage("Dana yang terkumpul akan diSalurkan kePihak yang Membutuhkan.")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                /*.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //cancel
                        dialog.cancel();
                    }
                });*/
        AlertDialog alertDialog = aleBuilder.create();
        alertDialog.show();
    }
}
