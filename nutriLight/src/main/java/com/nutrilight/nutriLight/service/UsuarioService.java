package com.nutrilight.nutriLight.service;

import java.nio.charset.Charset;
import java.util.Optional;
/*INSERIR MANUALMENTE*/
import org.apache.commons.codec.binary.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nutrilight.nutriLight.model.Lista;
import com.nutrilight.nutriLight.model.Produto;
import com.nutrilight.nutriLight.model.UserLogin;
import com.nutrilight.nutriLight.model.Usuario;
import com.nutrilight.nutriLight.repository.ListaRepository;
import com.nutrilight.nutriLight.repository.ProdutoRepository;
import com.nutrilight.nutriLight.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private ListaRepository listaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;

	public Optional<Usuario> CadastrarUsuario(Usuario usuario) {	
		
		/* CONDICAO PARA INPEDIR A CRIACAO DE UM USUARIO DUPLICADO DENTRO DA APLICACAO */
		if(repository.findByUsername(usuario.getUsername()).isPresent() && usuario.getId() == 0) {
			return null;
			
		}
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		String senhaEncoder = encoder.encode(usuario.getSenha());
		usuario.setSenha(senhaEncoder);
		
		/* ATRIBUICAO DE LISTA AO USUARIO */
		Lista lista = new Lista();
		
		/* REGISTRA O USUARIO NA BASE DE DADOS */
		repository.save(usuario);
		
		/* ASSOCIA O USUARIO A LISTA */
		lista.setUsuario(usuario);
		
		/* REGISTRA O CARRINHO NA BASE DE DADOS */
		listaRepository.save(lista);
		
		/* FINALIZA O CADASTRO DO USUARIO NO SISTEMA */
		return Optional.of(repository.save(usuario));
	}

	public Optional<UserLogin> Logar(Optional<UserLogin> user) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> usuario = repository.findByUsername(user.get().getUsername());

		if (usuario.isPresent()) {
			if (encoder.matches(user.get().getSenha(), usuario.get().getSenha())) {

				String auth = user.get().getUsername() + ":" + user.get().getSenha();
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);

				/* INSERE O TOKEN GERADO DENTRO DE NOSSO ATRIBUTO TOKEN */
				user.get().setToken(authHeader);
				
				user.get().setId(usuario.get().getId());
				user.get().setNome(usuario.get().getNome());
				user.get().setFoto(usuario.get().getFoto());
				user.get().setSenha(usuario.get().getSenha());
				user.get().setAltura(usuario.get().getAltura());
				user.get().setUsername(usuario.get().getUsername());
				user.get().setIdade(usuario.get().getIdade());
				user.get().setPeso(usuario.get().getPeso());
				user.get().setLista(usuario.get().getLista());
				user.get().setImc(usuario.get().getImc());

				return user;

			}
		}
		return null;
	}
	
	public Optional<Usuario> atualizarUsuario(Usuario usuario) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		String senhaEncoder = encoder.encode(usuario.getSenha());
		usuario.setSenha(senhaEncoder);
		
		return Optional.of(repository.save(usuario));
	}
	
	/* GERENCIAMENTO DE TABELAS ASSOCIATIVAS (MANY-TO-MANY) */
	public Usuario likePostagem(long idUsuario, long idPostagem) {
		
		Optional<Usuario> usuarioExistente = repository.findById(idUsuario);
		Optional<Produto> produtoExistente = produtoRepository.findById(idPostagem);
		
		/* CASO CASO O USUARIO AINDA NAO TENHA DADO LIKE, ENTAO E COMPUTADO UM NOVO LIKE NO PRODUTO */
		if(!(usuarioExistente.get().getLikeProduto().contains(produtoExistente.get()))) {
			
			if(usuarioExistente.isPresent() && produtoExistente.isPresent()) {
				usuarioExistente.get().getLikeProduto().add(produtoExistente.get());
				
				repository.save(usuarioExistente.get());
				
				return repository.save(usuarioExistente.get());
				
			}
			
		}else {
			/* CASO CONTRARIO O LIKE DO USUARIO E REMOVIDO DO PRODUTO */
			if(usuarioExistente.isPresent() && produtoExistente.isPresent()) {
				usuarioExistente.get().getLikeProduto().remove(produtoExistente.get());
				
				repository.save(usuarioExistente.get());
				
				return repository.save(usuarioExistente.get());
				
			}
			
		}

		return null;
	}

}
