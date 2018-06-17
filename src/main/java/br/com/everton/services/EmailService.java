package br.com.everton.services;

import br.com.everton.domain.Cliente;
import br.com.everton.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    void sendOrderConfirmationEmail(Pedido obj);
    void sendEmail(SimpleMailMessage msg);
    void sendNewpasswordEmail(Cliente cliente, String newPass);
}
