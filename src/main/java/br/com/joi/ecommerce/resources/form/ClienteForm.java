package br.com.joi.ecommerce.resources.form;

import java.util.HashSet;
import java.util.Set;

import br.com.joi.ecommerce.domain.Cliente;
import br.com.joi.ecommerce.domain.enums.TipoCliente;
import br.com.joi.ecommerce.repositories.ClienteRepository;

public class ClienteForm {
	
	private String nome;
	
	private String email;
	
	private String cpfOuCnpj;
	
	private Integer tipo;
	
	private Set<String> telefones = new HashSet<>();

	
	
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

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}

	public Cliente convertFormToObj() {	
		Cliente cliente = new Cliente(null, nome,email,cpfOuCnpj,TipoCliente.toEnum(tipo));
		cliente.setTelefones(telefones);
		return cliente;
	}

	@Override
	public String toString() {
		return "ClienteForm [nome=" + nome + ", email=" + email + ", cpfOuCnpj=" + cpfOuCnpj + ", tipo=" + tipo
				+ ", telefones=" + telefones + "]";
	}

	public Cliente atualizar(Integer id, ClienteRepository clienteRepository) {
		Cliente cliente = clienteRepository.getById(id);
		
		cliente.setNome(this.nome);
		cliente.setEmail(this.email);
		cliente.setCpfOuCnpj(this.cpfOuCnpj);
		cliente.setTipo(this.tipo);;
		cliente.setTelefones(this.telefones);
	
		return cliente;
	}
	
	
}
