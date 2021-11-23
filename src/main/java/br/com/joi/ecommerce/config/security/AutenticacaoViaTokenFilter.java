package br.com.joi.ecommerce.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.joi.ecommerce.domain.autenticacao.Usuario;
import br.com.joi.ecommerce.repositories.UsuarioRepository;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

	private TokenService tokenService;
	private UsuarioRepository usuarioRepository; 
		
	public AutenticacaoViaTokenFilter(TokenService tokenService,
			UsuarioRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;
	}
		
	private void autenticarCliente(String token) {	
		Integer idUsuario = tokenService.getIdUsuario(token);
		Usuario usuario = usuarioRepository.findById(idUsuario).get();		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
	}

	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");	
		if (token == null ||
			token.isEmpty() ||
			!token.startsWith("Bearer ")) {	
			return null;		
		}
		return token.substring(7, token.length());
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain)
			throws ServletException, IOException {
		String token = recuperarToken(request);
		Boolean valido = tokenService.isTokenValido(token);
		System.out.println("Token é valido ? " + valido);	
		if (valido) {
			autenticarCliente(token);
		}
		filterChain.doFilter(request, response);	
	}

}
