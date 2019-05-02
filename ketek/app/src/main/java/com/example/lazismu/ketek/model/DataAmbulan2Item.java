package com.example.lazismu.ketek.model;

import com.google.gson.annotations.SerializedName;

public class DataAmbulan2Item{

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

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

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
			",kondisi = '" + kondisi + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}