package br.com.everton.config;

import br.com.everton.services.DbService;
import br.com.everton.services.EmailService;
import br.com.everton.services.SmtpEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("prod")
public class ProdConfig {

    @Autowired
    private DbService dbService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;
    @Bean
    public boolean instantiateDatabase() throws ParseException {

        if(!"create".equals(strategy)){
            return false;
        }
        dbService.instantiateTestDatabse();
        return true;
    }

//    @Bean
//    public EmailService emailService(){
//        return new SmtpEmailService();
//    }

//    @Bean
//    public EmailService emailService(){
//        return new MockEmailService();
//    }
}
