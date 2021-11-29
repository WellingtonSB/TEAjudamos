package com.grupo34.Teapoio.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.grupo34.Teapoio.domain.Usuario;
import com.grupo34.Teapoio.domain.enums.Perfil;
import com.grupo34.Teapoio.domain.enums.TipoUsuario;
import com.grupo34.Teapoio.dto.UsuarioDTO;
import com.grupo34.Teapoio.dto.UsuarioNewDTO;
import com.grupo34.Teapoio.repository.PostagemRepository;
import com.grupo34.Teapoio.repository.UsuarioRepository;
import com.grupo34.Teapoio.security.UserSS;
import com.grupo34.Teapoio.services.exception.AuthorizationException;
import com.grupo34.Teapoio.services.exception.DataIntegrityException;
import com.grupo34.Teapoio.services.exception.ObjectNotFoundException;


@Service
public class UsuarioService {

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private S3Service s3Service;

	@Autowired
	private ImageService imageService;

	@Value("${img.prefix.client.profile}")
	private String prefix;

	@Autowired
	private PostagemRepository postagemRepository;

	public Usuario find(Integer id) throws ObjectNotFoundException {

		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}

		Optional<Usuario> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName()));
	}

	public List<Usuario> findAll() {
		return repository.findAll();
	}

	public Usuario findByEmail(String email) throws ObjectNotFoundException {
		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}

		Usuario obj = repository.findByEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Usuario.class.getName());
		}
		return obj;
	}
	
	
	@Transactional
	public Usuario insert(Usuario obj) {
		obj.setId(null);
		obj = repository.save(obj);
		postagemRepository.saveAll(obj.getPostagens());
		return obj;
	}

	public Usuario update(Usuario obj) throws ObjectNotFoundException {
		Usuario newObj = find(obj.getId());
		updateData(newObj, obj);
		return repository.save(newObj);
	}

	public void delete(Integer id) throws ObjectNotFoundException {
		find(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há registros  relacionados");
		}
	}

	public Usuario fromDTO(UsuarioDTO objDto) {
		return new Usuario(objDto.getId(), objDto.getNome(), objDto.getEmail(),null, objDto.getLikes(), null,objDto.getcrmCrp());
	}

	public Usuario fromDTO(UsuarioNewDTO objDto) {
		Usuario cli = new Usuario(null, objDto.getNome(), objDto.getEmail(), pe.encode(objDto.getSenha()), 0, TipoUsuario.toEnum(objDto.getTipo()),objDto.getcrmCrp());
		cli.getTelefones().add(objDto.getTelefone());
		if (objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		return cli;
	}

	private void updateData(Usuario newObj, Usuario obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		newObj.setLikes(obj.getLikes());
	}

	public URI uploadProfilePicture(MultipartFile multipartFile) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		String fileName = prefix + user.getId() + ".jpg";

		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
	}

}
