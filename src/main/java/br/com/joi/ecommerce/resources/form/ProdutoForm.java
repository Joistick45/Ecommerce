package br.com.joi.ecommerce.resources.form;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.joi.ecommerce.domain.Categoria;
import br.com.joi.ecommerce.domain.Produto;
import br.com.joi.ecommerce.repositories.ProdutoRepository;

public class ProdutoForm {

	@NotNull  @NotEmpty @NotBlank @Size(min = 3)
	private String nome;
	
	@NotNull  @NotEmpty @NotBlank @Size(min = 3)
	private Double preco;
	
	private List<Categoria> categorias = new ArrayList<>();
	
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Produto convertFormToObj() {
		return new Produto(null, nome, preco);
	}
	
	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}


	public Produto atualizar(Integer id, ProdutoRepository produtoRepository) {
		Produto produto = produtoRepository.getById(id);
		
		produto.setNome(this.nome);
		System.out.println(this.nome);
		
		return produto;	
	}


	
}
