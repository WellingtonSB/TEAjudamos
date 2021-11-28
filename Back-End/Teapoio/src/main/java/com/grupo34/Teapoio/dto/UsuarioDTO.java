package com.grupo34.Teapoio.dto;

import java.io.Serializable;
import java.util.Set;

import com.grupo34.Teapoio.domain.Usuario;

public class UsuarioDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private String email;
	private String crmCrp;
	private Integer likes;

	public UsuarioDTO() {

	}

	public UsuarioDTO(Usuario obj) {
		this.id = obj.getId();
		this.crmCrp = obj.getcrmCrp();
		this.nome = obj.getNome();
		this.email = obj.getEmail();
		this.likes = obj.getLikes();
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

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public String getcrmCrp() {
		return crmCrp;
	}

	public void setcrmCrp(String crmCrp) {
		this.crmCrp = crmCrp;
	}

}
