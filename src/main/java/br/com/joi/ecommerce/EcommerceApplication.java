package br.com.joi.ecommerce;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.joi.ecommerce.domain.Categoria;
import br.com.joi.ecommerce.domain.Cidade;
import br.com.joi.ecommerce.domain.Estado;
import br.com.joi.ecommerce.domain.Produto;
import br.com.joi.ecommerce.repositories.CategoriaRepository;
import br.com.joi.ecommerce.repositories.CidadeRepository;
import br.com.joi.ecommerce.repositories.EstadoRepository;
import br.com.joi.ecommerce.repositories.ProdutoRepository;

@SpringBootApplication
public class EcommerceApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p1.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		Estado est1 = new Estado(null, "São Paulo");
		Estado est2 = new Estado(null, "Rio de Janeiro");
		Estado est3 = new Estado(null, "Minas Gerais");
				
		Cidade c1 = new Cidade(null, "Santo André", est1);
		Cidade c2 = new Cidade(null, "Barra da Tijuca", est2);
		Cidade c3 = new Cidade(null, "Belo Horizonte", est3);
		Cidade c4 = new Cidade(null, "São Bernardo", est1);
		
		est1.getCidades().addAll(Arrays.asList(c1,c4));
		est2.getCidades().addAll(Arrays.asList(c2));
		est3.getCidades().addAll(Arrays.asList(c3));
		
		estadoRepository.saveAll(Arrays.asList(est1,est2,est3));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3,c4));

	}

}
