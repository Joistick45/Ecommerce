package br.com.joi.ecommerce.resources.dto;

public class TokenDto {
	
	private String token;
	private String type;

	public TokenDto(String token, String type) {
		
		this.token = token;
		this.type = type;
	}

	public String getToken() {
		return token;
	}

	public String getTipo() {
		return type;
	}
	
	

}
