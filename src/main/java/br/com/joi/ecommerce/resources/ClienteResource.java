package br.com.joi.ecommerce.resources;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.joi.ecommerce.domain.Cliente;
import br.com.joi.ecommerce.repositories.ClienteRepository;
import br.com.joi.ecommerce.resources.dto.ClienteDto;
import br.com.joi.ecommerce.resources.form.ClienteForm;
import br.com.joi.ecommerce.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@GetMapping(value="/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id) {
			Cliente objetoBuscado = clienteService.buscarPorId(id);
			return ResponseEntity.ok().body(objetoBuscado);	
	}
	
	@PostMapping
	public ResponseEntity<ClienteDto> cadastraCliente(@RequestBody @Valid ClienteForm form, UriComponentsBuilder uriBuilder){
		Cliente cliente = form.convertFormToObj();
		clienteRepository.save(cliente);	
		URI uri = uriBuilder.path("/categorias/{id}").buildAndExpand(cliente.getId()).toUri();
		return ResponseEntity.created(uri).body(new ClienteDto(cliente));
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<ClienteDto> atualizaCliente(@PathVariable Integer id,
			@RequestBody @Valid ClienteForm form){
		Optional<Cliente> optional = clienteRepository.findById(id);
	
			if(optional.isPresent()) {
				Cliente clienteAtualizado = form.atualizar(id, clienteRepository);
				return ResponseEntity.ok(new ClienteDto(clienteAtualizado));
			} else {
				return ResponseEntity.notFound().build();
			}
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> deletaCliente(@PathVariable Integer id){
		Optional<Cliente> optional = clienteRepository.findById(id);
		
		if(optional.isPresent()) {
			clienteRepository.deleteById(id);
			return ResponseEntity.ok().build();
			} else {	
			return ResponseEntity.notFound().build();
			}	
	}

}
