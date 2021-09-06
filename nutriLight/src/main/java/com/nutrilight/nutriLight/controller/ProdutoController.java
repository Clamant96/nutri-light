package com.nutrilight.nutriLight.controller;

import java.util.List;

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

import com.nutrilight.nutriLight.model.Lista;
import com.nutrilight.nutriLight.model.Produto;
import com.nutrilight.nutriLight.repository.ProdutoRepository;
import com.nutrilight.nutriLight.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository repository;
	
	@Autowired
	private ProdutoService service;
	
	@GetMapping
	public ResponseEntity<List<Produto>> getAllByProdutos() {
		
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Produto> getByIdProduto(@PathVariable long id) {
		
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/produtosUsuario/{id}")
	public ResponseEntity<List<Produto>> findByPostagemUsuario(@PathVariable long id) {
		
		return ResponseEntity.ok(repository.findAllByUsuario(id));
	}
	
	@PostMapping
	public ResponseEntity<Produto> postProduto(@RequestBody Produto produto) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(produto));
	}
	
	@PutMapping
	public ResponseEntity<Produto> putProduto(@RequestBody Produto produto) {
		
		return ResponseEntity.ok(repository.save(produto));
	}
	
	/*@PutMapping("/editar_produto")
	public ResponseEntity<Produto> putProduto(@PathVariable long idProduto, @PathVariable String username) {
		
		return ResponseEntity.ok(service.editarProdutoDoUsuario(idProduto, username));
	}*/
	
	@PutMapping("/tabela_produtos/produtos/{idProduto}/lista/{idLista}")
	public ResponseEntity<Produto> putAdicionarProdutoLista(@PathVariable long idProduto, @PathVariable long idLista) {
		
		return ResponseEntity.ok(service.adicionaProduto(idProduto, idLista));
	}
	
	@PutMapping("/deleta/tabela_produtos/produtos/{idProduto}/lista/{idLista}")
	public void putRemoverProdutoLista(@PathVariable long idProduto, @PathVariable long idLista) {
		
		service.deletarProduto(idProduto, idLista);
	}
	
	@DeleteMapping("/{id}")
	public void deleteProduto(@PathVariable long id) {
		
		repository.deleteById(id);
	}

}
