package com.example.lazismu.ketek.helper;

import android.content.Context;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.lazismu.ketek.helper.ConvertDate.ubahTanggal3;

public class CariTigaTanggal {

    public static boolean cariTigaTanggal(String tglAwal, String tglAkhir) {
        tglAwal = ubahTanggal3(tglAwal);
        tglAkhir = ubahTanggal3(tglAkhir);

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Date fromDate = null;
        Date toDate = null;

        try {
            fromDate = sdf1.parse(tglAwal);
            toDate = sdf1.parse(tglAkhir);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar c = Calendar.getInstance();
        c.setTime(fromDate);
        c.add(Calendar.DATE, 2); //berarti dia masuk tiga hari
        //terhitung dari tgl 2
        if (c.getTime().compareTo(toDate) < 0) {
            //Toast.makeText(context, "Tangga", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            //Toast.makeText(context, "Tanggal pinjam tidak boleh lebih dari tiga hari", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
