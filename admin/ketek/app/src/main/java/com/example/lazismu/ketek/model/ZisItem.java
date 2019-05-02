package com.example.lazismu.ketek.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ZisItem implements Parcelable {

	@SerializedName("password")
	private String password;

	@SerializedName("nominal")
	private String nominal;

	@SerializedName("nama")
	private String nama;

	@SerializedName("kategori")
	private String kategori;

	@SerializedName("id_zis")
	private String idZis;

	@SerializedName("id_user")
	private String idUser;

	@SerializedName("tanggal")
	private String tanggal;

	@SerializedName("gambar")
	private String gambar;

	@SerializedName("email")
	private String email;

	@SerializedName("status")
	private String status;

	@SerializedName("alamat")
	private String alamat;

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setNominal(String nominal){
		this.nominal = nominal;
	}

	public String getNominal(){
		return nominal;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setKategori(String kategori){
		this.kategori = kategori;
	}

	public String getKategori(){
		return kategori;
	}

	public void setIdZis(String idZis){
		this.idZis = idZis;
	}

	public String getIdZis(){
		return idZis;
	}

	public void setIdUser(String idUser){
		this.idUser = idUser;
	}

	public String getIdUser(){
		return idUser;
	}

	public void setTanggal(String tanggal){
		this.tanggal = tanggal;
	}

	public String getTanggal(){
		return tanggal;
	}

	public void setGambar(String gambar){
		this.gambar = gambar;
	}

	public String getGambar(){
		return gambar;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	@Override
 	public String toString(){
		return 
			"ZisItem{" + 
			"password = '" + password + '\'' + 
			",nominal = '" + nominal + '\'' + 
			",nama = '" + nama + '\'' + 
			",kategori = '" + kategori + '\'' + 
			",id_zis = '" + idZis + '\'' + 
			",id_user = '" + idUser + '\'' + 
			",tanggal = '" + tanggal + '\'' + 
			",gambar = '" + gambar + '\'' + 
			",email = '" + email + '\'' + 
			",status = '" + status + '\'' + 
			",alamat = '" + alamat + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.password);
		dest.writeString(this.nominal);
		dest.writeString(this.nama);
		dest.writeString(this.kategori);
		dest.writeString(this.idZis);
		dest.writeString(this.idUser);
		dest.writeString(this.tanggal);
		dest.writeString(this.gambar);
		dest.writeString(this.email);
		dest.writeString(this.status);
		dest.writeString(this.alamat);
	}

	public ZisItem() {
	}

	protected ZisItem(Parcel in) {
		this.password = in.readString();
		this.nominal = in.readString();
		this.nama = in.readString();
		this.kategori = in.readString();
		this.idZis = in.readString();
		this.idUser = in.readString();
		this.tanggal = in.readString();
		this.gambar = in.readString();
		this.email = in.readString();
		this.status = in.readString();
		this.alamat = in.readString();
	}

	public static final Parcelable.Creator<ZisItem> CREATOR = new Parcelable.Creator<ZisItem>() {
		@Override
		public ZisItem createFromParcel(Parcel source) {
			return new ZisItem(source);
		}

		@Override
		public ZisItem[] newArray(int size) {
			return new ZisItem[size];
		}
	};
}