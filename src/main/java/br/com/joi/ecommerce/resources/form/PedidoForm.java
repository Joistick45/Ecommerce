package br.com.joi.ecommerce.resources.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import br.com.joi.ecommerce.domain.Cliente;
import br.com.joi.ecommerce.domain.Endereco;
import br.com.joi.ecommerce.domain.ItemPedido;
import br.com.joi.ecommerce.domain.Pagamento;
import br.com.joi.ecommerce.domain.PagamentoComBoleto;
import br.com.joi.ecommerce.domain.PagamentoComCartao;
import br.com.joi.ecommerce.domain.Pedido;
import br.com.joi.ecommerce.domain.Produto;
import br.com.joi.ecommerce.domain.enums.EstadoPagamento;
import br.com.joi.ecommerce.repositories.ClienteRepository;
import br.com.joi.ecommerce.repositories.EnderecoRepository;
import br.com.joi.ecommerce.repositories.ItemPedidoRepository;
import br.com.joi.ecommerce.repositories.PagamentoRepository;
import br.com.joi.ecommerce.repositories.PedidoRepository;
import br.com.joi.ecommerce.repositories.ProdutoRepository;

public class PedidoForm {
	
	private Integer clienteId;
	private Integer enderecoId;
	private Integer produtoId;
	
	
	private String tipoPagamento;
//corpo do json opcional
	//boleto bancário
	private String dataPagamentoBoleto;
	private String dataVencimentoBoleto;
	
	//cartão de credito
	private Integer numeroDeParcelasCartao;


	public Pedido convertFormToObj(
			Cliente cliente,
			Endereco endereco,
			Produto produto,
			PedidoRepository pedidoRepository,
			PagamentoRepository pagamentoRepository,
			ItemPedidoRepository itemPedidoRepository) throws ParseException{
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

		
		Pedido pedido = new Pedido(null, sdf.parse("05/12/2021 10:32"), cliente, endereco);
				
		cliente.getPedidos().addAll(Arrays.asList(pedido));
		
		Pagamento pagamento = null;
		
		if (tipoPagamento.contains("BOLETO")) {
			pagamento = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, pedido, sdf.parse("20/10/2017 00:00"), null);
			pedido.setPagamento(pagamento);
		}else {
			pagamento = new PagamentoComCartao(null, EstadoPagamento.PENDENTE, pedido, this.numeroDeParcelasCartao);
			pedido.setPagamento(pagamento);
		}
		
		pedidoRepository.saveAll(Arrays.asList(pedido));
		pagamentoRepository.saveAll(Arrays.asList(pagamento));
		
		
		
//		
//		cliente.getPedidos().addAll(Arrays.asList(pedido));
//		
//		pedidoRepository.saveAll(Arrays.asList(pedido));
//		pagamentoRepository.saveAll(Arrays.asList(pagamento));
//		
//		
//		// ITEM PEDIDO
//		Produto produto = produtoRepository.getById(produtoId);	
//		ItemPedido itemPedido = new ItemPedido(pedido, produto, 0.00, 1, 2000.00);
//		
//		pedido.getItens().addAll(Arrays.asList(itemPedido));
//		produto.getItens().addAll(Arrays.asList(itemPedido));
//		
//		itemPedidoRepository.saveAll(Arrays.asList(itemPedido));
//		
//		return pedido;
		return null;
	} 
	


	@Override
	public String toString() {
		return "PedidoForm [clienteId=" + clienteId + ", enderecoId=" + enderecoId + ", produtoId=" + produtoId
				+ ", tipoPagamento=" + tipoPagamento + ", numeroDeParcelasCartao=" + numeroDeParcelasCartao + "]";
	}







	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

	public Integer getEnderecoId() {
		return enderecoId;
	}

	public void setEnderecoId(Integer enderecoId) {
		this.enderecoId = enderecoId;
	}

	public Integer getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Integer produtoId) {
		this.produtoId = produtoId;
	}

	public String getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

//	public String getDataPagamentoBoleto() {
//		return dataPagamentoBoleto;
//	}
//
//	public void setDataPagamentoBoleto(String dataPagamentoBoleto) {
//		this.dataPagamentoBoleto = dataPagamentoBoleto;
//	}
//
//	public String getDataVencimentoBoleto() {
//		return dataVencimentoBoleto;
//	}
//
//	public void setDataVencimentoBoleto(String dataVencimentoBoleto) {
//		this.dataVencimentoBoleto = dataVencimentoBoleto;
//	}
//
	public Integer getNumeroDeParcelasCartao() {
		return numeroDeParcelasCartao;
	}

	public void setNumeroDeParcelasCartao(Integer numeroDeParcelasCartao) {
		this.numeroDeParcelasCartao = numeroDeParcelasCartao;
	}
	
	

}
