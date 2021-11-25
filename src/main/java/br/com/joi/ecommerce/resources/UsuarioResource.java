package br.com.joi.ecommerce.resources;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.joi.ecommerce.domain.autentication.Usuario;
import br.com.joi.ecommerce.repositories.UsuarioRepository;

@RestController
@RequestMapping(value="/usuarios")
public class UsuarioResource {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping
	public ResponseEntity<?> findByEmail(@PathVariable String email) {
			Optional<Usuario> objetoBuscado = usuarioRepository.findByEmail(email);
			return ResponseEntity.ok().body(objetoBuscado);	
	}
	

}
