package br.com.everton.config;

import br.com.everton.services.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DbService dbService;
    @Bean
    public boolean instantiateDatabase() throws ParseException {
        dbService.instantiateTestDatabse();
        return true;
    }
}
