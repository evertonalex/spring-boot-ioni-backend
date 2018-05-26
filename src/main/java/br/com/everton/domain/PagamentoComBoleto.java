package br.com.everton.domain;

import java.util.Date;

import javax.persistence.Entity;

import br.com.everton.domain.enums.EstadoPagamento;

@Entity
public class PagamentoComBoleto extends Pagamento{
	private static final long serialVersionUID = 1L;
	private Date dataVendimento;
	private Date dataPagamento;
	
	public PagamentoComBoleto() {
		
	}

	public Date getDataVendimento() {
		return dataVendimento;
	}

	public void setDataVendimento(Date dataVendimento) {
		this.dataVendimento = dataVendimento;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public PagamentoComBoleto(Integer id, EstadoPagamento estado, Pedido pedido, Date dataVencimento, Date dataPagamento) {
		super(id, estado, pedido);
		this.dataPagamento = dataPagamento;
		this.dataVendimento = dataVencimento;
	}
	
}
