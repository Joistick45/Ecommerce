package br.com.joi.ecommerce.resources.dto;

import br.com.joi.ecommerce.domain.Endereco;

public class EnderecoDto {
	
	private Integer enderecoId;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cep;
	private String cidade;
	private String estado;
	
	public EnderecoDto(Endereco endereco) {
		this.enderecoId = endereco.getId();
		this.logradouro = endereco.getLogradouro();
		this.numero = endereco.getNumero();
		this.complemento = endereco.getComplemento();
		this.bairro = endereco.getBairro();
		this.cep = endereco.getCep();
		this.cidade = endereco.getCidade().getNome();
		this.estado = endereco.getCidade().getEstado().getNome();	
	}
	
	public Integer getEnderecoId() {
		return enderecoId;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public String getNumero() {
		return numero;
	}
	public String getComplemento() {
		return complemento;
	}
	public String getBairro() {
		return bairro;
	}
	public String getCep() {
		return cep;
	}
	public String getCidade() {
		return cidade;
	}
	public String getEstado() {
		return estado;
	}


}
