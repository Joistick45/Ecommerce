package br.com.joi.ecommerce.resources.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import br.com.joi.ecommerce.domain.Categoria;
import br.com.joi.ecommerce.domain.Produto;

public class ProdutoDto {

	private Integer id;
	private String nome;
	private Double preco;
	
	
	private List<Categoria> categorias = new ArrayList<>();
	
	
	public ProdutoDto(Produto produto) {
		this.id = produto.getId();
		this.nome = produto.getNome();
		this.preco = produto.getPreco();
		this.categorias = produto.getCategorias();
		
	}


	public Integer getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}
	public Double getPreco() {
		return preco;
	}
	public List<Categoria> getCategorias() {
		return categorias;
	}
	
	public static List<ProdutoDto> converterToDto(List<Produto> produtos) {
		return produtos
				.stream()
				.map(ProdutoDto::new)
				.collect(Collectors.toList());
	}
	
	public static Page<ProdutoDto> converterToPage(Page<Produto> produtos) {
		return produtos.map(ProdutoDto::new);
	}
	
	

}
