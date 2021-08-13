package com.nutrilight.nutriLight.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nutrilight.nutriLight.model.Lista;
import com.nutrilight.nutriLight.model.Produto;
import com.nutrilight.nutriLight.repository.ListaRepository;
import com.nutrilight.nutriLight.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ListaRepository listaRepository;
	
	/* ADICIONA OBJETOS NA LISTA */
	public Produto adicionaProduto(long idProduto, long idLista) {
		
		Optional<Produto> produtoExistente = produtoRepository.findById(idProduto);
		Optional<Lista> listaExistente = listaRepository.findById(idLista);
		
		/* SE O 'PRODUTO' E 'LISTA' EXISTIREM E O PRODUTO A SER INSEIDO AINDA NAO EXISTIR NA LISTA OU VICE VERSA ENTRA NA CONDICAO */
		if(produtoExistente.isPresent() && listaExistente.isPresent() && !(produtoExistente.get().getListas().contains(listaExistente.get().getId()))) {
			
			/* ADICIONA O PRODUTO AO CARRINHO DO USUARIO */
			produtoExistente.get().getListas().add(listaExistente.get());
			
			System.out.println("Retorno: "+ listaExistente.get().getProdutos().contains(produtoExistente.get()));
			
			System.out.println("QTD produtos "+ listaExistente.get().getProdutos().size());
			
			produtoRepository.save(produtoExistente.get());
			listaRepository.save(listaExistente.get());
			
			return produtoRepository.save(produtoExistente.get());
			
		}
		
		return null;
	}
	
	/* DELETAR OBJETOS DA LISTA */
	public void deletarProduto(long idProduto, long idLista) {

		Optional<Produto> produtoExistente = produtoRepository.findById(idProduto);
		Optional<Lista> listaExistente = listaRepository.findById(idLista);
		
		if(listaExistente.get().getProdutos().contains(produtoExistente.get())) {
			/* REMOVE A LISTA DO PRODUTO */
			produtoExistente.get().getListas().remove(listaExistente.get());
			
			produtoRepository.save(produtoExistente.get());
			listaRepository.save(listaExistente.get());
			
		}
		
	}

}
