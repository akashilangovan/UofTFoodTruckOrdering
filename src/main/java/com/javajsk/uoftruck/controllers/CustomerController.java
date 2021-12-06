package com.javajsk.uoftruck.controllers;

import adapters.dam.SHA512Hasher;
import adapters.dam.entityrepoitories.CustomerDB;
import adapters.dam.entityrepoitories.FoodDB;
import businessrules.customer.inputboundaries.CustomerLogin;
import businessrules.customer.inputboundaries.CustomerSignUp;
import businessrules.customer.inputboundaries.ModifyCustomer;
import businessrules.customer.usecases.CustomerLoginInteractor;
import businessrules.customer.usecases.CustomerSignUpInteractor;
import businessrules.customer.usecases.ModifyCustomerInteractor;
import businessrules.dai.CustomerRepository;
import businessrules.dai.Hasher;
import businessrules.dai.Repository;
import businessrules.outputboundaries.*;
import entities.Customer;
import entities.Food;
import framework.MongoDB;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import presenters.CustomerPresenter;
import presenters.ObjectPresenter;
import presenters.RepositoryPresenter;
import presenters.VendorPresenter;

import java.security.Permission;

@RestController
public class CustomerController {

    CustomerLogin customerLogin;
    CustomerSignUp customerSignUp;
    ModifyCustomer modifyCustomer;
    MongoDB db;
    Repository<Food> foodRepository;
    CustomerRepository customerRepository;
    VendorBoundary vendorBoundary;
    CustomerBoundary customerBoundary;
    RepositoryBoundary repositoryBoundary;
    Hasher hasher;
    ObjectBoundary<Customer> customerObjectBoundary;


    public CustomerController() {
        customerObjectBoundary = new ObjectPresenter<Customer>();
        hasher = new SHA512Hasher();
        repositoryBoundary = new RepositoryPresenter();
        customerBoundary = new CustomerPresenter();
        vendorBoundary = new VendorPresenter();
        db = new MongoDB();
        customerRepository = new CustomerDB(db);
        foodRepository = new FoodDB(db);
        this.customerLogin = new CustomerLoginInteractor(customerRepository, customerBoundary, repositoryBoundary,hasher);
        this.customerSignUp = new CustomerSignUpInteractor(customerRepository, repositoryBoundary, customerBoundary, customerObjectBoundary, hasher);
        this.modifyCustomer = new ModifyCustomerInteractor(customerRepository, customerObjectBoundary, repositoryBoundary, customerBoundary, hasher);

    }

    @PutMapping("/CustomerLogin/{username}/{password}")
    public ResponseObject runCustomerLogin(@PathVariable String username, @PathVariable String password){
        return customerLogin.login(username, password);
    }
    @PutMapping("/CustomerSignup/{username}/{password}/{confirmed_password}")
    public ResponseObject runCustomerSignup(@PathVariable String username, @PathVariable String password,
                                      @PathVariable String confirmed_password){
        return customerSignUp.signUp(username, password, confirmed_password);
    }
    @PutMapping("/ModifyCustomer/{userToken}/{username}/{password}/{confirmed_password}")
    public ResponseObject runModifyCustomer(@PathVariable String username, @PathVariable String password,
                                      @PathVariable String confirmed_password, @PathVariable String userToken){
        return modifyCustomer.modify(userToken, username, password, confirmed_password);

    }
}
