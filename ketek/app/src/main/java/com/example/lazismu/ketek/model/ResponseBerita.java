package com.example.lazismu.ketek.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseBerita implements Parcelable {

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("berita")
	private List<BeritaItem> berita;

	@SerializedName("sukses")
	private String sukses;

	@SerializedName("count")
	private int count;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setBerita(List<BeritaItem> berita){
		this.berita = berita;
	}

	public List<BeritaItem> getBerita(){
		return berita;
	}

	public void setSukses(String sukses){
		this.sukses = sukses;
	}

	public String getSukses(){
		return sukses;
	}

	public void setCount(int count){
		this.count = count;
	}

	public int getCount(){
		return count;
	}

	@Override
 	public String toString(){
		return 
			"ResponseBerita{" + 
			"pesan = '" + pesan + '\'' + 
			",berita = '" + berita + '\'' + 
			",sukses = '" + sukses + '\'' + 
			",count = '" + count + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.pesan);
		dest.writeTypedList(this.berita);
		dest.writeString(this.sukses);
		dest.writeInt(this.count);
	}

	public ResponseBerita() {
	}

	protected ResponseBerita(Parcel in) {
		this.pesan = in.readString();
		this.berita = in.createTypedArrayList(BeritaItem.CREATOR);
		this.sukses = in.readString();
		this.count = in.readInt();
	}

	public static final Parcelable.Creator<ResponseBerita> CREATOR = new Parcelable.Creator<ResponseBerita>() {
		@Override
		public ResponseBerita createFromParcel(Parcel source) {
			return new ResponseBerita(source);
		}

		@Override
		public ResponseBerita[] newArray(int size) {
			return new ResponseBerita[size];
		}
	};
}