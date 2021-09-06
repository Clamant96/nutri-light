package com.nutrilight.nutriLight.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "produtos")
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	private String nome;
	
	@NotNull
	private double kg;
	
	@NotNull
	private String img;
	
	@ManyToOne
	@JsonIgnoreProperties("produtos")
	private Categoria categoria;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
	  name = "tabela_produtos",
	  joinColumns = @JoinColumn(name = "produto_id"),
	  inverseJoinColumns = @JoinColumn(name = "lista_id")
	)
	@JsonIgnoreProperties({"usuario", "produtos"})
	private List<Lista> listas = new ArrayList<>();
	
	@OneToMany(mappedBy = "produto", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("produto")
	private List<Mensagem> mensagens;
	
	@ManyToOne
	@JsonIgnoreProperties("produtos")
	private Usuario usuario;
	
	@ManyToMany(mappedBy = "likeProduto", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"nome", "idade", "peso", "username", "senha", "altura", "foto", "imc", "lista", "produtos", "likeProduto"})
	private List<Usuario> like = new ArrayList<>();
	
	private String categoriaTipoIMC;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getKg() {
		return kg;
	}

	public void setKg(double kg) {
		this.kg = kg;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<Lista> getListas() {
		return listas;
	}

	public void setListas(List<Lista> listas) {
		this.listas = listas;
	}

	public List<Mensagem> getMensagens() {
		return mensagens;
	}

	public void setMensagens(List<Mensagem> mensagens) {
		this.mensagens = mensagens;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getLike() {
		return like;
	}

	public void setLike(List<Usuario> like) {
		this.like = like;
	}

	public String getCategoriaTipoIMC() {
		return categoriaTipoIMC;
	}

	public void setCategoriaTipoIMC(String categoriaTipoIMC) {
		this.categoriaTipoIMC = categoriaTipoIMC;
	}
	
}
