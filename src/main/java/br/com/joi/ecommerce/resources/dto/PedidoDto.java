package br.com.joi.ecommerce.resources.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import br.com.joi.ecommerce.domain.Cliente;
import br.com.joi.ecommerce.domain.Endereco;
import br.com.joi.ecommerce.domain.ItemPedido;
import br.com.joi.ecommerce.domain.Pagamento;
import br.com.joi.ecommerce.domain.Pedido;

public class PedidoDto {

	private Integer pedidoId;	
	private Date timestamp;
	private Pagamento pagamento;
	private Cliente cliente;
	private Endereco enderecoDeEntrega;
	private Set<ItemPedido> itens = new HashSet<>();
	
	
	public PedidoDto(Pedido pedido) {
		this.pedidoId = pedido.getId();
		this.timestamp = pedido.getTimestamp();
		this.pagamento = pedido.getPagamento();
		this.cliente = pedido.getCliente();
		this.enderecoDeEntrega = pedido.getEnderecoDeEntrega();
		this.itens = pedido.getItens();
	}


	public Integer getPedidoId() {
		return pedidoId;
	}


	public Date getTimestamp() {
		return timestamp;
	}


	public Pagamento getPagamento() {
		return pagamento;
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
