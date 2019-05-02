package com.example.lazismu.ketek.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AmbulanItem implements Parcelable {

	@SerializedName("kondisi")
	private String kondisi;

	@SerializedName("id_ambulan2")
	private String idAmbulan2;

	@SerializedName("status_ambulan")
	private String statusAmbulan;

	@SerializedName("id_user")
	private String idUser;

	@SerializedName("tujuan")
	private String tujuan;

	@SerializedName("alamat")
	private String alamat;

	@SerializedName("no_pol")
	private String noPol;

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("id_ambulan")
	private String idAmbulan;

	@SerializedName("nama")
	private String nama;

	@SerializedName("sim")
	private String sim;

	@SerializedName("tgl_kembali")
	private String tglKembali;

	@SerializedName("nama_driver")
	private String namaDriver;

	@SerializedName("nama_ambulan")
	private String namaAmbulan;

	@SerializedName("email")
	private String email;

	@SerializedName("tgl_pinjam")
	private String tglPinjam;

	@SerializedName("status")
	private String status;

	public void setKondisi(String kondisi){
		this.kondisi = kondisi;
	}

	public String getKondisi(){
		return kondisi;
	}

	public void setIdAmbulan2(String idAmbulan2){
		this.idAmbulan2 = idAmbulan2;
	}

	public String getIdAmbulan2(){
		return idAmbulan2;
	}

	public void setStatusAmbulan(String statusAmbulan){
		this.statusAmbulan = statusAmbulan;
	}

	public String getStatusAmbulan(){
		return statusAmbulan;
	}

	public void setIdUser(String idUser){
		this.idUser = idUser;
	}

	public String getIdUser(){
		return idUser;
	}

	public void setTujuan(String tujuan){
		this.tujuan = tujuan;
	}

	public String getTujuan(){
		return tujuan;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	public void setNoPol(String noPol){
		this.noPol = noPol;
	}

	public String getNoPol(){
		return noPol;
	}

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setIdAmbulan(String idAmbulan){
		this.idAmbulan = idAmbulan;
	}

	public String getIdAmbulan(){
		return idAmbulan;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setSim(String sim){
		this.sim = sim;
	}

	public String getSim(){
		return sim;
	}

	public void setTglKembali(String tglKembali){
		this.tglKembali = tglKembali;
	}

	public String getTglKembali(){
		return tglKembali;
	}

	public void setNamaDriver(String namaDriver){
		this.namaDriver = namaDriver;
	}

	public String getNamaDriver(){
		return namaDriver;
	}

	public void setNamaAmbulan(String namaAmbulan){
		this.namaAmbulan = namaAmbulan;
	}

	public String getNamaAmbulan(){
		return namaAmbulan;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setTglPinjam(String tglPinjam){
		this.tglPinjam = tglPinjam;
	}

	public String getTglPinjam(){
		return tglPinjam;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"AmbulanItem{" + 
			"kondisi = '" + kondisi + '\'' + 
			",id_ambulan2 = '" + idAmbulan2 + '\'' + 
			",status_ambulan = '" + statusAmbulan + '\'' + 
			",id_user = '" + idUser + '\'' + 
			",tujuan = '" + tujuan + '\'' + 
			",alamat = '" + alamat + '\'' + 
			",no_pol = '" + noPol + '\'' + 
			",pesan = '" + pesan + '\'' + 
			",id_ambulan = '" + idAmbulan + '\'' + 
			",nama = '" + nama + '\'' + 
			",sim = '" + sim + '\'' + 
			",tgl_kembali = '" + tglKembali + '\'' + 
			",nama_driver = '" + namaDriver + '\'' + 
			",nama_ambulan = '" + namaAmbulan + '\'' + 
			",email = '" + email + '\'' + 
			",tgl_pinjam = '" + tglPinjam + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.kondisi);
		dest.writeString(this.idAmbulan2);
		dest.writeString(this.statusAmbulan);
		dest.writeString(this.idUser);
		dest.writeString(this.tujuan);
		dest.writeString(this.alamat);
		dest.writeString(this.noPol);
		dest.writeString(this.pesan);
		dest.writeString(this.idAmbulan);
		dest.writeString(this.nama);
		dest.writeString(this.sim);
		dest.writeString(this.tglKembali);
		dest.writeString(this.namaDriver);
		dest.writeString(this.namaAmbulan);
		dest.writeString(this.email);
		dest.writeString(this.tglPinjam);
		dest.writeString(this.status);
	}

	public AmbulanItem() {
	}

	protected AmbulanItem(Parcel in) {
		this.kondisi = in.readString();
		this.idAmbulan2 = in.readString();
		this.statusAmbulan = in.readString();
		this.idUser = in.readString();
		this.tujuan = in.readString();
		this.alamat = in.readString();
		this.noPol = in.readString();
		this.pesan = in.readString();
		this.idAmbulan = in.readString();
		this.nama = in.readString();
		this.sim = in.readString();
		this.tglKembali = in.readString();
		this.namaDriver = in.readString();
		this.namaAmbulan = in.readString();
		this.email = in.readString();
		this.tglPinjam = in.readString();
		this.status = in.readString();
	}

	public static final Parcelable.Creator<AmbulanItem> CREATOR = new Parcelable.Creator<AmbulanItem>() {
		@Override
		public AmbulanItem createFromParcel(Parcel source) {
			return new AmbulanItem(source);
		}

		@Override
		public AmbulanItem[] newArray(int size) {
			return new AmbulanItem[size];
		}
	};
}