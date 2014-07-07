package com.example.fileuploaduser;

public class Token {

	public String Nama;
	public String Url;
	public Token(String Nama,String Url ){
		super();
		this.Nama = Nama;
		this.Url = Url;
	}
	
	public String getNama(){
		return Nama;
	}
	
	public String getUrl(){
		return Url;
	}
}
