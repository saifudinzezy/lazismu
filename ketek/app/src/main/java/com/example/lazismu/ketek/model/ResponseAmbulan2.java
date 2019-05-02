package com.example.lazismu.ketek.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseAmbulan2{

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
}