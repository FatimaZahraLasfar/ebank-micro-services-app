package org.fzl.ebankservice.services;

import org.fzl.ebankservice.entities.BankAccount;
import org.fzl.ebankservice.repository.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class EbankService {
    private CustomerRestClient customerRestClient;
    private BankAccountRepository accountRepository;

    public EbankService(BankAccountRepository accountRepository, CustomerRestClient customerRestClient){
        this.accountRepository = accountRepository;
    }

    public List<BankAccount> getAllBankAccounts(){
        return accountRepository.findAll();
    }

    public BankAccount getBankAccountById(String id){
        return accountRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Account Not Found"));

    }
    public BankAccount save(BankAccount bankAccount){
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setCreatedAt(new Date());
        return accountRepository.save(bankAccount);
    }
}
