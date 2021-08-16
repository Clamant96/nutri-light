package com.nutrilight.nutriLight.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nutrilight.nutriLight.model.Lista;
import com.nutrilight.nutriLight.model.Produto;
import com.nutrilight.nutriLight.repository.ListaRepository;

@Service
public class ListaService {
	
	@Autowired
	private ListaRepository listaRepository;
	
	/* PESQUISANDO POR PRODUTO ESPECIFICO EM UMA LISTA DE DESEJOS DE PRODUTOS */
	public List<Produto> pesquisaPorProdutoNaListaDeDesejos(long idListaUsuario) {
		Optional<Lista> listaDeDesejoExistente = listaRepository.findById(idListaUsuario);
		
		if(listaDeDesejoExistente.isPresent()) {
			listaDeDesejoExistente.get().getProdutos();
			
			return listaRepository.save(listaDeDesejoExistente.get()).getProdutos();
			
		}
		
		return null;
		
	}

}
