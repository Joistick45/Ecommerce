package br.com.joi.ecommerce.resources.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.joi.ecommerce.domain.Categoria;
import br.com.joi.ecommerce.repositories.CategoriaRepository;

public class CategoriaForm {

	@NotNull  @NotEmpty @NotBlank @Size(min = 3)
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Categoria convertFormToObj() {
		return new Categoria(null, nome);
	}

	public Categoria atualizar(Integer id, CategoriaRepository categoriaRepository) {
		Categoria categoria = categoriaRepository.getById(id);
		
		categoria.setNome(this.nome);
		System.out.println(this.nome);
		
		return categoria;	
	}

	
}
