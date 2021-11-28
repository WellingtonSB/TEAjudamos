package com.grupo34.Teapoio.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.grupo34.Teapoio.domain.Postagem;
import com.grupo34.Teapoio.domain.Usuario;
import com.grupo34.Teapoio.dto.PostagemDTO;
import com.grupo34.Teapoio.dto.UsuarioDTO;
import com.grupo34.Teapoio.repository.PostagemRepository;
import com.grupo34.Teapoio.services.PostagemService;


@RestController
@RequestMapping(value = "/post")
public class PostagemResource {

	@Autowired
	private PostagemService service;

	@Autowired
	private PostagemRepository repository;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Postagem> GetById(@PathVariable int id) {
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}

	@RequestMapping(value ="/find/{id}",method = RequestMethod.GET)
	public ResponseEntity<Optional<Postagem>> findUser(@PathVariable int id) {
		Optional<Postagem> list = repository.findById(id);
		List<PostagemDTO>listDTO = list.stream().map(obj-> new PostagemDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(list);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<PostagemDTO>> findAll() {
		List<Postagem> list = service.findAll();
		List<PostagemDTO> listDto = list.stream().map(obj -> new PostagemDTO(obj))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	//http://localhost:8080/registros/page?linesPerPage=1
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<PostagemDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Postagem> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<PostagemDTO> listDto = list.map(obj -> new PostagemDTO(obj));
		return ResponseEntity.ok().body(listDto);
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Postagem obj) {
		service.registrar(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody PostagemDTO objDto, @PathVariable Integer id) {
		Postagem obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}