package org.fzl.ebankservice.services;

import org.fzl.ebankservice.entities.BankAccount;
import org.fzl.ebankservice.feign.CustomerRestClient;
import org.fzl.ebankservice.model.Customer;
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
        this.customerRestClient = customerRestClient;
    }

    public List<BankAccount> getAllBankAccounts(){
        return accountRepository.findAll();
    }

    public BankAccount getBankAccountById(String id){
        BankAccount bankAccount = accountRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Account Not Found"));
        bankAccount.setCustomer(
                customerRestClient.getCustomerById(bankAccount.getCustomerId()));
        return bankAccount;
    }
    public BankAccount save(BankAccount bankAccount) {
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setCreatedAt(new Date());
        try {
            customerRestClient.getCustomerById(bankAccount.getCustomerId());
        } catch (Exception e) {
            throw new RuntimeException("Customer " + bankAccount.getCustomerId()
                    + " not found, cannot create account", e);
        }
        return accountRepository.save(bankAccount);
    }
}
