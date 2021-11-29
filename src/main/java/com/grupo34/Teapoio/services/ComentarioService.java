package com.grupo34.Teapoio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.grupo34.Teapoio.domain.Comentarios;
import com.grupo34.Teapoio.dto.ComentarioDTO;
import com.grupo34.Teapoio.repository.ComentarioRepository;
import com.grupo34.Teapoio.services.exception.DataIntegrityException;

@Service
public class ComentarioService {

	@Autowired
	private ComentarioRepository repo;

	public Comentarios find(Integer id) {
		Optional<Comentarios> obj = repo.findById(id);
		return obj.orElse(null);
	}

	public List<Comentarios> fildAll() {
		return repo.findAll();
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir");
		}
	}

	public Comentarios update(Comentarios obj) {
		Comentarios newObj = find(obj.getId());
		updateData(newObj,obj);
		return repo.save(newObj);
	}

	private void updateData(Comentarios newObj, Comentarios obj) {
		newObj.setText(obj.getText());
	}

	public Comentarios fromDTO(ComentarioDTO objDto) {
		return new Comentarios(objDto.getId(),objDto.getText());
	}
	
	public void criar (Comentarios post) {
		repo.save(post);
	}
}
