package br.com.joi.ecommerce.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.joi.ecommerce.domain.autentication.Usuario;
import br.com.joi.ecommerce.repositories.UsuarioRepository;
import br.com.joi.ecommerce.resources.dto.UsuarioDto;
import br.com.joi.ecommerce.resources.form.NovoUsuarioForm;

@RestController
@RequestMapping(value="/usuarios")
public class UsuariosResource {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping
	@Cacheable(value = "listaUsuarios")
	public Page<UsuarioDto> findAllUsuarios(@RequestParam(required = false) String email, 
			@PageableDefault (sort = "id", direction = Direction.ASC, size = 10) Pageable paginacao){
		
		if(email != null) {
			Page<Usuario> usuarioBuscado = usuarioRepository.findByEmail(email,paginacao);
			return UsuarioDto.convertertToPage(usuarioBuscado);

		}else {
			Page<Usuario> usuarios = usuarioRepository.findAll(paginacao);
			return UsuarioDto.convertertToPage(usuarios);
		}
	}
	
	@PostMapping
	@CacheEvict(value = "listaUsuarios", allEntries = true)
	public ResponseEntity<UsuarioDto> cadastraUsuarioNovo(@RequestBody @Valid NovoUsuarioForm form, UriComponentsBuilder uriBuilder){
		Usuario usuario = form.convertFormToObj();
		usuarioRepository.save(usuario);
		
		URI uri = uriBuilder.path("/categorias/{id}").buildAndExpand(usuario.getId()).toUri();
		return ResponseEntity.created(uri).body(new UsuarioDto(usuario));
	}

	
	
	
	
}
