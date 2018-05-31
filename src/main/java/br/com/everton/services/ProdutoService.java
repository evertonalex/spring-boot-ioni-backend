package br.com.everton.services;

import br.com.everton.domain.Categoria;
import br.com.everton.domain.Produto;
import br.com.everton.repositories.CategoriaRepository;
import br.com.everton.repositories.ProdutoRepository;
import br.com.everton.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo;

	@Autowired
	private CategoriaRepository categoriaRepository;

	public Produto find(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto nao encontrado! id:" + id + ", Tipo: " + Produto.class.getName()));
	}

	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

		List<Categoria> categorias = categoriaRepository.findAllById(ids);

		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);

	}

}
