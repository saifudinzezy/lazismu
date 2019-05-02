package com.example.lazismu.ketek;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

public class kalkugaji extends AppCompatActivity {
    private Button butkalkulasi;
    private EditText edmas, edpertanian, edpertanian1, edbarangTemuan, edperdagangan, edternak, edgalian, edhasil1;


    private double mas, pertanian, pertanian1, barangTemuan, perdagangan, ternak, galian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalkugaji);

        edmas = (EditText) findViewById(R.id.edmas);
        edpertanian = (EditText) findViewById(R.id.edpertanian);
        edpertanian1 = (EditText) findViewById(R.id.edpertanian1);
        edbarangTemuan = (EditText) findViewById(R.id.edtemuan);
        edperdagangan = (EditText) findViewById(R.id.edperdagangan);
        edternak = (EditText) findViewById(R.id.edternak);
        edgalian = (EditText) findViewById(R.id.edgalian);
        edhasil1 = (EditText) findViewById(R.id.edhasil1);

        butkalkulasi = (Button) findViewById(R.id.butkalkulasi);

        butkalkulasi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (TextUtils.isEmpty(edmas.getText())){
                    mas = 0;
                }else {
                    mas = hitungmasMas(Integer.parseInt(edmas.getText().toString()));
                } // end if

                if (TextUtils.isEmpty(edpertanian.getText())){
                    pertanian = 0;
                }else {
                    pertanian = hitungPertanian(Integer.parseInt(edpertanian.getText().toString()));
                } //end if

                if (TextUtils.isEmpty(edpertanian1.getText())){
                    pertanian1 = 0;
                }else {
                    pertanian1 = hitungPertanian1(Integer.parseInt(edpertanian1.getText().toString()));
                } //end if

                if (TextUtils.isEmpty(edbarangTemuan.getText())){
                    barangTemuan = 0;
                }else {
                    barangTemuan = hitungtemuan(Integer.parseInt(edbarangTemuan.getText().toString()));
                } //end if

                if (TextUtils.isEmpty(edperdagangan.getText())){
                    perdagangan = 0;
                }else {
                    perdagangan = hitungmasPerdagangan(Integer.parseInt(edperdagangan.getText().toString()));
                } //end if

                if (TextUtils.isEmpty(edternak.getText())){
                    ternak = 0;
                }else {
                    ternak = hitungternak(Integer.parseInt(edternak.getText().toString()));
                } //end if

                if (TextUtils.isEmpty(edgalian.getText())){
                    galian = 0;
                }else {
                    galian = hitunggalian(Integer.parseInt(edgalian.getText().toString()));
                } //end if


                double fix = mas+pertanian+pertanian1+barangTemuan+perdagangan+ternak+galian;
//                edhasil1.setText(""+fix);
                Intent intent = new Intent(kalkugaji.this, Harta.class);
                intent.putExtra("hasil", fix);
                startActivity(intent);
                finish();
            }
        });


    }

    private double hitungmasMas(int nilai){
        return (nilai * 2.5) / 100 ;
    }

    private double hitungPertanian(int nilai){
        return (nilai * 10) / 100 ;
    }

    private double hitungPertanian1(int nilai){
        return (nilai * 5) / 100 ;
    }

    private double hitungtemuan(int nilai){
        return (nilai * 20) / 100 ;
    }

    private double hitungmasPerdagangan(int nilai){
        return (nilai * 2.5) / 100 ;
    }

    private double hitungternak(int nilai){
        return (nilai * 2.5) / 100 ;
    }

    private double hitunggalian(int nilai){
        return (nilai * 2.5) / 100 ;
    }

}
