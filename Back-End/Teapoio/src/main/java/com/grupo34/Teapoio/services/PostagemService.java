package com.grupo34.Teapoio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.grupo34.Teapoio.domain.Postagem;
import com.grupo34.Teapoio.dto.PostagemDTO;
import com.grupo34.Teapoio.repository.PostagemRepository;
import com.grupo34.Teapoio.repository.UsuarioRepository;
import com.grupo34.Teapoio.services.exception.DataIntegrityException;

@Service
public class PostagemService {

	@Autowired
	private PostagemRepository repo;

	@Autowired
	private UsuarioRepository repoUser;

	public Postagem find(Integer id) {
		Optional<Postagem> obj = repo.findById(id);
		return obj.orElse(null);
	}

	public List<Postagem> findAll() {
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

	public Postagem update(Postagem obj) {
		Postagem newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public Postagem fromDTO(PostagemDTO objDto) {
		return new Postagem(objDto.getId(), objDto.getContent(), objDto.getDateTime());
	}

	private void updateData(Postagem newObj, Postagem obj) {

		newObj.setContent(obj.getContent());
		newObj.setDateTime(obj.getDateTime());
	}

	public void registrar(Postagem post) {
		repo.save(post);
	}

	public Page<Postagem> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

}