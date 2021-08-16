package com.nutrilight.nutriLight.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nutrilight.nutriLight.model.Lista;
import com.nutrilight.nutriLight.model.Produto;
import com.nutrilight.nutriLight.repository.ListaRepository;
import com.nutrilight.nutriLight.service.ListaService;

@RestController
@RequestMapping("/listas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ListaController {

	@Autowired
	private ListaRepository repository;
	
	@Autowired
	private ListaService service;
	
	@GetMapping
	public ResponseEntity<List<Lista>> getAllByListas() {
		
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Lista> getByIdLista(@PathVariable long id) {
		
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/listaProdutos/{idListaUsuario}")
	public ResponseEntity<List<Produto>> findAllByProdutosListaDeDesejos(@PathVariable long idListaUsuario) {
		
		return ResponseEntity.ok(service.pesquisaPorProdutoNaListaDeDesejos(idListaUsuario));
	}
	
}
