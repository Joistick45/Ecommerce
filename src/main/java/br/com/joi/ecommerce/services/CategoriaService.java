package br.com.joi.ecommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.joi.ecommerce.domain.Categoria;
import br.com.joi.ecommerce.repositories.CategoriaRepository;
import br.com.joi.ecommerce.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria buscarPorId(Integer id) {
		Optional<Categoria> categoriaBuscada = categoriaRepository.findById(id);
		return categoriaBuscada.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
}
