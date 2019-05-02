package com.example.lazismu.ketek.menu.zakat.kalkuza;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.lazismu.ketek.R;
import com.example.lazismu.ketek.menu.zakat.item.Perdagangan;
import com.example.lazismu.ketek.menu.zakat.item.Pertanian;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.lazismu.ketek.helper.FunctionError.cekEditText;
import static com.example.lazismu.ketek.helper.FunctionError.setErrorEditText;

public class KalkuzaPerdagangan extends AppCompatActivity {
    @BindView(R.id.edpenghasilan)
    EditText edpenghasilan;
    @BindView(R.id.butkalku)
    Button butkalku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalkuza_perdagangan);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.butkalku)
    public void onViewClicked() {
        if (cekEditText(edpenghasilan)) {
            setErrorEditText(edpenghasilan, "Nilai Kosong");
        }else {
            String isigaji = edpenghasilan.getText().toString();
            double a = Double.parseDouble(isigaji);
            double fix = Zakat(a);
            Intent intent = new Intent(KalkuzaPerdagangan.this, Perdagangan.class);
            intent.putExtra("jumlah", fix);
            startActivity(intent);
            finish();
        }
    }

    public double Zakat(double a){
        return a * 2.5;
    }
}
