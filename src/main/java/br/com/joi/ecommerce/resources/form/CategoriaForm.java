package br.com.joi.ecommerce.resources.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.joi.ecommerce.domain.Categoria;

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
	
	
}
