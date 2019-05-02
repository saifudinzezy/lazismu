package com.example.lazismu.ketek;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.lazismu.ketek.helper.Buka;
import com.example.lazismu.ketek.login.Login;
import com.example.lazismu.ketek.menu.Akun;
import com.example.lazismu.ketek.menu.Ambulan;
import com.example.lazismu.ketek.menu.Berita;
import com.example.lazismu.ketek.menu.Infaq;
import com.example.lazismu.ketek.menu.Laporan;
import com.example.lazismu.ketek.menu.zakat.Zakat;
import com.example.lazismu.ketek.model.ResponseBerita;
import com.example.lazismu.ketek.network.ApiService;
import com.example.lazismu.ketek.network.RetroClient;
import com.example.lazismu.ketek.shared.SharedLogin;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.lazismu.ketek.helper.Constan.URL_IMAGE;

public class MainMenu extends AppCompatActivity implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener, View.OnClickListener {

    SliderLayout sliderLayout;
    HashMap<String, String> HashMapForURL;
    HashMap<String, Integer> HashMapForLocalRes;
    private ImageView btnZakat, btnAmbulance, btninfaq, btnlaporan, btnakun, btnInfo;
    SharedLogin sharedLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        sharedLogin = new SharedLogin(this);

        //cek apakah user sudah login
        if (sharedLogin.getSharedSudahLogin()) {
            if (!sharedLogin.getSharedSudahLogin2()) {
                //cek login kedua
                startActivity(new Intent(MainMenu.this, Login.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        }

        konek();

        sliderLayout = (SliderLayout) findViewById(R.id.slider);

        //Call this method if you want to add images from URL .
        //AddImagesUrlOnline();
        getBerita();

        //Call this method to add images from local drawable folder .
        //AddImageUrlFormLocalRes();

        //Call this method to stop automatic sliding.
        //sliderLayout.stopAutoCycle();

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);

        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

        sliderLayout.setCustomAnimation(new DescriptionAnimation());

        sliderLayout.setDuration(3000);

        sliderLayout.addOnPageChangeListener(MainMenu.this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }

    @Override
    protected void onStop() {

        sliderLayout.stopAutoCycle();

        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        // logika click disini
        //Toast.makeText(this, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
        Buka.buka(MainMenu.this, Berita.class);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {

        Log.d("Slider Demo", "Page Changed: " + position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void AddImagesUrlOnline() {

        HashMapForURL = new HashMap<String, String>();

        HashMapForURL.put("CupCake", "http://d1nnx3nhddxmeh.cloudfront.net/wp-content/uploads/2017/01/10154602/Tea-Leoni-.png");
        HashMapForURL.put("Donut", "http://androidblog.esy.es/images/donut-2.png");
        HashMapForURL.put("Eclair", "http://androidblog.esy.es/images/eclair-3.png");
        HashMapForURL.put("Froyo", "http://androidblog.esy.es/images/froyo-4.png");
        HashMapForURL.put("GingerBread", "http://androidblog.esy.es/images/gingerbread-5.png");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView5:
                //TODO BUKA HALAMAN
                Buka.buka(MainMenu.this, Zakat.class);
                break;
            case R.id.imageView4:
                Buka.buka(MainMenu.this, Ambulan.class);
                break;
            case R.id.imageView6:
                Buka.buka(MainMenu.this, Infaq.class);
                break;
            case R.id.imageView7:
                Buka.buka(MainMenu.this, Laporan.class);
                break;
            case R.id.imageView9:
                Buka.buka(MainMenu.this, Akun.class);
                break;
            case R.id.imageView2:
                Buka.buka(MainMenu.this, Tentangkami.class);
                break;
        }
    }

    private void konek() {
        btnZakat = findViewById(R.id.imageView5);
        btnAmbulance = findViewById(R.id.imageView4);
        btninfaq = findViewById(R.id.imageView6);
        btnlaporan = findViewById(R.id.imageView7);
        btnakun = findViewById(R.id.imageView9);
        btnInfo = findViewById(R.id.imageView2);

        btnZakat.setOnClickListener(this);
        btnAmbulance.setOnClickListener(this);
        btninfaq.setOnClickListener(this);
        btnlaporan.setOnClickListener(this);
        btnakun.setOnClickListener(this);
        btnInfo.setOnClickListener(this);
    }

    private void getBerita() {
        ApiService apiService = RetroClient.getApiService();
        Call<ResponseBerita> call = apiService.getBerita();
        call.enqueue(new Callback<ResponseBerita>() {
            @Override
            public void onResponse(Call<ResponseBerita> call, Response<ResponseBerita> response) {
                //Log.e("Tag", "Hasil List :" + new Gson().toJson(hasilPesan));
                if (response.body().getSukses().equalsIgnoreCase("true")) {
                    try {
                        HashMapForURL = new HashMap<String, String>();

                        for (int i = 0; i < response.body().getBerita().size(); i++) {
                            HashMapForURL.put(response.body().getBerita().get(i).getJudul(),
                                    URL_IMAGE + response.body().getBerita().get(i).getGambar());
                        }

                        for (String name : HashMapForURL.keySet()) {

                            TextSliderView textSliderView = new TextSliderView(MainMenu.this);

                            textSliderView
                                    .description(name)
                                    .image(HashMapForURL.get(name))
                                    .setScaleType(BaseSliderView.ScaleType.Fit)
                                    .setOnSliderClickListener(MainMenu.this);

                            textSliderView.bundle(new Bundle());
                            textSliderView.getBundle()
                                    .putString("extra", name);

                            sliderLayout.addSlider(textSliderView);
                        }
                    } catch (Exception e) {
                        Toast.makeText(MainMenu.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("Tag", "Gagal req data ");
                }
            }

            @Override
            public void onFailure(Call<ResponseBerita> call, Throwable t) {
                Toast.makeText(MainMenu.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
