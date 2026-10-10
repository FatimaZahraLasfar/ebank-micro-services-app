package org.fzl.ebankservice;

import org.fzl.ebankservice.entities.BankAccount;
import org.fzl.ebankservice.services.EbankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EbankServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(EbankService ebankService){
        return args->{
            for(int i= 0 ; i<= 3; i++){
                for(int j = 0; i<5; j++){
                    ebankService.save(BankAccount.builder()
                                    .type(Math.random()>0.5 ? "CURRENT-ACCOUNT" : "SAVING-ACCOUNT")
                                    .balance(1000+Math.random()*60000)
                                    .customerId(i)
                            .build());
                }
            }
        };
    }

}
