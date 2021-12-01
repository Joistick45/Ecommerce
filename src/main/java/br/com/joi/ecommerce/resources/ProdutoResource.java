package br.com.joi.ecommerce.resources;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.joi.ecommerce.domain.Categoria;
import br.com.joi.ecommerce.domain.Produto;
import br.com.joi.ecommerce.repositories.CategoriaRepository;
import br.com.joi.ecommerce.repositories.ProdutoRepository;
import br.com.joi.ecommerce.resources.dto.ProdutoDto;
import br.com.joi.ecommerce.resources.form.ProdutoForm;
import br.com.joi.ecommerce.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoRepository produtoRepository;
	

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoService produtoService;

	/*
	categorias?page=0&size=10&sort=id,desc
	*/
	@GetMapping
	@Cacheable(value = "listaProdutos")
	public Page<ProdutoDto> findAllProdutos(@RequestParam(required = false) String id, 
			@PageableDefault (sort = "id", direction = Direction.ASC, size = 10) Pageable paginacao){
			
		Page<Produto> produtos = produtoRepository.findAll(paginacao);
		return ProdutoDto.converterToPage(produtos);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<?> findProdutosComCategorias(@PathVariable Integer id) {
			Produto objetoBuscado = produtoService.buscarPorId(id);
			return ResponseEntity.ok().body(objetoBuscado);	
	}
	
	
	@PostMapping
	@CacheEvict(value = "listaProdutos", allEntries = true)
	public ResponseEntity<ProdutoDto> cadastraProduto(
			@RequestBody @Valid ProdutoForm form, UriComponentsBuilder uriBuilder){
		
		Produto produto = form.convertFormToObj();	
		List<Integer> categoriasId = form.getCategoriasByFormId();
	
		for (Integer categoriaId : categoriasId) {
			Categoria categoria = categoriaRepository.getById(categoriaId);
			
			categoria.getProdutos().addAll(Arrays.asList(produto));
			produto.getCategorias().addAll(Arrays.asList(categoria));
			
			categoriaRepository.saveAll(Arrays.asList(categoria));
			produtoRepository.saveAll(Arrays.asList(produto));
			
		}	
	
		URI uri = uriBuilder.path("/protudos/{id}").buildAndExpand(produto.getId()).toUri();
		return ResponseEntity.created(uri).body(new ProdutoDto(produto));
	}
	
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaProdutos", allEntries = true)
	public ResponseEntity<ProdutoDto> atualizaProduto(@PathVariable Integer id,
			@RequestBody @Valid ProdutoForm form){
		Optional<Produto> optional = produtoRepository.findById(id);
		
			if(optional.isPresent()) {
				Produto produtoAtualizado = form.atualizar(id, produtoRepository,categoriaRepository);
				return ResponseEntity.ok(new ProdutoDto(produtoAtualizado));
			} else {
				return ResponseEntity.notFound().build();
			}
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaProdutos", allEntries = true)
	public ResponseEntity<?> deletaProduto(@PathVariable Integer id){
		Optional<Produto> optional = produtoRepository.findById(id);
		
		if(optional.isPresent()) {
			produtoRepository.deleteById(id);
			return ResponseEntity.ok().build();
			} else {	
			return ResponseEntity.notFound().build();
			}	
	}
	
	
	
}
