package com.example.lazismu.ketek.menu;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.lazismu.ketek.R;
import com.example.lazismu.ketek.menu.item.Umkm;
import com.example.lazismu.ketek.helper.Buka;
import com.example.lazismu.ketek.menu.item.Beasiswa2;
import com.example.lazismu.ketek.menu.item.Bedahrumah;

public class Infaq extends AppCompatActivity implements View.OnClickListener {

    private ImageView btnbeasiswa, btnbedahrumah, btnumkm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infaq);

        konek();
        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            btnbeasiswa.setEnabled(false);
            btnbedahrumah.setEnabled(false);
            btnumkm.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }*/
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                btnbeasiswa.setEnabled(true);
                btnbedahrumah.setEnabled(true);
                btnumkm.setEnabled(true);
            }
        }
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView1:
                //Toast.makeText(this, "klik", Toast.LENGTH_SHORT).show();
                Buka.buka(Infaq.this, Beasiswa2.class);
                break;
            case R.id.imageView2:
                Buka.buka(Infaq.this, Bedahrumah.class);
                break;
            case R.id.imageView3:
                Buka.buka(Infaq.this, Umkm.class);
                break;
        }
    }


    private void konek() {
        btnbeasiswa = findViewById(R.id.imageView1);
        btnbedahrumah = findViewById(R.id.imageView2);
        btnumkm = findViewById(R.id.imageView3);

        btnbeasiswa.setOnClickListener(this);
        btnbedahrumah.setOnClickListener(this);
        btnumkm.setOnClickListener(this);

    }

}
