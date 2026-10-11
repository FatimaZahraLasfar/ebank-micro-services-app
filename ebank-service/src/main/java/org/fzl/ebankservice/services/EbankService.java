package org.fzl.ebankservice.services;

import org.fzl.ebankservice.entities.BankAccount;
import org.fzl.ebankservice.feign.CustomerRestClient;
import org.fzl.ebankservice.model.Customer;
import org.fzl.ebankservice.repository.BankAccountRepository;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
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
    @McpTool(description = "Get All Bank Accounts")
    public List<BankAccount> getAllBankAccounts(){
        return accountRepository.findAll();
    }
    @McpTool(description = "Get Bank Account By Id")
    public BankAccount getBankAccountById(@McpToolParam(description = "The bank account id") String id){
        BankAccount bankAccount = accountRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Account Not Found"));
        bankAccount.setCustomer(
                customerRestClient.getCustomerById(bankAccount.getCustomerId()));
        return bankAccount;
    }
    @McpTool(description = "Save a new bank account")
    public BankAccount save(@McpToolParam(description = "The bank account to save (balance,type,customerId) ") BankAccount bankAccount) {
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
