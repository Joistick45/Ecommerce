package br.com.joi.ecommerce.resources.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import br.com.joi.ecommerce.domain.Categoria;

public class CategoriaDto {
	
	private Integer id;
	private String nome;

	public CategoriaDto(Categoria categoria) {
		this.id = categoria.getId();
		this.nome = categoria.getNome();
		
	}
	
	public Integer getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}

	public static List<CategoriaDto> converterToDto(List<Categoria> categorias) {
		return categorias
				.stream()
				.map(CategoriaDto::new)
				.collect(Collectors.toList());
	}
	
	public static Page<CategoriaDto> converterToPage(Page<Categoria> categorias) {
		return categorias.map(CategoriaDto::new);
	}
	
	

}
