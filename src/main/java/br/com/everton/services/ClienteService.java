package br.com.everton.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import br.com.everton.domain.enums.Perfil;
import br.com.everton.security.UserSS;
import br.com.everton.services.exceptions.AutorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.everton.domain.Cidade;
import br.com.everton.domain.Cliente;
import br.com.everton.domain.Endereco;
import br.com.everton.domain.enums.TipoCliente;
import br.com.everton.dto.ClienteDTO;
import br.com.everton.dto.ClienteNewDTO;
import br.com.everton.repositories.CidadeRepository;
import br.com.everton.repositories.ClienteRepository;
import br.com.everton.repositories.EnderecoRepository;
import br.com.everton.services.exceptions.DataIntegrityException;
import br.com.everton.services.exceptions.ObjectNotFoundException;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ClienteService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private S3Service s3Service;

	@Autowired
	private ImageService imageService;

	@Value("${img.prefix.client.profile}")
	private String prefix;

	@Value("${img.profile.size}")
	private Integer size;

	public Cliente find(Integer id) {

		UserSS user = UserService.authenticated();
		if(user == null || !user.hasRole(Perfil.ADMIN)&& !id.equals(user.getId())){
			throw new AutorizationException("Acesso negado");
		}

		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto nao encontrado! id:" + id + ", Tipo: " + Cliente.class.getName()));
	}

	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		
		return obj;
	}

	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);	
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Nao é possivel excluir Clientes que possuam produtos");
		}
		
	}
	
	public List<Cliente> findAll(){
		return repo.findAll();
	}

	public Cliente findByEmail(String email){
	    UserSS user = UserService.authenticated();
	    if(user==null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())){
	        throw new AutorizationException("Acesso negado");
        }

        Cliente obj = repo.findByEmail(email);
	    if(obj == null){
	        throw new ObjectNotFoundException("Objeto não encontrado! id: " + user.getId() + ", Tipo: " + Cliente.class.getName());
        }
	    return obj;
    }
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipo()), bCryptPasswordEncoder.encode(objDTO.getSenha()));
		Cidade cid = cidadeRepository.getOne(objDTO.getCidadeID());
		
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		
		cli.getTelefones().add(objDTO.getTelefone1());
		if(objDTO.getTelefone2() != null) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		if(objDTO.getTelefone3() != null) {
			cli.getTelefones().add(objDTO.getTelefone3());
		}
		
		return cli;
	}

	private void updateData(Cliente newObj, Cliente obj) {
		obj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

	public URI uploadProfilePicture(MultipartFile multipartFile){

		UserSS user = UserService.authenticated();
		if(user == null){
			throw new AutorizationException("Acesso negado");
		}

		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);

		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);


		String fileName = prefix + user.getId()+".jpg";

		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"),fileName, "image" );
//
//		URI uri = s3Service.uploadFile(multipartFile);
//		Cliente cli= repo.getOne(user.getId());
//		cli.setImageUrl(uri.toString());
//		repo.save(cli);
//
//		return s3Service.uploadFile(multipartFile);
	}

}
