package br.com.joi.ecommerce.resources.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

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

		// cria ordem de pedido
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		Pedido pedido = new Pedido(null, sdf.parse("05/12/2021 10:32"), cliente, endereco);

		// seta metodo de pagamento do pedido
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
		
		//monta carrinho com itens do pedido	
		JSONArray itens = new JSONArray(this.itens);
		
		
		Set<ItemPedido> itensPedido = new HashSet<>();
	
		for (Object object : itens) {		
			JSONObject itensObject = new JSONObject(object.toString());
			JSONObject produtoObject = itensObject.getJSONObject("produto");
			
			Produto produto = produtoRepository.getById(produtoObject.getInt("id"));
			
			ItemPedido itemPedido = new ItemPedido(pedido, produto, itensObject.getDouble("desconto"), itensObject.getInt("quantidade"), itensObject.getDouble("preco"));
			pedido.getItens().addAll(Arrays.asList(itemPedido));
			produto.getItens().addAll(Arrays.asList(itemPedido));
			itensPedido.add(itemPedido);			
		}
		
		itemPedidoRepository.saveAll(itensPedido);
		return pedido;
	}
	
//	public String teste() {
//
//		JSONArray itens = new JSONArray(this.itens);
//		
//		for (Object object : itens) {		
//			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//			JSONObject itensObject = new JSONObject(object.toString());
//			JSONObject produtoObject = itensObject.getJSONObject("produto");
//			System.out.println(itensObject);
//			System.out.println(itensObject.getDouble("preco"));
//			System.out.println(itensObject.getDouble("desconto"));
//			System.out.println(itensObject.getDouble("quantidade"));		
//			System.out.println(produtoObject);
//			System.out.println(produtoObject.getInt("id"));
//			
//		}
//
//
//		return null;	
//	}
	
	
}