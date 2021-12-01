package br.com.joi.ecommerce.resources.dto;

import java.util.HashSet;
import java.util.Set;

import br.com.joi.ecommerce.domain.Cliente;

public class ClienteDto {

	private String nome;
	
	private String email;
	
	private String cpfOuCnpj;
	
	private Integer tipo;
	
	private Set<String> telefones = new HashSet<>();

	public ClienteDto(Cliente cliente) {	
		this.nome = cliente.getNome();
		this.email = cliente.getEmail();
		this.cpfOuCnpj = cliente.getCpfOuCnpj();
		this.tipo = cliente.getTipo().getCod();
		this.telefones = cliente.getTelefones();
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public Integer getTipo() {
		return tipo;
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	
	
}
