package com.nutrilight.nutriLight.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nutrilight.nutriLight.model.UserLogin;
import com.nutrilight.nutriLight.model.Usuario;
import com.nutrilight.nutriLight.repository.UsuarioRepository;
import com.nutrilight.nutriLight.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<List<Usuario>> getAllByUsuarios() {
		
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> findByIdUsuario(@PathVariable long id) {
		
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
		
	}
	
	@GetMapping("/username/{username}")
	public ResponseEntity<List<Usuario>> findByIdNomeUsuario(@PathVariable String username) {
		
		return ResponseEntity.ok(repository.findAllByUsernameContainingIgnoreCase(username));
	}

	@PostMapping("/logar")
	public ResponseEntity<UserLogin> Autentication(@RequestBody Optional<UserLogin> user) {
		return usuarioService.Logar(user).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> Post(@RequestBody Usuario usuario) {
		Optional<Usuario> user = usuarioService.CadastrarUsuario(usuario);
		
		try {
			return ResponseEntity.ok(user.get());
			
		}catch(Exception e) {
			return ResponseEntity.badRequest().build();
			
		}
		
	}
	
	@PutMapping
	public ResponseEntity<Usuario> putUsuario(@RequestBody Usuario usuario) {
		return ResponseEntity.ok(repository.save(usuario));
		
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<Usuario> update(@RequestBody Usuario usuario) {
		Optional<Usuario> user = usuarioService.atualizarUsuario(usuario);
		
		try {
			return ResponseEntity.ok(user.get());
			
		}catch(Exception e) {
			return ResponseEntity.badRequest().build();
			
		}
		
	}
	
	/*
	 * EXPLICACAO URI:
	 * 
	 * 	/likes_usuario_postagem -> nome da tabela associativa que esta no model Postagem
	 * 	/likePostagem -> Lista de postagens na classes usuario
	 * 	/like -> Lista de usuarios na classe Postagem
	 * 
	 * */
	@PutMapping("/likes_usuario_postagem/likeProduto/{idProduto}/like/{idUsuario}")
	public ResponseEntity<Usuario> postLikePostagem(@PathVariable long idUsuario, @PathVariable long idProduto){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.likePostagem(idUsuario, idProduto));
	}
	
	@DeleteMapping("/{id}")
	public void deleteUsuario(@PathVariable long id) {
		
		repository.deleteById(id);
	}

}
