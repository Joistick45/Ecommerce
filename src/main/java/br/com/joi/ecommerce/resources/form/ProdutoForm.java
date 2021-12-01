package br.com.joi.ecommerce.resources.form;

import java.util.ArrayList;
import java.util.List;

import br.com.joi.ecommerce.domain.Produto;
import br.com.joi.ecommerce.repositories.CategoriaRepository;
import br.com.joi.ecommerce.repositories.ProdutoRepository;

public class ProdutoForm {


	private String nome;
	
	private Double preco;

	private List<Integer> categorias;

	
	
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

	public List<Integer> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Integer> categorias) {
		this.categorias = categorias;
	}

	public Produto convertFormToObj() {	
		return new Produto(null,nome,preco);
	}
	
	public List<Integer> getCategoriasByFormId() {
		ArrayList<Integer> categoriasId = new ArrayList<Integer>();
		categoriasId.addAll(categorias);
		return categoriasId;
	}
	
	@Override
	public String toString() {
		return "ProdutoForm [nome=" + nome + ", preco=" + preco + ", categorias=" + categorias + "]";
	}

	public Produto atualizar(Integer id, ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
		Produto produto = produtoRepository.getById(id);
		
		produto.setNome(this.nome);
		produto.setPreco(this.preco);
		produto.setCategoriaById(this.categorias, categoriaRepository);
		
		return produto;
	}


	
}
