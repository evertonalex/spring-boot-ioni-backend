package br.com.everton.repositories;

import br.com.everton.domain.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.everton.domain.Produto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{

    @Transactional(readOnly=true)
    /*@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE :nome AND cat IN :categorias")
    Page<Produto> findDistinctByNomeContainingAndCategoriasIn(@Param("nome") String nome,  @Param("categorias") List<Categoria> categorias, Pageable pageRequest);
*/
    /*EQUIVALENTE AO METODO ACIMA*/
    Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias, Pageable pageRequest);

}
