package br.com.joi.ecommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.joi.ecommerce.domain.Cliente;
import br.com.joi.ecommerce.repositories.ClienteRepository;
import br.com.joi.ecommerce.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente buscarPorId(Integer id) {
		
		Optional<Cliente> clienteBuscado = clienteRepository.findById(id);
		
		return clienteBuscado.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

}
