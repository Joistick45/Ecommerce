package br.com.joi.ecommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.joi.ecommerce.domain.Pedido;
import br.com.joi.ecommerce.repositories.PedidoRepository;
import br.com.joi.ecommerce.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository categoriaRepository;
	
	public Pedido buscarPorId(Integer id) {
		
		Optional<Pedido> pedidoBuscado = categoriaRepository.findById(id);
		
		return pedidoBuscado.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

}
