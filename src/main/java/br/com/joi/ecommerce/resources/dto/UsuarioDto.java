package br.com.joi.ecommerce.resources.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import br.com.joi.ecommerce.domain.autentication.Usuario;

public class UsuarioDto {
	
	private Integer id;
	private String nome;
	private String email;
	
	public UsuarioDto(Usuario usuario) {
		this.setId(usuario.getId());
		this.nome = usuario.getNome();
		this.email = usuario.getEmail();
	}

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public static List<UsuarioDto> convertertToDto(List<Usuario> usuarios) {
		return usuarios
				.stream()
				.map(UsuarioDto::new)
				.collect(Collectors.toList());
	}
	
	public static Page<UsuarioDto> convertertToPage(Page<Usuario> usuarios) {
		return usuarios.map(UsuarioDto::new);
	}



}
