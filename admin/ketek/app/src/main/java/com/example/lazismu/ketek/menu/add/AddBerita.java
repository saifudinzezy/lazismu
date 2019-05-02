package com.example.lazismu.ketek.menu.add;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lazismu.ketek.R;
import com.example.lazismu.ketek.helper.CRUDBerita;
import com.example.lazismu.ketek.helper.RealPathUtil;
import com.example.lazismu.ketek.model.BeritaItem;
import com.example.lazismu.ketek.model.ResponseInsert;
import com.example.lazismu.ketek.network.ApiService;
import com.example.lazismu.ketek.network.RetroClient;

import net.dankito.richtexteditor.android.RichTextEditor;
import net.dankito.richtexteditor.android.toolbar.GroupedCommandsEditorToolbar;
import net.dankito.utils.android.permissions.IPermissionsService;
import net.dankito.utils.android.permissions.PermissionsService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.lazismu.ketek.helper.Constan.KEY_DATA;
import static com.example.lazismu.ketek.helper.Constan.URL_IMAGE;
import static com.example.lazismu.ketek.helper.ConvertDate.tglHariIni;
import static com.example.lazismu.ketek.helper.FunctionError.cekEditText;
import static com.example.lazismu.ketek.helper.FunctionError.getTextEditText;
import static com.example.lazismu.ketek.helper.FunctionError.kosongkan;
import static com.example.lazismu.ketek.helper.FunctionError.setErrorEditText;
import static com.example.lazismu.ketek.helper.RealPathUtil.setTextViews;

public class AddBerita extends AppCompatActivity implements CRUDBerita.OnReset{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_judul)
    EditText edtJudul;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.txt_insert)
    TextView txtInsert;
    @BindView(R.id.txt_place)
    TextView txtPlace;
    @BindView(R.id.txt_view)
    TextView txtView;
    @BindView(R.id.editor)
    RichTextEditor editor;
    @BindView(R.id.editorToolbar)
    GroupedCommandsEditorToolbar editorToolbar;
    //var img
    int PICK_IMAGE_REQUEST = 1;
    int bitmap_size = 60; // range 1 - 100
    Bitmap bitmap, decoded;
    String part_image;
    ProgressDialog progressDialog;
    BeritaItem data;
    IPermissionsService permissionsService;
    CRUDBerita crudBerita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_berita);
        ButterKnife.bind(this);
        permissionsService = new PermissionsService(this);
        crudBerita = new CRUDBerita(this, this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Tunggu Sebentar...");

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tambah Berita");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editorToolbar.setEditor(editor);

        editor.setEditorFontSize(20);
        editor.setPadding((4 * (int) getResources().getDisplayMetrics().density));
        editor.setHtml(" ");

        data = getIntent().getParcelableExtra(KEY_DATA);
        if (data != null) {
            //Toast.makeText(this, data.getIdAdmin(), Toast.LENGTH_SHORT).show();
            Glide.with(this)
                    .load(URL_IMAGE + data.getGambar())
                    .into(img);
            edtJudul.setText(data.getJudul());
            editor.setHtml(data.getArtikel());
            txtPlace.setText(data.getGambar());
            getSupportActionBar().setTitle("Ubah Data");
        } else {
            getSupportActionBar().setTitle("Tambah Data");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_berita, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add) {
            if (data == null) {
                if (cekEditText(edtJudul)) {
                    setErrorEditText(edtJudul, "Judul Kosong");
                }else {
                    crudBerita.simpan(part_image, getTextEditText(edtJudul), editor.getCachedHtml().toString(),
                            tglHariIni(), progressDialog);
                }
            } else {
                //jika place sama dengan nama imagenya
                if (data.getGambar() == txtPlace.getText().toString()) {
                    crudBerita.updateWithField(data.getIdBerita(), getTextEditText(edtJudul), editor.getCachedHtml(),
                            data.getTanggal());
                } else {
                    crudBerita.updateWithImage(part_image, data.getIdBerita(), getTextEditText(edtJudul),
                            editor.getCachedHtml().toString(), data.getTanggal(), data.getGambar());
                }
                finish();
            }
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            String realPath;
            // SDK < API11
            if (Build.VERSION.SDK_INT < 11)
                realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, data.getData());

                // SDK >= 11 && SDK < 19
            else if (Build.VERSION.SDK_INT < 19)
                realPath = RealPathUtil.getRealPathFromURI_API11to18(this, data.getData());

                // SDK > 19 (Android 4.4)
            else
                realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());


            setTextViews(AddBerita.this, Build.VERSION.SDK_INT, data.getData().getPath(), realPath, txtPlace, img);
            part_image = realPath;
        }
    }

    @OnClick({R.id.txt_insert, R.id.txt_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_insert:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                break;
            case R.id.txt_view:
                if (img.getVisibility() == View.GONE) {
                    img.setVisibility(View.VISIBLE);
                    Drawable img = getResources().getDrawable(R.drawable.ic_visibility_grey_900_24dp);
                    //sesuaikan image sesuai isian bisa di start/awal, top/atas,end/akhir, bottom/bawah
                    txtView.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, img, null);
                } else {
                    img.setVisibility(View.GONE);
                    Drawable img = getResources().getDrawable(R.drawable.ic_visibility_off_black_24dp
                    );
                    //sesuaikan image sesuai isian bisa di start/awal, top/atas,end/akhir, bottom/bawah
                    txtView.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, img, null);
                }
                break;
        }
    }

    @Override
    public void onReset() {
        /*kosongkan(edtJudul);
        editor.setHtml(" ");*/
    }
}
