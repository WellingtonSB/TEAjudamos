package com.grupo34.Teapoio.dto;

import java.io.Serializable;
import java.util.Date;

import com.grupo34.Teapoio.domain.Postagem;

public class PostagemDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String content;

	private Date dateTime = new Date(System.currentTimeMillis());

	public PostagemDTO() {
	}

	public PostagemDTO(Postagem obj) {
		this.id = obj.getId();
		this.content = obj.getContent();
		this.dateTime = obj.getDateTime();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

}
