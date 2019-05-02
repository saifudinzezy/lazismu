package com.example.lazismu.ketek.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AmbulanItem implements Parcelable {

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("id_ambulan")
	private String idAmbulan;

	@SerializedName("nama")
	private String nama;

	@SerializedName("tgl_kembali")
	private String tglKembali;

	@SerializedName("nama_driver")
	private String namaDriver;

	@SerializedName("id_user")
	private String idUser;

	@SerializedName("tujuan")
	private String tujuan;

	@SerializedName("email")
	private String email;

	@SerializedName("tgl_pinjam")
	private String tglPinjam;

	@SerializedName("status")
	private String status;

	@SerializedName("alamat")
	private String alamat;

	@SerializedName("id_ambulan2")
	private String id_ambulan2;

	@SerializedName("sim")
	private String sim;

	@SerializedName("nama_ambulan")
	private String nama_ambulan;

	@SerializedName("no_pol")
	private String no_pol;

	@SerializedName("kondisi")
	private String kondisi;

	@SerializedName("status_ambulan")
	private String status_ambulan;

	public String getId_ambulan2() {
		return id_ambulan2;
	}

	public void setId_ambulan2(String id_ambulan2) {
		this.id_ambulan2 = id_ambulan2;
	}

	public String getSim() {
		return sim;
	}

	public void setSim(String sim) {
		this.sim = sim;
	}

	public String getNama_ambulan() {
		return nama_ambulan;
	}

	public void setNama_ambulan(String nama_ambulan) {
		this.nama_ambulan = nama_ambulan;
	}

	public String getNo_pol() {
		return no_pol;
	}

	public void setNo_pol(String no_pol) {
		this.no_pol = no_pol;
	}

	public String getKondisi() {
		return kondisi;
	}

	public void setKondisi(String kondisi) {
		this.kondisi = kondisi;
	}

	public String getStatus_ambulan() {
		return status_ambulan;
	}

	public void setStatus_ambulan(String status_ambulan) {
		this.status_ambulan = status_ambulan;
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

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	@Override
 	public String toString(){
		return 
			"AmbulanItem{" + 
			"pesan = '" + pesan + '\'' + 
			",id_ambulan = '" + idAmbulan + '\'' + 
			",nama = '" + nama + '\'' + 
			",tgl_kembali = '" + tglKembali + '\'' + 
			",nama_driver = '" + namaDriver + '\'' + 
			",id_user = '" + idUser + '\'' + 
			",tujuan = '" + tujuan + '\'' + 
			",email = '" + email + '\'' + 
			",tgl_pinjam = '" + tglPinjam + '\'' + 
			",status = '" + status + '\'' + 
			",alamat = '" + alamat + '\'' + 
			"}";
		}

	public AmbulanItem() {
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.pesan);
		dest.writeString(this.idAmbulan);
		dest.writeString(this.nama);
		dest.writeString(this.tglKembali);
		dest.writeString(this.namaDriver);
		dest.writeString(this.idUser);
		dest.writeString(this.tujuan);
		dest.writeString(this.email);
		dest.writeString(this.tglPinjam);
		dest.writeString(this.status);
		dest.writeString(this.alamat);
		dest.writeString(this.id_ambulan2);
		dest.writeString(this.sim);
		dest.writeString(this.nama_ambulan);
		dest.writeString(this.no_pol);
		dest.writeString(this.kondisi);
		dest.writeString(this.status_ambulan);
	}

	protected AmbulanItem(Parcel in) {
		this.pesan = in.readString();
		this.idAmbulan = in.readString();
		this.nama = in.readString();
		this.tglKembali = in.readString();
		this.namaDriver = in.readString();
		this.idUser = in.readString();
		this.tujuan = in.readString();
		this.email = in.readString();
		this.tglPinjam = in.readString();
		this.status = in.readString();
		this.alamat = in.readString();
		this.id_ambulan2 = in.readString();
		this.sim = in.readString();
		this.nama_ambulan = in.readString();
		this.no_pol = in.readString();
		this.kondisi = in.readString();
		this.status_ambulan = in.readString();
	}

	public static final Creator<AmbulanItem> CREATOR = new Creator<AmbulanItem>() {
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