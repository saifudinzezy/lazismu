package com.example.lazismu.ketek.menu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.lazismu.ketek.R;
import com.example.lazismu.ketek.helper.Buka;
import com.example.lazismu.ketek.login.Login;
import com.example.lazismu.ketek.menu.add.Berita;
import com.example.lazismu.ketek.menu.fragment.DaftarAmbulan;
import com.example.lazismu.ketek.shared.SharedLogin;
import com.example.lazismu.ketek.sp.Splashscreen;
import com.imangazaliev.circlemenu.CircleMenu;
import com.imangazaliev.circlemenu.CircleMenuButton;

import static com.example.lazismu.ketek.shared.SharedLogin.SP_EMAIL;
import static com.example.lazismu.ketek.shared.SharedLogin.SP_PASSWORD;

public class Menu extends AppCompatActivity {
    SharedLogin sharedLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        sharedLogin = new SharedLogin(Menu.this);

        //cek apakah user sudah login
        if (sharedLogin.getSharedSudahLogin()) {
            if (!sharedLogin.getSharedSudahLogin2()) {
                //cek login kedua
                startActivity(new Intent(Menu.this, Login.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        CircleMenu circleMenu = (CircleMenu) findViewById(R.id.circleMenu);
        circleMenu.setOnItemClickListener(new CircleMenu.OnItemClickListener() {
            @Override
            public void onItemClick(CircleMenuButton menuButton) {
                switch (menuButton.getId()) {
                    case R.id.favorite:
                        Buka.buka(Menu.this, Berita.class);
                        break;
                    case R.id.search:
                        Buka.buka(Menu.this, Laporan.class);
                        break;
                    case R.id.alert:
                        Buka.buka(Menu.this, Akun.class);
                        break;
                    case R.id.ambulan:
                        Buka.buka(Menu.this, DaftarAmbulan.class);
                        break;
                }
            }
        });

        circleMenu.setStateUpdateListener(new CircleMenu.OnStateUpdateListener() {
            @Override
            public void onMenuExpanded() {
                Log.d("CircleMenuStatus", "Expanded");
            }

            @Override
            public void onMenuCollapsed() {
                Log.d("CircleMenuStatus", "Collapsed");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }
}