package com.grupo34.Teapoio.dto;

import java.io.Serializable;

import com.grupo34.Teapoio.domain.Comentarios;
import com.grupo34.Teapoio.domain.Postagem;

public class ComentarioDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String text;
	
	public ComentarioDTO() {
	}

	public ComentarioDTO(Comentarios obj) {
		this.id = obj.getId();
		this.text = obj.getText();
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
	
	
}
