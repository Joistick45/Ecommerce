package br.com.joi.ecommerce.resources;

import java.net.URI;
import java.util.List;
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
import br.com.joi.ecommerce.domain.Endereco;
import br.com.joi.ecommerce.repositories.CidadeRepository;
import br.com.joi.ecommerce.repositories.ClienteRepository;
import br.com.joi.ecommerce.repositories.EnderecoRepository;
import br.com.joi.ecommerce.repositories.EstadoRepository;
import br.com.joi.ecommerce.resources.dto.ClienteDto;
import br.com.joi.ecommerce.resources.dto.EnderecoDto;
import br.com.joi.ecommerce.resources.form.ClienteForm;
import br.com.joi.ecommerce.resources.form.EnderecoForm;
import br.com.joi.ecommerce.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;	
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	
	
	@GetMapping(value="/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id) {
			Cliente objetoBuscado = clienteService.buscarPorId(id);
			return ResponseEntity.ok().body(objetoBuscado);	
	}
	
	@PostMapping
	public ResponseEntity<ClienteDto> cadastraCliente(@RequestBody @Valid ClienteForm form, UriComponentsBuilder uriBuilder){
		Cliente cliente = form.convertFormToObj();
		clienteRepository.save(cliente);	
		URI uri = uriBuilder.path("/clientes/{id}").buildAndExpand(cliente.getId()).toUri();
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
	
	//
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
	
	@GetMapping(value="/{id}/enderecos")
	public ResponseEntity<?> findAllEnderecosDeUmCliente(@PathVariable Integer id) {
			Cliente clienteBuscado = clienteService.buscarPorId(id);
			List<Endereco> enderecosBuscados = clienteBuscado.getEnderecos();
			return ResponseEntity.ok().body(enderecosBuscados);	
	}
	
	@PostMapping(value="/{clienteId}/enderecos")
	public ResponseEntity<EnderecoDto> cadastraEnderecoParaCliente(@PathVariable("clienteId") Integer clienteId,
			@RequestBody @Valid EnderecoForm form,
			UriComponentsBuilder uriBuilder){

		Cliente cliente = clienteRepository.getById(clienteId);
		Endereco endereco = form.convertFormToObj(cliente,estadoRepository,cidadeRepository);	
		enderecoRepository.save(endereco);		
		
		URI uri = uriBuilder.path("/clientes/{id}").buildAndExpand(endereco.getId()).toUri();
		return ResponseEntity.created(uri).body(new EnderecoDto(endereco));
	}
	
	@PutMapping(value="/{clienteId}/enderecos/{enderecoId}")
	@Transactional
	public ResponseEntity<EnderecoDto> atualizaEnderecoDoCliente(@PathVariable("clienteId") Integer clienteId,@PathVariable("enderecoId") Integer enderecoId,
			@RequestBody @Valid EnderecoForm form){
		Optional<Endereco> optional = enderecoRepository.findById(enderecoId);
		
			if(optional.isPresent()) {
				Endereco enderecoAtualizado = form.atualizar(enderecoId, enderecoRepository,estadoRepository,cidadeRepository);
				return ResponseEntity.ok(new EnderecoDto(enderecoAtualizado));
			} else {
				return ResponseEntity.notFound().build();
			}
	}
	
	
	//não deixa deletar endereço, se tiver pedido vinculado
	@DeleteMapping(value="/{clienteId}/enderecos/{enderecoId}")
	@Transactional
	public ResponseEntity<?> deletaEnderecoDoCliente(@PathVariable("clienteId") Integer clienteId,
													@PathVariable("enderecoId") Integer enderecoId){
		Optional<Endereco> optional = enderecoRepository.findById(enderecoId);
		
		if(optional.isPresent()) {
			enderecoRepository.deleteById(enderecoId);
			return ResponseEntity.ok().build();
			} else {	
			return ResponseEntity.notFound().build();
			}	
	}

}
