package com.example.lazismu.ketek.model;

import com.google.gson.annotations.SerializedName;

public class UserItem{

	@SerializedName("nama")
	private String nama;

	@SerializedName("id_user")
	private String idUser;

	@SerializedName("email")
	private String email;

	@SerializedName("alamat")
	private String alamat;

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setIdUser(String idUser){
		this.idUser = idUser;
	}

	public String getIdUser(){
		return idUser;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	@Override
 	public String toString(){
		return 
			"UserItem{" + 
			"nama = '" + nama + '\'' + 
			",id_user = '" + idUser + '\'' + 
			",email = '" + email + '\'' + 
			",alamat = '" + alamat + '\'' + 
			"}";
		}
}