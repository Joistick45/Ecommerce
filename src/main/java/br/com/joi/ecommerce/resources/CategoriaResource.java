package br.com.joi.ecommerce.resources;

import java.net.URI;
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
import br.com.joi.ecommerce.repositories.CategoriaRepository;
import br.com.joi.ecommerce.resources.dto.CategoriaDto;
import br.com.joi.ecommerce.resources.form.CategoriaForm;
import br.com.joi.ecommerce.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	/*
	categorias?page=0&size=10&sort=id,desc
	*/
	@GetMapping
	@Cacheable(value = "listaCategorias")
	public Page<CategoriaDto> findAllCategorias(@RequestParam(required = false) String id, 
			@PageableDefault (sort = "id", direction = Direction.ASC, size = 10) Pageable paginacao){

		Page<Categoria> categorias = categoriaRepository.findAll(paginacao);
		return CategoriaDto.converterToPage(categorias);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<?> findCategoriasComProdutos(@PathVariable Integer id) {
			Categoria objetoBuscado = categoriaService.buscarPorId(id);
			return ResponseEntity.ok().body(objetoBuscado);	
	}
	
	@PostMapping
	@CacheEvict(value = "listaCategorias", allEntries = true)
	public ResponseEntity<CategoriaDto> cadastraCategoria(@RequestBody @Valid CategoriaForm form, UriComponentsBuilder uriBuilder){
		Categoria categoria = form.convertFormToObj();
		categoriaRepository.save(categoria);
		
		URI uri = uriBuilder.path("/categorias/{id}").buildAndExpand(categoria.getId()).toUri();
		return ResponseEntity.created(uri).body(new CategoriaDto(categoria));
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<CategoriaDto> atualizaCategoria(@PathVariable Integer id,
			@RequestBody @Valid CategoriaForm form){

		Optional<Categoria> optional = categoriaRepository.findById(id);
			
				if(optional.isPresent()) {
				Categoria categoriaAtualizada = form.atualizar(id, categoriaRepository);
				return ResponseEntity.ok(new CategoriaDto(categoriaAtualizada));
				} else {
				return ResponseEntity.notFound().build();
				}
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> deletaCategoria(@PathVariable Integer id){
		Optional<Categoria> optional = categoriaRepository.findById(id);
		
		if(optional.isPresent()) {
			categoriaRepository.deleteById(id);
			return ResponseEntity.ok().build();
			} else {
			return ResponseEntity.notFound().build();
			}	
	}
	
	
	
}
