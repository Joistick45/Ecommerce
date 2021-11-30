package br.com.joi.ecommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.joi.ecommerce.domain.Produto;
import br.com.joi.ecommerce.repositories.ProdutoRepository;
import br.com.joi.ecommerce.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	public Produto buscarPorId(Integer id) {
		Optional<Produto> categoriaBuscada = produtoRepository.findById(id);
		return categoriaBuscada.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}
	
	public Produto buscarPorIdListandoAsCategorias(Integer id) {
		Optional<Produto> categoriaBuscada = produtoRepository.findById(id);
		System.out.println(categoriaBuscada);
		return categoriaBuscada.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}
	
	
	
}
