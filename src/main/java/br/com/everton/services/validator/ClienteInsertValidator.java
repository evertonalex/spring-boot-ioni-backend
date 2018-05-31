package br.com.everton.services.validator;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.everton.domain.Cliente;
import br.com.everton.domain.enums.TipoCliente;
import br.com.everton.dto.ClienteNewDTO;
import br.com.everton.repositories.ClienteRepository;
import br.com.everton.resources.exception.FieldMessage;
import br.com.everton.services.validator.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}
		
	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		
		/*if(objDto.getTipo() == null) {
			list.add(new FieldMessage("Tipo", "Tipo nao pode ser nulo"));
		}*/
		
		System.out.println("VALIDANDO @ANOTATION PERSONALIZADA");
		
		if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}
		
		if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		
		System.out.println("EMAIL " + objDto.getEmail());
		
		Cliente aux = repo.findByEmail(objDto.getEmail());
		
		System.out.println("AUX" + aux);
		
		if(aux != null) {
			System.out.println("EMAIL JA EXISTENTE");
			list.add(new FieldMessage("email", "Email já existente"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFiledName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}