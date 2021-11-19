package br.com.joi.ecommerce.resources;

import java.net.URI;
import java.util.List;

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

	@GetMapping
	public List<CategoriaDto> findall(){
		List<Categoria> categorias = categoriaRepository.findAll();
		return CategoriaDto.converterToDto(categorias);
	}
	

	@PostMapping
	public ResponseEntity<CategoriaDto> cadastrar(@RequestBody @Valid CategoriaForm form, UriComponentsBuilder uriBuilder){
		Categoria categoria = form.convertFormToObj();
		categoriaRepository.save(categoria);
		
		URI uri = uriBuilder.path("/categorias/{id}").buildAndExpand(categoria.getId()).toUri();
		return ResponseEntity.created(uri).body(new CategoriaDto(categoria));
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<?> findCategoriasComProdutos(@PathVariable Integer id) {
			Categoria objetoBuscado = categoriaService.buscarPorId(id);
			return ResponseEntity.ok().body(objetoBuscado);	
	}

}
