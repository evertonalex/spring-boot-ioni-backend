package br.com.everton.services;

import br.com.everton.domain.Cliente;
import br.com.everton.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

public interface EmailService {
    void sendOrderConfirmationEmail(Pedido obj);
    void sendEmail(SimpleMailMessage msg);
    void sendNewpasswordEmail(Cliente cliente, String newPass);

    void sendOrderConfirmationHtmlEmail(Pedido obj);
    void sendHtmlEmail(MimeMessage msg);
}
