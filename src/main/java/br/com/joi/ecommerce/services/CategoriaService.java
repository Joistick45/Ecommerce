package br.com.joi.ecommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.joi.ecommerce.domain.Categoria;
import br.com.joi.ecommerce.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria buscarPorId(Integer id) {
		Optional<Categoria> categoriaBuscada = categoriaRepository.findById(id);
		return categoriaBuscada.orElse(null);
		}

}
