package com.example.lazismu.ketek.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DataUserItem implements Parcelable {

	@SerializedName("nama")
	private String nama;

	@SerializedName("sandi")
	private String sandi;

	@SerializedName("id")
	private String id;

	@SerializedName("id_user")
	private String idUser;

	@SerializedName("email")
	private String email;

	@SerializedName("alamat")
	private String alamat;

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setSandi(String sandi){
		this.sandi = sandi;
	}

	public String getSandi(){
		return sandi;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setIdUser(String idUser){
		this.idUser = idUser;
	}

	public String getIdUser(){
		return idUser;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
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
			"DataUserItem{" + 
			"nama = '" + nama + '\'' + 
			",sandi = '" + sandi + '\'' + 
			",id = '" + id + '\'' + 
			",id_user = '" + idUser + '\'' + 
			",email = '" + email + '\'' + 
			",alamat = '" + alamat + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.nama);
		dest.writeString(this.sandi);
		dest.writeString(this.id);
		dest.writeString(this.idUser);
		dest.writeString(this.email);
		dest.writeString(this.alamat);
	}

	public DataUserItem() {
	}

	protected DataUserItem(Parcel in) {
		this.nama = in.readString();
		this.sandi = in.readString();
		this.id = in.readString();
		this.idUser = in.readString();
		this.email = in.readString();
		this.alamat = in.readString();
	}

	public static final Parcelable.Creator<DataUserItem> CREATOR = new Parcelable.Creator<DataUserItem>() {
		@Override
		public DataUserItem createFromParcel(Parcel source) {
			return new DataUserItem(source);
		}

		@Override
		public DataUserItem[] newArray(int size) {
			return new DataUserItem[size];
		}
	};
}