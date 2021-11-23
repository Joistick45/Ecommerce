package br.com.joi.ecommerce.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.joi.ecommerce.repositories.UsuarioRepository;

@EnableWebSecurity
@Configuration
//@Profile(value = {"prod","test"}) //-Dspring.profiles.active=prod
public class SecurityConfigurations extends WebSecurityConfigurerAdapter{
	
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	
	
	//configurações de Autenticação
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoService)
		.passwordEncoder(new BCryptPasswordEncoder());
		
	}
	
	
	
	//configurações de Autorização (urls do projeto)
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		
		.antMatchers(HttpMethod.GET,"/categorias").permitAll()
		.antMatchers(HttpMethod.GET,"/categorias/*").permitAll()
		.antMatchers(HttpMethod.POST,"/auth").permitAll()		
		.anyRequest().authenticated()
		
		.and().csrf().disable()				//disable cross-site request forwarding
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //auth statelles without session
		.and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);;
		
	}
	
	
	
	
	//configuraçõe de recursos estaticos (js, css, imagens, etc.)
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/*.html",
									"/v2/api-docs",
									"/webjars/**",
									"/configuration/**",
									"/swagger-resources/**");
	}
	
	
//	public static void main(String[] args) {
//		System.out.println(new BCryptPasswordEncoder().encode("123456"));
//	}
	

	
}
