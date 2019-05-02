package com.example.lazismu.ketek.network;

import com.example.lazismu.ketek.menu.ResponseAdmin;
import com.example.lazismu.ketek.model.ResponseAmbulan;
import com.example.lazismu.ketek.model.ResponseAmbulan2;
import com.example.lazismu.ketek.model.ResponseBerita;
import com.example.lazismu.ketek.model.ResponseDataUSer;
import com.example.lazismu.ketek.model.ResponseInsert;
import com.example.lazismu.ketek.model.ResponseNoUrut;
import com.example.lazismu.ketek.model.ResponseUser;
import com.example.lazismu.ketek.model.ResponseZIS;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

    //read
    @FormUrlEncoded
    @POST("read/login_user.php")
    Call<ResponseUser> login(@Field("id") String id, @Field("password") String password);

    @FormUrlEncoded
    @POST("read/login_admin.php")
    Call<ResponseAdmin> loginAdmin(@Field("email") String email, @Field("password") String password);

    @GET("read/no_user.php")
    Call<ResponseNoUrut> getNoUrut();

    @GET("read/ambulan3.php")
    Call<ResponseAmbulan2> getDataAmbulan();

    @GET("read/berita.php")
    Call<ResponseBerita> getBerita();

    @GET("read/user.php")
    Call<ResponseDataUSer> getDataUser();

    //read zis
    @FormUrlEncoded
    @POST("read/zis.php")
    Call<ResponseZIS> getZIS(@Field("id") String id);

    @FormUrlEncoded
    @POST("read/zis.php")
    Call<ResponseZIS> getZIS(@Field("tahun") String tahun, @Field("zis") String zis);

    @GET("read/zis.php")
    Call<ResponseZIS> getZIS();

    //read ambulan
    @FormUrlEncoded
    @POST("read/ambulan.php")
    Call<ResponseAmbulan> getAmbulan(@Field("id") String id);

    @GET("read/ambulan.php")
    Call<ResponseAmbulan> getAmbulan();

    @FormUrlEncoded
    @POST("read/ambulan.php")
    Call<ResponseAmbulan> getAmbulan(@Field("bulan") String bulan, @Field("tahun") String tahun);

    //create
    @FormUrlEncoded
    @POST("create/user.php")
    Call<ResponseInsert> insertUser(@Field("id") String id, @Field("nama") String nama,
                                    @Field("alamat") String alamat, @Field("password") String password,
                                    @Field("email") String email);

    @FormUrlEncoded
    @POST("create/ambulan.php")
    Call<ResponseInsert> insertAmbulan(@Field("id") String id, @Field("nama") String nama,
                                    @Field("tgl_pinjam") String tgl_pinjam, @Field("tgl_kembali") String tgl_kembali,
                                    @Field("tujuan") String tujuan);

    @FormUrlEncoded
    @POST("create/ambulan2.php")
    Call<ResponseInsert> insertDataAmbulan(@Field("nama") String nama, @Field("no_pol") String no_pol,
                                    @Field("kondisi") String kondisi, @Field("status") String status);

    @Multipart
    @POST("create/zis.php")
    Call<ResponseInsert> insertZis(@Part("id") String id,
                                      @Part MultipartBody.Part image,
                                      @Part("nominal") String nominal,
                                      @Part("tanggal") String tanggal,
                                      @Part("kategori") String kategori);

    @Multipart
    @POST("create/berita.php")
    Call<ResponseInsert> insertBerta(@Part("judul") String judul,
                                      @Part MultipartBody.Part image,
                                      @Part("artikel") String artikel,
                                      @Part("tanggal") String tanggal);

    //update
    @FormUrlEncoded
    @POST("update/user.php")
    Call<ResponseInsert> updateUser(@Field("id") String id, @Field("nama") String nama,
                                    @Field("alamat") String alamat, @Field("password") String password,
                                    @Field("email") String email);

    //update
    @FormUrlEncoded
    @POST("update/ambulan2.php")
    Call<ResponseInsert> updateDataAmbulan(@Field("id") String id, @Field("nama") String nama,
                                    @Field("no_pol") String no_pol, @Field("kondisi") String kondisi,
                                    @Field("status") String status);

    @FormUrlEncoded
    @POST("update/ambulan2.php")
    Call<ResponseInsert> updateDataAmbulan(@Field("id") String id, @Field("status") String status);

    //update
    @FormUrlEncoded
    @POST("update/admin.php")
    Call<ResponseInsert> updateAdmin(@Field("id") String id, @Field("nama") String nama, @Field("password") String password,
                                    @Field("email") String email);

    @FormUrlEncoded
    @POST("update/ambulan.php")
    Call<ResponseInsert> updateAmbulan(@Field("id") int id, @Field("nama") String nama, @Field("tgl_pinjam") String tgl_pinjam,
                                       @Field("tgl_kembali") String tgl_kembali, @Field("tujuan") String tujuan,
                                       @Field("pesan") String pesan, @Field("status") String status,
                                       @Field("id_ambulan") String id_ambulan, @Field("sim") String sim);

    //update
    @Multipart
    @POST("update/zis.php")
    Call<ResponseInsert> updateZIS1(@Part("id") int id,
                                       @Part MultipartBody.Part image,
                                       @Part("nominal") String nominal,
                                       @Part("tanggal") String tanggal,
                                       @Part("hapus") String hapus,
                                       @Part("status") String status);

    @FormUrlEncoded
    @POST("update/zis.php")
    Call<ResponseInsert> updateZIS2(@Field("id") int id,
                                       @Field("nominal") String nominal,
                                       @Field("tanggal") String tanggal,
                                       @Field("status") String status);

    @FormUrlEncoded
    @POST("update/zis2.php")
    Call<ResponseInsert> updateZIS(@Field("id") String id, @Field("status") String status);

    //berita
    @Multipart
    @POST("update/berita.php")
    Call<ResponseInsert> updateBerita1(@Part("id") int id,
                                       @Part("judul") String judul,
                                       @Part MultipartBody.Part image,
                                       @Part("artikel") String artikel,
                                       @Part("hapus") String hapus,
                                       @Part("tanggal") String tanggal);

    @Multipart
    @POST("update/berita.php")
    Call<ResponseInsert> updateBerita2(@Part("id") int id,
                                       @Part("judul") String judul,
                                       @Part("artikel") String artikel,
                                       @Part("tanggal") String tanggal);

    //delete
    @FormUrlEncoded
    @POST("delete/delete.php")
    Call<ResponseInsert> delete(@Field("tabel") String tabel, @Field("cari") String cari, @Field("id_data") String idData);
}