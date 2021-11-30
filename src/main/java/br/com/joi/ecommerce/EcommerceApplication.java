package br.com.joi.ecommerce;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import br.com.joi.ecommerce.domain.Categoria;
import br.com.joi.ecommerce.domain.Cidade;
import br.com.joi.ecommerce.domain.Cliente;
import br.com.joi.ecommerce.domain.Endereco;
import br.com.joi.ecommerce.domain.Estado;
import br.com.joi.ecommerce.domain.ItemPedido;
import br.com.joi.ecommerce.domain.Pagamento;
import br.com.joi.ecommerce.domain.PagamentoComBoleto;
import br.com.joi.ecommerce.domain.PagamentoComCartao;
import br.com.joi.ecommerce.domain.Pedido;
import br.com.joi.ecommerce.domain.Produto;
import br.com.joi.ecommerce.domain.enums.EstadoPagamento;
import br.com.joi.ecommerce.domain.enums.TipoCliente;
import br.com.joi.ecommerce.repositories.CategoriaRepository;
import br.com.joi.ecommerce.repositories.CidadeRepository;
import br.com.joi.ecommerce.repositories.ClienteRepository;
import br.com.joi.ecommerce.repositories.EnderecoRepository;
import br.com.joi.ecommerce.repositories.EstadoRepository;
import br.com.joi.ecommerce.repositories.ItemPedidoRepository;
import br.com.joi.ecommerce.repositories.PagamentoRepository;
import br.com.joi.ecommerce.repositories.PedidoRepository;
import br.com.joi.ecommerce.repositories.ProdutoRepository;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableSwagger2
@EnableCaching
public class EcommerceApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;	
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;	
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {

		Categoria categoria1 = new Categoria(null, "Informática");
		Categoria categoria2 = new Categoria(null, "Escritório");
		
		Produto produto1 = new Produto(null, "Computador", 2000.00);
		Produto produto2 = new Produto(null, "Impressora", 800.00);
		Produto produto3 = new Produto(null, "Mouse", 80.00);
		
		categoria1.getProdutos().addAll(Arrays.asList(produto1,produto2,produto3));
		categoria2.getProdutos().addAll(Arrays.asList(produto2));

		produto1.getCategorias().addAll(Arrays.asList(categoria1));
		produto2.getCategorias().addAll(Arrays.asList(categoria1,categoria2));
		produto3.getCategorias().addAll(Arrays.asList(categoria1));
		
		categoriaRepository.saveAll(Arrays.asList(categoria1,categoria2));
		produtoRepository.saveAll(Arrays.asList(produto1,produto2,produto3));
		
		Estado estado1 = new Estado(null, "São Paulo");
		Estado estado2 = new Estado(null, "Rio de Janeiro");
		Estado estado3 = new Estado(null, "Minas Gerais");
				
		Cidade cidade1 = new Cidade(null, "Santo André", estado1);
		Cidade cidade2 = new Cidade(null, "Barra da Tijuca", estado2);
		Cidade cidade3 = new Cidade(null, "Belo Horizonte", estado3);
		Cidade cidade4 = new Cidade(null, "São Bernardo", estado1);
		
		estado1.getCidades().addAll(Arrays.asList(cidade1,cidade4));
		estado2.getCidades().addAll(Arrays.asList(cidade2));
		estado3.getCidades().addAll(Arrays.asList(cidade3));
		
		estadoRepository.saveAll(Arrays.asList(estado1,estado2,estado3));
		cidadeRepository.saveAll(Arrays.asList(cidade1,cidade2,cidade3,cidade4));
		
		Cliente cliente1 = new Cliente(null,"Joi Macedo Pereira Neto", "joi@empresa.com","888888888-88", TipoCliente.PESSOAFISICA);
		cliente1.getTelefones().addAll(Arrays.asList("1144445555","1144446666"));
		
		Endereco endereco1 = new Endereco(null, "Rua das Flores", "100", "Apto 100","Vila Paulista" , "09000777", cliente1, cidade1);
		Endereco endereco2 = new Endereco(null, "Rua das Pedras", "200", "Apto 200","Vila Carioca" ,"09000888", cliente1, cidade2);

		cliente1.getEnderecos().addAll(Arrays.asList(endereco1,endereco2));
		
		clienteRepository.saveAll(Arrays.asList(cliente1));
		enderecoRepository.saveAll(Arrays.asList(endereco1,endereco2));
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		Pedido pedido1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cliente1, endereco1);
		Pedido pedido2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cliente1, endereco2);
		
		Pagamento pagamento1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedido1, 6);
		pedido1.setPagamento(pagamento1);
		
		Pagamento pagamento2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, pedido2, sdf.parse("20/10/2017 00:00"), null);
		pedido2.setPagamento(pagamento2);
		
		cliente1.getPedidos().addAll(Arrays.asList(pedido1,pedido2));
		
		pedidoRepository.saveAll(Arrays.asList(pedido1,pedido2));
		pagamentoRepository.saveAll(Arrays.asList(pagamento1,pagamento2));
		
		ItemPedido itemPedido1 = new ItemPedido(pedido1, produto1, 0.00, 1, 2000.00);
		ItemPedido itemPedido2 = new ItemPedido(pedido1, produto3, 0.00, 2, 80.00);
		ItemPedido itemPedido3 = new ItemPedido(pedido2, produto2, 100.00, 1, 800.00);
		
		pedido1.getItens().addAll(Arrays.asList(itemPedido1,itemPedido2));
		pedido2.getItens().addAll(Arrays.asList(itemPedido3));
		
		produto1.getItens().addAll(Arrays.asList(itemPedido1));
		produto2.getItens().addAll(Arrays.asList(itemPedido3));
		produto3.getItens().addAll(Arrays.asList(itemPedido2));
		
		itemPedidoRepository.saveAll(Arrays.asList(itemPedido1,itemPedido2,itemPedido3));
				
	}

}
