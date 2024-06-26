package br.com.primamed.customers.usecases

import br.com.primamed.customers.adapters.controllers.dto.CustomerDto
import br.com.primamed.customers.adapters.controllers.dto.NewCustomerDto
import br.com.primamed.customers.adapters.repositories.entities.Customer
import java.util.*

interface CustomerUsecase {


    fun saveCustomer(newCustomerDto: NewCustomerDto): Customer
    fun getCustomerByRegistration(document: String, transactionId: String): Optional<Customer>
    fun addCustomerAddress(newCustomerDto: NewCustomerDto, customer: Customer)
    fun getAll(): List<CustomerDto>


}