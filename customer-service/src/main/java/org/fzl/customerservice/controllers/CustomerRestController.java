package org.fzl.customerservice.controllers;

import org.fzl.customerservice.entities.Customer;
import org.fzl.customerservice.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerRestController {
    private CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/Customers")
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }
    @GetMapping("/Customers/{id}")
    public Customer findCustomerById(@PathVariable Long id){
        return customerService.findCustomerById(id);
    }
    @PostMapping("/customers")
    public Customer saveCustomer (@RequestBody Customer customer){
        return customerService.saveCustomer(customer);
    }
}
