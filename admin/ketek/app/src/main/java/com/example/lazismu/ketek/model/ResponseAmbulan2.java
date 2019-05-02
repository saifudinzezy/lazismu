package com.example.lazismu.ketek.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseAmbulan2 implements Parcelable {

	@SerializedName("data_ambulan2")
	private List<DataAmbulan2Item> dataAmbulan2;

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("response")
	private String response;

	public void setDataAmbulan2(List<DataAmbulan2Item> dataAmbulan2){
		this.dataAmbulan2 = dataAmbulan2;
	}

	public List<DataAmbulan2Item> getDataAmbulan2(){
		return dataAmbulan2;
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
			"ResponseAmbulan2{" + 
			"data_ambulan2 = '" + dataAmbulan2 + '\'' + 
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
		dest.writeTypedList(this.dataAmbulan2);
		dest.writeString(this.pesan);
		dest.writeString(this.response);
	}

	public ResponseAmbulan2() {
	}

	protected ResponseAmbulan2(Parcel in) {
		this.dataAmbulan2 = in.createTypedArrayList(DataAmbulan2Item.CREATOR);
		this.pesan = in.readString();
		this.response = in.readString();
	}

	public static final Parcelable.Creator<ResponseAmbulan2> CREATOR = new Parcelable.Creator<ResponseAmbulan2>() {
		@Override
		public ResponseAmbulan2 createFromParcel(Parcel source) {
			return new ResponseAmbulan2(source);
		}

		@Override
		public ResponseAmbulan2[] newArray(int size) {
			return new ResponseAmbulan2[size];
		}
	};
}