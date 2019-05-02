package com.example.lazismu.ketek.menu.zakat.kalkuza;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lazismu.ketek.R;
import com.example.lazismu.ketek.menu.zakat.item.Profesi;

public class kalkuza extends AppCompatActivity {
    private Button butkalku;
    private EditText a, b;

    private double gaji, keluar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalkuza);

        a = (EditText) findViewById(R.id.edpenghasilan);
        b = (EditText) findViewById(R.id.edpengeluaran);
        butkalku = findViewById(R.id.butkalku);

        butkalku.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if(a.length()==0 && b.length()==0){
                    Toast.makeText(getApplication(),"Gaji dan Pengeluaran tidak boleh Kosong",Toast.LENGTH_LONG).show();
                }
                //notifikasi ini akan muncul jika panjangnya tidak diisi
                else if (a.length()==0){
                    Toast.makeText(getApplication(),"Gaji tidak boleh kososng", Toast.LENGTH_LONG).show();
                }
                //notifikasi jika lebar tidak diisi
                else if (b.length()==0){
                    Toast.makeText(getApplication(),"Pengeluaran tidak boleh kosong",Toast.LENGTH_LONG).show();
                }
                else{
                    String isigaji = a.getText().toString();
                    String isipengeluaran = b.getText().toString();
                    double a = Double.parseDouble(isigaji);
                    double b = Double.parseDouble(isipengeluaran);
                    double fix = Zakat(a,b);
                    Intent intent = new Intent(kalkuza.this, Profesi.class);
                intent.putExtra("jumlah", fix);
                startActivity(intent);
                finish();

                }
            }
        });


    }
    public double Zakat(double a, double b){return (a-b)*2.5/100;}
}
