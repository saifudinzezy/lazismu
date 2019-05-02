package com.example.lazismu.ketek.menu;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseAdmin{

	@SerializedName("code")
	private int code;

	@SerializedName("admin")
	private List<AdminItem> admin;

	@SerializedName("message")
	private String message;

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public void setAdmin(List<AdminItem> admin){
		this.admin = admin;
	}

	public List<AdminItem> getAdmin(){
		return admin;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"ResponseAdmin{" + 
			"code = '" + code + '\'' + 
			",admin = '" + admin + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}