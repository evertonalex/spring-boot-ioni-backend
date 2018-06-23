package br.com.everton;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import br.com.everton.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.everton.domain.Categoria;
import br.com.everton.domain.Cidade;
import br.com.everton.domain.Cliente;
import br.com.everton.domain.Endereco;
import br.com.everton.domain.Estado;
import br.com.everton.domain.ItemPedido;
import br.com.everton.domain.Pagamento;
import br.com.everton.domain.PagamentoComBoleto;
import br.com.everton.domain.PagamentoComCartao;
import br.com.everton.domain.Pedido;
import br.com.everton.domain.Produto;
import br.com.everton.domain.enums.EstadoPagamento;
import br.com.everton.domain.enums.TipoCliente;
import br.com.everton.repositories.CategoriaRepository;
import br.com.everton.repositories.CidadeRepository;
import br.com.everton.repositories.ClienteRepository;
import br.com.everton.repositories.EnderecoRepository;
import br.com.everton.repositories.EstadoRepository;
import br.com.everton.repositories.PagamentoRepository;
import br.com.everton.repositories.PedidoRepository;
import br.com.everton.repositories.ProdutoRepository;
import br.com.everton.repositories.ItemPedidoRepository;

@SpringBootApplication
public class CursoMcApplication implements CommandLineRunner{

	@Autowired
	private S3Service s3Service;
	
	public static void main(String[] args) {

		SpringApplication.run(CursoMcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		s3Service.uploadFile("C:\\Users\\everton_alex\\Desktop\\testeS3\\s3Teste.pdf");
	}
}
