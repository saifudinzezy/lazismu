package com.example.lazismu.ketek.network;

import com.example.lazismu.ketek.model.ResponseAmbulan;
import com.example.lazismu.ketek.model.ResponseAmbulan2;
import com.example.lazismu.ketek.model.ResponseBerita;
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

    @GET("read/no_user.php")
    Call<ResponseNoUrut> getNoUrut();

    @GET("read/berita.php")
    Call<ResponseBerita> getBerita();

    @GET("read/ambulan2.php")
    Call<ResponseAmbulan2> getDataAmbulan();

    @FormUrlEncoded
    @POST("read/zis.php")
    Call<ResponseZIS> getZIS(@Field("id") String id);

    @FormUrlEncoded
    @POST("read/ambulan.php")
    Call<ResponseAmbulan> getAmbulan(@Field("id") String id);

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
                                    @Field("tujuan") String tujuan, @Field("sim") String sim, @Field("id_ambulan") String id_ambulan);

    @Multipart
    @POST("create/zis.php")
    Call<ResponseInsert> insertZis(@Part("id") String id,
                                      @Part MultipartBody.Part image,
                                      @Part("nominal") String nominal,
                                      @Part("tanggal") String tanggal,
                                      @Part("kategori") String kategori);

    //update
    @FormUrlEncoded
    @POST("update/user.php")
    Call<ResponseInsert> updateUser(@Field("id") String id, @Field("nama") String nama,
                                    @Field("alamat") String alamat, @Field("password") String password,
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

    //delete
    @FormUrlEncoded
    @POST("delete/delete.php")
    Call<ResponseInsert> delete(@Field("tabel") String tabel, @Field("cari") String cari, @Field("id_data") String idData);
}