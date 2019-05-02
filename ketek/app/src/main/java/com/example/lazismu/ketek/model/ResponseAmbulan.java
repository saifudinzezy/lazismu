package com.example.lazismu.ketek.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseAmbulan{

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
}