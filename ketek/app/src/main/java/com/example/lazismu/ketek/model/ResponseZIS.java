package com.example.lazismu.ketek.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResponseZIS implements Parcelable {

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("zis")
	private List<ZisItem> zis;

	@SerializedName("response")
	private String response;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setZis(List<ZisItem> zis){
		this.zis = zis;
	}

	public List<ZisItem> getZis(){
		return zis;
	}

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	@Override
 	public String toString(){
		return 
			"ResponseZIS{" + 
			"pesan = '" + pesan + '\'' + 
			",zis = '" + zis + '\'' + 
			",response = '" + response + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.pesan);
		dest.writeList(this.zis);
		dest.writeString(this.response);
	}

	public ResponseZIS() {
	}

	protected ResponseZIS(Parcel in) {
		this.pesan = in.readString();
		this.zis = new ArrayList<ZisItem>();
		in.readList(this.zis, ZisItem.class.getClassLoader());
		this.response = in.readString();
	}

	public static final Parcelable.Creator<ResponseZIS> CREATOR = new Parcelable.Creator<ResponseZIS>() {
		@Override
		public ResponseZIS createFromParcel(Parcel source) {
			return new ResponseZIS(source);
		}

		@Override
		public ResponseZIS[] newArray(int size) {
			return new ResponseZIS[size];
		}
	};
}