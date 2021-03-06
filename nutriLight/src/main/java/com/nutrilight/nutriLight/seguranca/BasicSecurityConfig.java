package com.nutrilight.nutriLight.seguranca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// liberar edpoints para poderem ser acessados sem a necessidade de um token
		http.authorizeRequests()
		.antMatchers("/**").permitAll() 
		.antMatchers("/usuarios/logar").permitAll()
		.antMatchers("/usuarios/cadastrar").permitAll()
		.antMatchers(HttpMethod.GET, "/usuarios").permitAll()
		.antMatchers(HttpMethod.GET, "/categorias").permitAll()
		.antMatchers(HttpMethod.GET, "/listas").permitAll()
		.antMatchers(HttpMethod.GET, "/produtos").permitAll()
		.antMatchers(HttpMethod.GET, "/mensagens").permitAll()
		
		
		/*.antMatchers("/usuarios/logar").permitAll()
		.antMatchers("/usuarios/cadastrar").permitAll()
		.antMatchers("/usuarios/{id}").permitAll()
		.antMatchers("/usuarios/likes_usuario_postagem/likeProduto/{idProduto}/like/{idUsuario}").permitAll()
		.antMatchers("/categorias").permitAll()
		.antMatchers("/categorias/{id}").permitAll()
		.antMatchers("/listas").permitAll()
		.antMatchers("/listas/{id}").permitAll()
		.antMatchers("/listas/listaProdutos/{idListaUsuario}").permitAll()
		.antMatchers("/produtos").permitAll()
		.antMatchers("/produtos/{id}").permitAll()
		.antMatchers("/produtos/editar_produto").permitAll()
		.antMatchers("/produtos/tabela_produtos").permitAll()
		.antMatchers("/produtos/tabela_produtos/produtos/{idProduto}/lista/{idLista}").permitAll()
		.antMatchers("/produtos/deleta/tabela_produtos/produtos/{idProduto}/lista/{idLista}").permitAll()
		.antMatchers("/produtos/produtosUsuario/{id}").permitAll()
		.antMatchers("/mensagens").permitAll()
		.antMatchers("/mensagens/{id}").permitAll()*/
		// nao deixar acessar os demais endpoints sem estarem com um token
		.anyRequest().authenticated()
		// trabalha com uma seguranca basica
		.and().httpBasic()
		.and().sessionManagement()
		// STATELESS -> nao salva a secao
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().cors()
		// desabilita as configuracoes padroes
		.and().csrf().disable();
	}

}

