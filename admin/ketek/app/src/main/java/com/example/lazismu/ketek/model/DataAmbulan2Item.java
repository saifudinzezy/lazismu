package com.example.lazismu.ketek.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DataAmbulan2Item implements Parcelable {

	@SerializedName("no_pol")
	private String noPol;

	@SerializedName("id_ambulan")
	private String idAmbulan;

	@SerializedName("nama")
	private String nama;

	@SerializedName("kondisi")
	private String kondisi;

	@SerializedName("status")
	private String status;

	public void setNoPol(String noPol){
		this.noPol = noPol;
	}

	public String getNoPol(){
		return noPol;
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

	public void setKondisi(String kondisi){
		this.kondisi = kondisi;
	}

	public String getKondisi(){
		return kondisi;
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
			"DataAmbulan2Item{" + 
			"no_pol = '" + noPol + '\'' + 
			",id_ambulan = '" + idAmbulan + '\'' + 
			",nama = '" + nama + '\'' + 
			",kondisi = '" + kondisi + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.noPol);
		dest.writeString(this.idAmbulan);
		dest.writeString(this.nama);
		dest.writeString(this.kondisi);
		dest.writeString(this.status);
	}

	public DataAmbulan2Item() {
	}

	protected DataAmbulan2Item(Parcel in) {
		this.noPol = in.readString();
		this.idAmbulan = in.readString();
		this.nama = in.readString();
		this.kondisi = in.readString();
		this.status = in.readString();
	}

	public static final Parcelable.Creator<DataAmbulan2Item> CREATOR = new Parcelable.Creator<DataAmbulan2Item>() {
		@Override
		public DataAmbulan2Item createFromParcel(Parcel source) {
			return new DataAmbulan2Item(source);
		}

		@Override
		public DataAmbulan2Item[] newArray(int size) {
			return new DataAmbulan2Item[size];
		}
	};
}