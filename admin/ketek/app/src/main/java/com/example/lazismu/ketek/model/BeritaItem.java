package com.example.lazismu.ketek.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class BeritaItem implements Parcelable {

	@SerializedName("artikel")
	private String artikel;

	@SerializedName("tanggal")
	private String tanggal;

	@SerializedName("judul")
	private String judul;

	@SerializedName("id_berita")
	private String idBerita;

	@SerializedName("gambar")
	private String gambar;

	public void setArtikel(String artikel){
		this.artikel = artikel;
	}

	public String getArtikel(){
		return artikel;
	}

	public void setTanggal(String tanggal){
		this.tanggal = tanggal;
	}

	public String getTanggal(){
		return tanggal;
	}

	public void setJudul(String judul){
		this.judul = judul;
	}

	public String getJudul(){
		return judul;
	}

	public void setIdBerita(String idBerita){
		this.idBerita = idBerita;
	}

	public String getIdBerita(){
		return idBerita;
	}

	public void setGambar(String gambar){
		this.gambar = gambar;
	}

	public String getGambar(){
		return gambar;
	}

	@Override
 	public String toString(){
		return 
			"BeritaItem{" + 
			"artikel = '" + artikel + '\'' + 
			",tanggal = '" + tanggal + '\'' + 
			",judul = '" + judul + '\'' + 
			",id_berita = '" + idBerita + '\'' + 
			",gambar = '" + gambar + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.artikel);
		dest.writeString(this.tanggal);
		dest.writeString(this.judul);
		dest.writeString(this.idBerita);
		dest.writeString(this.gambar);
	}

	public BeritaItem() {
	}

	protected BeritaItem(Parcel in) {
		this.artikel = in.readString();
		this.tanggal = in.readString();
		this.judul = in.readString();
		this.idBerita = in.readString();
		this.gambar = in.readString();
	}

	public static final Parcelable.Creator<BeritaItem> CREATOR = new Parcelable.Creator<BeritaItem>() {
		@Override
		public BeritaItem createFromParcel(Parcel source) {
			return new BeritaItem(source);
		}

		@Override
		public BeritaItem[] newArray(int size) {
			return new BeritaItem[size];
		}
	};
}