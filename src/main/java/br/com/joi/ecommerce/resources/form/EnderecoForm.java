package br.com.joi.ecommerce.resources.form;

import java.util.Arrays;

import br.com.joi.ecommerce.domain.Cidade;
import br.com.joi.ecommerce.domain.Cliente;
import br.com.joi.ecommerce.domain.Endereco;
import br.com.joi.ecommerce.domain.Estado;
import br.com.joi.ecommerce.repositories.CidadeRepository;
import br.com.joi.ecommerce.repositories.EnderecoRepository;
import br.com.joi.ecommerce.repositories.EstadoRepository;

public class EnderecoForm {

	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cep;
	private String cidade;
	private String estado;
	
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	@Override
	public String toString() {
		return "EnderecoForm [logradouro=" + logradouro + ", numero=" + numero + ", complemento=" + complemento
				+ ", bairro=" + bairro + ", cep=" + cep + ", cidade=" + cidade + ", estado=" + estado + "]";
	}
	
	
	public Endereco convertFormToObj(Cliente cliente,
			EstadoRepository estadoRepository,
			CidadeRepository cidadeRepository) {	
		
		//precisa criar algum tipo de validação para Estado e Cidade, para evitar objeto igual
		Estado estado = new Estado(null, this.estado);	
		estadoRepository.saveAll(Arrays.asList(estado));
		
		Cidade cidade = new Cidade(null, this.cidade, estado);
		cidadeRepository.saveAll(Arrays.asList(cidade));
		
		 // precisa validar endereço, se algum outro objeto no banco tem o mesmo cep e numero, para evitar objeto igual
		return new Endereco(null, this.logradouro, this.numero, this.complemento, this.bairro, this.cep, cliente, cidade);
	}
	
	public Endereco atualizar(Integer enderecoId,
			EnderecoRepository enderecoRepository,
			EstadoRepository estadoRepository,
			CidadeRepository cidadeRepository) {

		Endereco endereco = enderecoRepository.getById(enderecoId);
		Estado estado = new Estado(null, this.estado);
		Cidade cidade = new Cidade(null, this.cidade, estado);
	
		
		endereco.setLogradouro(logradouro);;
		endereco.setNumero(numero);;
		endereco.setComplemento(complemento);;
		endereco.setBairro(bairro);;
		endereco.setCep(cep);


		endereco.setCidade(cidade);
		cidadeRepository.saveAll(Arrays.asList(cidade));
		
		endereco.getCidade().setEstado(estado);;
		estadoRepository.saveAll(Arrays.asList(estado));

		return endereco;
	}
	

}
