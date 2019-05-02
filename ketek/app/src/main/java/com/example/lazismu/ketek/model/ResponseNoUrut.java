package com.example.lazismu.ketek.model;

import com.google.gson.annotations.SerializedName;

public class ResponseNoUrut{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("no_urut")
	private String noUrut;

	@SerializedName("sukses")
	private String sukses;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setNoUrut(String noUrut){
		this.noUrut = noUrut;
	}

	public String getNoUrut(){
		return noUrut;
	}

	public void setSukses(String sukses){
		this.sukses = sukses;
	}

	public String getSukses(){
		return sukses;
	}

	@Override
 	public String toString(){
		return 
			"ResponseNoUrut{" + 
			"pesan = '" + pesan + '\'' + 
			",no_urut = '" + noUrut + '\'' + 
			",sukses = '" + sukses + '\'' + 
			"}";
		}
}