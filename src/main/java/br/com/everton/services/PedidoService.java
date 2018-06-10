package br.com.everton.services;

import java.util.Date;
import java.util.Optional;

import br.com.everton.domain.ItemPedido;
import br.com.everton.domain.PagamentoComBoleto;
import br.com.everton.domain.enums.EstadoPagamento;
import br.com.everton.repositories.ItemPedidoRepository;
import br.com.everton.repositories.PagamentoRepository;
import br.com.everton.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.everton.domain.Pedido;
import br.com.everton.repositories.PedidoRepository;
import br.com.everton.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private EmailService emailService;

	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto nao encontrado! id:" + id + ", Tipo: " + Pedido.class.getName()));
	}

	public Pedido insert(Pedido obj){
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PEDENTE);
		obj.getPagamento().setPedido(obj);

		if(obj.getPagamento() instanceof PagamentoComBoleto){
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());

		for(ItemPedido ip : obj.getItens()){
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());

		System.out.println(obj);
		emailService.sendOrderConfirmationEmail(obj);
		return obj;
	}

}
