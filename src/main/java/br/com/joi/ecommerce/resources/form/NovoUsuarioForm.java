package br.com.joi.ecommerce.resources.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.joi.ecommerce.domain.autentication.Usuario;

public class NovoUsuarioForm {
	
	@NotNull  @NotEmpty @NotBlank @Size(min = 3)
	private String nome;
	@NotNull  @NotEmpty @NotBlank @Size(min = 3)
	private String email;
	@NotNull  @NotEmpty @NotBlank @Size(min = 3)
	private String senha;


	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Usuario convertFormToObj() {
		return new Usuario(null, nome, email,new BCryptPasswordEncoder().encode(senha));		
	}

	
	
	
	
}
