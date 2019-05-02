package com.example.lazismu.ketek.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseDataUSer implements Parcelable {

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("response")
	private String response;

	@SerializedName("data_user")
	private List<DataUserItem> dataUser;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setDataUser(List<DataUserItem> dataUser){
		this.dataUser = dataUser;
	}

	public List<DataUserItem> getDataUser(){
		return dataUser;
	}

	@Override
 	public String toString(){
		return 
			"ResponseDataUSer{" + 
			"pesan = '" + pesan + '\'' + 
			",response = '" + response + '\'' + 
			",data_user = '" + dataUser + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.pesan);
		dest.writeString(this.response);
		dest.writeTypedList(this.dataUser);
	}

	public ResponseDataUSer() {
	}

	protected ResponseDataUSer(Parcel in) {
		this.pesan = in.readString();
		this.response = in.readString();
		this.dataUser = in.createTypedArrayList(DataUserItem.CREATOR);
	}

	public static final Parcelable.Creator<ResponseDataUSer> CREATOR = new Parcelable.Creator<ResponseDataUSer>() {
		@Override
		public ResponseDataUSer createFromParcel(Parcel source) {
			return new ResponseDataUSer(source);
		}

		@Override
		public ResponseDataUSer[] newArray(int size) {
			return new ResponseDataUSer[size];
		}
	};
}