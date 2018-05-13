package br.com.everton;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mysql.fabric.xmlrpc.base.Array;

import br.com.everton.domain.Categoria;
import br.com.everton.domain.Cidade;
import br.com.everton.domain.Cliente;
import br.com.everton.domain.Endereco;
import br.com.everton.domain.Estado;
import br.com.everton.domain.Produto;
import br.com.everton.domain.enums.TipoCliente;
import br.com.everton.repositories.CategoriaRepository;
import br.com.everton.repositories.CidadeRepository;
import br.com.everton.repositories.ClienteRepository;
import br.com.everton.repositories.EnderecoRepository;
import br.com.everton.repositories.EstadoRepository;
import br.com.everton.repositories.ProdutoRepository;

@SpringBootApplication
public class CursoMcApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursoMcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "teste");
		Categoria cat2 = new Categoria(null, "test2");
		
		Produto p1 = new Produto(null, "computador",2000.00);
		Produto p2 = new Produto(null, "Impressora",800.00);
		Produto p3 = new Produto(null, "Mouse",80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		//categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		//produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "minas gerais");
		Estado est2 = new Estado(null, "sao paulo");
		
		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		//estadoRepository.saveAll(Arrays.asList(est1, est2));
		//cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@email.com", "4334290000",TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("123456", "7891347"));
		
		Endereco e1 = new Endereco(null, "Rua nome da rua", "123", "casa", "bairro", "86741147", cli1, c1);
		Endereco e2 = new Endereco(null, "Av avenida 1", "124", "casa", "centro", "86741147", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		//clienteRepository.saveAll(Arrays.asList(cli1));
		//enderecoRepository.saveAll(Arrays.asList(e1, e2));
	}
}
