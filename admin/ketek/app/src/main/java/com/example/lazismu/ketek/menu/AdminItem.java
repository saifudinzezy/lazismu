package com.example.lazismu.ketek.menu;

import com.google.gson.annotations.SerializedName;

public class AdminItem{

	@SerializedName("nama")
	private String nama;

	@SerializedName("id_admin")
	private String idAdmin;

	@SerializedName("sandi")
	private String sandi;

	@SerializedName("email")
	private String email;

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setIdAdmin(String idAdmin){
		this.idAdmin = idAdmin;
	}

	public String getIdAdmin(){
		return idAdmin;
	}

	public void setSandi(String sandi){
		this.sandi = sandi;
	}

	public String getSandi(){
		return sandi;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"AdminItem{" + 
			"nama = '" + nama + '\'' + 
			",id_admin = '" + idAdmin + '\'' + 
			",sandi = '" + sandi + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}