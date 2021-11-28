package com.grupo34.Teapoio.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.grupo34.Teapoio.domain.enums.Perfil;
import com.grupo34.Teapoio.domain.enums.TipoUsuario;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	@Column(unique = true)
	private String email;
	@JsonIgnore
	private String senha;
	private String crmCrp;
	private Integer tipo;
	private Integer likes;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("usuario")
	private List<Comentarios> comentario;
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("usuario")
	private List<Postagem> postagens = new ArrayList<>();
	
	// SET -> Conjunto(nao aceita repeticoes)
	@ElementCollection
	@CollectionTable(name = "TELEFONE")
	private Set<String> telefones = new HashSet<>();

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PERFIS")
	private Set<Integer> perfis = new HashSet<>();



	public Usuario() {
		addPerfil(Perfil.ADMIN);
	}

	public Usuario(Integer id, String nome, String email, String senha, Integer likes, TipoUsuario tipo,String crmCrp) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.crmCrp=crmCrp;
		this.likes = likes;
		this.tipo = (tipo == null) ? null : tipo.getCod();
		addPerfil(Perfil.ADMIN);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<Perfil> getPerfis() {
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}

	public void addPerfil(Perfil perfil) {
		perfis.add(perfil.getCod());
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}

	public TipoUsuario getTipo() {
		return TipoUsuario.toEnum(tipo);
	}

	public void setTipo(TipoUsuario tipo) {
		this.tipo = tipo.getCod();
	}

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public List<Postagem> getPostagens() {
		return postagens;
	}

	public void setPostagems(List<Postagem> postagens) {
		this.postagens = postagens;
	}

	public List<Comentarios> getComentario() {
		return comentario;
	}

	public void setComentario(List<Comentarios> comentario) {
		this.comentario = comentario;
	}

	public String getcrmCrp() {
		return crmCrp;
	}

	public void setcrmCrp(String crmCrp) {
		this.crmCrp = crmCrp;
	}

	
	
}