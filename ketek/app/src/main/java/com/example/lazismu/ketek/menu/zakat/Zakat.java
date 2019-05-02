package com.example.lazismu.ketek.menu.zakat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.example.lazismu.ketek.R;
import com.example.lazismu.ketek.helper.Buka;
import com.example.lazismu.ketek.menu.zakat.item.Perdagangan;
import com.example.lazismu.ketek.menu.zakat.item.Pertanian;
import com.example.lazismu.ketek.menu.zakat.item.Profesi;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.lazismu.ketek.helper.HtmlString.zakatPasar;
import static com.example.lazismu.ketek.helper.HtmlString.zakatPetani;
import static com.example.lazismu.ketek.helper.HtmlString.zakatProfesi;

public class Zakat extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.wb_zakat_gaji)
    WebView wbZakatGaji;
    @BindView(R.id.wb_petani)
    WebView wbPetani;
    @BindView(R.id.wb_pasar)
    WebView wbPasar;
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakat);
        ButterKnife.bind(this);
        konek();

        wbZakatGaji.getSettings().setJavaScriptEnabled(true);
        wbZakatGaji.loadData(zakatProfesi, "text/html; charset=utf-8", "UTF-8");

        wbPetani.getSettings().setJavaScriptEnabled(true);
        wbPetani.loadData(zakatPetani, "text/html; charset=utf-8", "UTF-8");

        wbPasar.getSettings().setJavaScriptEnabled(true);
        wbPasar.loadData(zakatPasar, "text/html; charset=utf-8", "UTF-8");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                //Toast.makeText(this, "klik", Toast.LENGTH_SHORT).show();
                Buka.buka(Zakat.this, Profesi.class);
                break;
            case R.id.btn2:
                Buka.buka(Zakat.this, Pertanian.class);
                break;
            case R.id.btn3:
                Buka.buka(Zakat.this, Pertanian.class);
                break;
            case R.id.btn4:
                Buka.buka(Zakat.this, Pertanian.class);
                break;
            case R.id.btn5:
                Buka.buka(Zakat.this, Pertanian.class);
                break;
            case R.id.btn6:
                Buka.buka(Zakat.this, Perdagangan.class);
                break;
            case R.id.btn7:
                Buka.buka(Zakat.this, Pertanian.class);
                break;

        }
    }

    private void konek() {
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
    }

}
