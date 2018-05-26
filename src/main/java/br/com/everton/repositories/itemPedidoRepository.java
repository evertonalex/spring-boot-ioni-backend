package br.com.everton.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.everton.domain.ItemPedido;

@Repository
public interface itemPedidoRepository extends JpaRepository<ItemPedido, Integer>{
	
}
