package com.grupo34.Teapoio.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "comentarios")
public class Comentarios implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String text;

	@Temporal(TemporalType.TIMESTAMP)
	private Date data = new Date(System.currentTimeMillis());

	@ManyToOne
	@JsonIgnoreProperties(value = { "postagem", "comentario" })
	private Usuario usuario;

	@ManyToOne
	@JsonIgnoreProperties(value = { "usuario", "comentario" })
	private Postagem postagem;

	
	public Comentarios() {

	}
	
	public Comentarios(Integer id, String text) {
		this.id = id;
		this.text=text;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Postagem getPostagem() {
		return postagem;
	}

	public void setPostagem(Postagem postagem) {
		this.postagem = postagem;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comentarios other = (Comentarios) obj;
		return Objects.equals(id, other.id);
	}

}
