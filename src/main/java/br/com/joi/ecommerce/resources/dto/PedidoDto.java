package br.com.joi.ecommerce.resources.dto;

import java.util.HashSet;
import java.util.Set;

import br.com.joi.ecommerce.domain.Cliente;
import br.com.joi.ecommerce.domain.Endereco;
import br.com.joi.ecommerce.domain.ItemPedido;
import br.com.joi.ecommerce.domain.Pedido;

public class PedidoDto {
	
	private Integer id;
	private Cliente cliente;
	private Endereco enderecoDeEntrega;
	private Set<ItemPedido> itens = new HashSet<>();
	
	
	public PedidoDto(Pedido pedido) {
		super();
		this.id = pedido.getId();
		this.cliente = pedido.getCliente();
		this.enderecoDeEntrega = pedido.getEnderecoDeEntrega();
		this.itens = pedido.getItens();
	}


	public Integer getId() {
		return id;
	}


	public Cliente getCliente() {
		return cliente;
	}


	public Endereco getEnderecoDeEntrega() {
		return enderecoDeEntrega;
	}


	public Set<ItemPedido> getItens() {
		return itens;
	}
	
	

	



}
