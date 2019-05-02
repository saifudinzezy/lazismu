package com.example.lazismu.ketek.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResponseAmbulan implements Parcelable {

	@SerializedName("ambulan")
	private List<AmbulanItem> ambulan;

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("response")
	private String response;

	public void setAmbulan(List<AmbulanItem> ambulan){
		this.ambulan = ambulan;
	}

	public List<AmbulanItem> getAmbulan(){
		return ambulan;
	}

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

	@Override
 	public String toString(){
		return 
			"ResponseAmbulan{" + 
			"ambulan = '" + ambulan + '\'' + 
			",pesan = '" + pesan + '\'' + 
			",response = '" + response + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeList(this.ambulan);
		dest.writeString(this.pesan);
		dest.writeString(this.response);
	}

	public ResponseAmbulan() {
	}

	protected ResponseAmbulan(Parcel in) {
		this.ambulan = new ArrayList<AmbulanItem>();
		in.readList(this.ambulan, AmbulanItem.class.getClassLoader());
		this.pesan = in.readString();
		this.response = in.readString();
	}

	public static final Parcelable.Creator<ResponseAmbulan> CREATOR = new Parcelable.Creator<ResponseAmbulan>() {
		@Override
		public ResponseAmbulan createFromParcel(Parcel source) {
			return new ResponseAmbulan(source);
		}

		@Override
		public ResponseAmbulan[] newArray(int size) {
			return new ResponseAmbulan[size];
		}
	};
}