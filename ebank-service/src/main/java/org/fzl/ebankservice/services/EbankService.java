package org.fzl.ebankservice.services;

import org.fzl.ebankservice.entities.BankAccount;
import org.fzl.ebankservice.repository.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EbankService {
    private BankAccountRepository accountRepository;

    public EbankService(BankAccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public List<BankAccount> getAllBankAccounts(){
        return accountRepository.findAll();
    }

    public BankAccount getBankAccountById(String id){
        return accountRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Account Not Found"));

    }
    public BankAccount saveBankAccount(BankAccount bankAccount){
        return accountRepository.save(bankAccount);
    }
}
