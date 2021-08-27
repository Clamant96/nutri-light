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
	
	/* CONTADOR DE PROTEINAS NA LISTA/PRODUTO */
	int contador = 0;
	
	/* ADICIONA OBJETOS NA LISTA */
	public Produto adicionaProduto(long idProduto, long idLista) {
		
		Optional<Produto> produtoExistente = produtoRepository.findById(idProduto);
		Optional<Lista> listaExistente = listaRepository.findById(idLista);
		
		/* SE O 'PRODUTO' E 'LISTA' EXISTIREM E O PRODUTO A SER INSEIDO AINDA NAO EXISTIR NA LISTA OU VICE VERSA ENTRA NA CONDICAO */
		if(produtoExistente.isPresent() && listaExistente.isPresent() && !(produtoExistente.get().getListas().contains(listaExistente.get().getId()))) {
			
			System.out.println("Contador: "+ contador);
			System.out.println("ID: "+ produtoExistente.get().getCategoria().getId());
			
			switch((int) produtoExistente.get().getCategoria().getId()) {
				case 1:
					System.out.println("MENU | PROTEINAS");
					
					/** 
					 * NAVEGA ENTRE O PRODUTO E VERIFICA SE TEM NO MAXIMO 2 PROTEINAS 
					 * CASO TENHA MAIS DE DUAS PROTEINAS, NAO E INSERIDO MAIS UM NA LISTA, POIS O MAXIMO SAO 2
					 * */
					for(int i = 0; i < listaExistente.get().getProdutos().size(); i++) {
						if(listaExistente.get().getProdutos().get(i).getCategoria().getId() == 1) {
							contador++;
						}
						
					}
					
					System.out.println("Contagem contador: "+ contador);
					
					/* ADICIONA O PRODUTO A LISTA DO USUARIO */
					if(contador < 2 && !(listaExistente.get().getProdutos().contains(produtoExistente.get()))) {
						produtoExistente.get().getListas().add(listaExistente.get());
						
					}else {
						System.out.println("Voce ja tem a quantidade maxima de produtos dessa categoria!");
						
					}
					
				break;
				
				case 2:
					System.out.println("MENU | CARBOIDRATOS");
					
					/** 
					 * NAVEGA ENTRE O PRODUTO E VERIFICA SE TEM NO MAXIMO 2 PROTEINAS 
					 * CASO TENHA MAIS DE DUAS PROTEINAS, NAO E INSERIDO MAIS UM NA LISTA, POIS O MAXIMO SAO 3
					 * */
					for(int i = 0; i < listaExistente.get().getProdutos().size(); i++) {
						if(listaExistente.get().getProdutos().get(i).getCategoria().getId() == 2) {
							contador++;
						}
						
					}
					
					System.out.println("Contagem contador: "+ contador);
					
					/* ADICIONA O PRODUTO A LISTA DO USUARIO */
					if(contador < 3 && !(listaExistente.get().getProdutos().contains(produtoExistente.get()))) {
						produtoExistente.get().getListas().add(listaExistente.get());
						
					}else {
						System.out.println("Voce ja tem a quantidade maxima de produtos dessa categoria!");	
						
					}
					
				break;
				
				case 3:
					System.out.println("MENU | VERDURAS");
					
					/** 
					 * NAVEGA ENTRE O PRODUTO E VERIFICA SE TEM NO MAXIMO 2 PROTEINAS 
					 * CASO TENHA MAIS DE DUAS PROTEINAS, NAO E INSERIDO MAIS UM NA LISTA, POIS O MAXIMO SAO 5
					 * */
					for(int i = 0; i < listaExistente.get().getProdutos().size(); i++) {
						if(listaExistente.get().getProdutos().get(i).getCategoria().getId() == 3) {
							contador++;
						}
						
					}
					
					/* ADICIONA O PRODUTO A LISTA DO USUARIO */
					if(contador < 5 && !(listaExistente.get().getProdutos().contains(produtoExistente.get()))) {
						produtoExistente.get().getListas().add(listaExistente.get());
						
					}else {
						System.out.println("Voce ja tem a quantidade maxima de produtos dessa categoria!");
						
					}
					
				break;
				
				case 4:
					System.out.println("MENU | FRUTAS");
					
					/** 
					 * NAVEGA ENTRE O PRODUTO E VERIFICA SE TEM NO MAXIMO 2 PROTEINAS 
					 * CASO TENHA MAIS DE DUAS PROTEINAS, NAO E INSERIDO MAIS UM NA LISTA, POIS O MAXIMO SAO 3
					 * */
					for(int i = 0; i < listaExistente.get().getProdutos().size(); i++) {
						if(listaExistente.get().getProdutos().get(i).getCategoria().getId() == 4) {
							contador++;
						}
						
					}
					
					/* ADICIONA O PRODUTO A LISTA DO USUARIO */
					if(contador < 3 && !(listaExistente.get().getProdutos().contains(produtoExistente.get()))) {
						produtoExistente.get().getListas().add(listaExistente.get());
						
					}else {
						System.out.println("Voce ja tem a quantidade maxima de produtos dessa categoria!");
						
					}
					
				break;
				
				default:
					System.out.println("Opcao invalida!");
					
				break;
			}
			
			produtoRepository.save(produtoExistente.get());
			listaRepository.save(listaExistente.get());
			
			contador = 0;
			
			return produtoRepository.save(produtoExistente.get());
			
		}
		
		contador = 0;
		
		return null;
	}
	
	/* EDITA PRODUTO (SOMENTE O USUARIO QUE CRIOU PODE REALIZAR ESSA ACAO) */
	public Produto editarProdutoDoUsuario(long idProduto, String username) {
		Optional<Produto> produtoExistente = produtoRepository.findById(idProduto);
		
		if(produtoExistente.get().getUsuario().getUsername().equals(username)) {
			produtoRepository.save(produtoExistente.get());
			
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
