package com.grupo34.Teapoio.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.grupo34.Teapoio.domain.Comentarios;
import com.grupo34.Teapoio.domain.Postagem;
import com.grupo34.Teapoio.dto.ComentarioDTO;
import com.grupo34.Teapoio.dto.PostagemDTO;
import com.grupo34.Teapoio.repository.ComentarioRepository;
import com.grupo34.Teapoio.services.ComentarioService;


@RestController
@RequestMapping(value = "/comment")
public class ComentarioResource {
	
	@Autowired
	private ComentarioService service;
	
	@Autowired
	private ComentarioRepository repository;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Comentarios> GetById(@PathVariable int id) {
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}
	
	@RequestMapping(value ="/find/{id}",method = RequestMethod.GET)
	public ResponseEntity<Optional<Comentarios>> findUser(@PathVariable int id) {
		Optional<Comentarios> list = repository.findById(id);
		List<ComentarioDTO>listDTO = list.stream().map(obj-> new ComentarioDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(list);
	}
	

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ComentarioDTO>> findAll() {
		List<Comentarios> list = service.fildAll();
		List<ComentarioDTO> listDto = list.stream().map(obj -> new ComentarioDTO(obj))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Comentarios obj) {
		service.criar(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ComentarioDTO objDto, @PathVariable Integer id) {
		Comentarios obj = service.fromDTO(objDto);
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
