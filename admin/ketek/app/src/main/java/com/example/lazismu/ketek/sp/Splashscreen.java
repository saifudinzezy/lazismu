package com.example.lazismu.ketek.sp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.example.lazismu.ketek.MainMenu;
import com.example.lazismu.ketek.R;
import com.example.lazismu.ketek.menu.Menu;
import com.felipecsl.gifimageview.library.GifImageView;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class Splashscreen extends AppCompatActivity {

    private GifImageView gifImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splashscreen);

        setContentView(R.layout.activity_splashscreen);

        gifImageView = (GifImageView) findViewById(R.id.gifimageView);

        try {
            InputStream inputStream = getAssets().open("loading.gif");
            byte [] bytes = IOUtils.toByteArray(inputStream);
            gifImageView.setBytes(bytes);
            gifImageView.startAnimation();

        } catch (IOException e) {
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), Menu.class));
                finish();
            }
        }, 4000L); //3000 L = 3 detik
    }
}
