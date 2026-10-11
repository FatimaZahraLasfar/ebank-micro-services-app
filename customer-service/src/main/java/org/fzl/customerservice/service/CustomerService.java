package org.fzl.customerservice.service;

import org.fzl.customerservice.entities.Customer;
import org.fzl.customerservice.repository.CustomerRepository;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @McpTool(description = "Get ALL customers")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @McpTool(description = "Find customer by id")
    public Customer findCustomerById(
            @McpToolParam(description = "The customer's id") Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer Not Found"));
    }

    @McpTool(description = "Save a new customer")
    public Customer saveCustomer(
            @McpToolParam(description = "The Customer to save (name, email)") Customer customer) {
        return customerRepository.save(customer);
    }
}