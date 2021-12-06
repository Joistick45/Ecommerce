package br.com.joi.ecommerce.resources;

import java.text.ParseException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.joi.ecommerce.domain.Cliente;
import br.com.joi.ecommerce.domain.Endereco;
import br.com.joi.ecommerce.domain.Pedido;
import br.com.joi.ecommerce.domain.Produto;
import br.com.joi.ecommerce.repositories.ClienteRepository;
import br.com.joi.ecommerce.repositories.EnderecoRepository;
import br.com.joi.ecommerce.repositories.ItemPedidoRepository;
import br.com.joi.ecommerce.repositories.PagamentoRepository;
import br.com.joi.ecommerce.repositories.PedidoRepository;
import br.com.joi.ecommerce.repositories.ProdutoRepository;
import br.com.joi.ecommerce.resources.dto.PedidoDto;
import br.com.joi.ecommerce.resources.form.PedidoForm;
import br.com.joi.ecommerce.services.PedidoService;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@GetMapping(value="/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id) {
			Pedido objetoBuscado = pedidoService.buscarPorId(id);
			return ResponseEntity.ok().body(objetoBuscado);	
	}
	
	@PostMapping
	public ResponseEntity<PedidoDto> novoPedido(@RequestBody @Valid PedidoForm form, UriComponentsBuilder uriBuilder) throws ParseException{
		System.out.println(form.toString());
		Cliente cliente = clienteRepository.getById(form.getClienteId());
		Endereco endereco = enderecoRepository.getById(form.getEnderecoId());
		Produto produto = produtoRepository.getById(form.getProdutoId());

		
		Pedido pedido = form.convertFormToObj(cliente, endereco, produto, pedidoRepository, pagamentoRepository, itemPedidoRepository);


//		pedidoRepository.save(pedido);	
//		URI uri = uriBuilder.path("/clientes/{id}").buildAndExpand(pedido.getId()).toUri();
//		return ResponseEntity.created(uri).body(new PedidoDto(pedido));
		return null;
	}
}
