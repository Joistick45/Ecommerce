package br.com.joi.ecommerce.resources.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import br.com.joi.ecommerce.repositories.ItemPedidoRepository;
import br.com.joi.ecommerce.repositories.PagamentoRepository;
import br.com.joi.ecommerce.repositories.PedidoRepository;
import br.com.joi.ecommerce.repositories.ProdutoRepository;

public class PedidoForm {

	private Object pagamento;
	private Object cliente;
	private Object enderecoDeEntrega;
	private ArrayList<Object> itens;



	// boleto bancário
	private String dataPagamentoBoleto;
	private String dataVencimentoBoleto;

	// cartão de credito
	private Integer numeroDeParcelasCartao;

	public Object getPagamento() {
		return pagamento;
	}

	public Object getCliente() {
		return cliente;
	}

	public Object getEnderecoDeEntrega() {
		return enderecoDeEntrega;
	}

	public ArrayList<Object> getItens() {
		return itens;
	}

	public String getDataPagamentoBoleto() {
		return dataPagamentoBoleto;
	}

	public String getDataVencimentoBoleto() {
		return dataVencimentoBoleto;
	}

	public Integer getNumeroDeParcelasCartao() {
		return numeroDeParcelasCartao;
	}
	
	public Integer getClienteId() {
		String clienteString = this.cliente.toString();
		return Integer.parseInt(clienteString.substring(4, clienteString.length() - 1));

	}

	public Integer getEnderecoDeEntregaId() {
		String enderecoString = this.enderecoDeEntrega.toString();
		return Integer.parseInt(enderecoString.substring(4, enderecoString.length() - 1));
	}

	public Pedido convertFormToObj(Cliente cliente, Endereco endereco, ProdutoRepository produtoRepository,
			PedidoRepository pedidoRepository, PagamentoRepository pagamentoRepository,
			ItemPedidoRepository itemPedidoRepository) throws ParseException {

		// cria pedido
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		Pedido pedido = new Pedido(null, sdf.parse("05/12/2021 10:32"), cliente, endereco);

		// seta metodo de pagamento
		String[] informacoesPagamento = this.pagamento.toString().substring(1, this.pagamento.toString().length() - 1)
				.split(", ");
		ArrayList<String> listaDeValores = new ArrayList<>();

		for (String informacao : informacoesPagamento) {
			String[] valores = informacao.split("=");
			listaDeValores.add(valores[1]);
		}

		Pagamento pagamento = null;
		if (listaDeValores.contains("BOLETO")) {
			pagamento = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, pedido, sdf.parse(listaDeValores.get(2)),
					null);
		} else if (listaDeValores.contains("CARTAO")) {
			pagamento = new PagamentoComCartao(null, EstadoPagamento.PENDENTE, pedido,
					Integer.parseInt(listaDeValores.get(2)));
		}


		pedido.setPagamento(pagamento);
		cliente.getPedidos().addAll(Arrays.asList(pedido));

		pedidoRepository.saveAll(Arrays.asList(pedido));
		pagamentoRepository.saveAll(Arrays.asList(pagamento));
//
//		Produto produto = produtoRepository.getById(1);
//		
//		// ITEM PEDIDO
//		ItemPedido itemPedido = null;//new ItemPedido(pedido, produto, 0.00, 1, 2000.00);
//
//		pedido.getItens().addAll(Arrays.asList(itemPedido));
//		produto.getItens().addAll(Arrays.asList(itemPedido));
//
//		itemPedidoRepository.saveAll(Arrays.asList(itemPedido));
//
//		return pedido;
		return null;

	}
	
	public String teste() {
		
		System.out.println(this.itens.toString());
		
		
		
		return null;	
	}
	
	
}