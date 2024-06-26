package br.com.primamed.customers.adapters.services

import br.com.primamed.customers.adapters.controllers.dto.CustomerDto
import br.com.primamed.customers.adapters.controllers.dto.DependentDto
import br.com.primamed.customers.adapters.controllers.dto.NewCustomerDto
import br.com.primamed.customers.adapters.controllers.dto.NewDependentDto
import br.com.primamed.customers.adapters.repositories.entities.Customer
import br.com.primamed.customers.exceptions.CustomerNotFoundException
import br.com.primamed.customers.usecases.CustomerUsecase
import br.com.primamed.customers.usecases.DependentsUsecase
import br.com.primamed.customers.utils.Mappers
import br.com.primamed.customers.utils.Mappers.Companion.getCustomerDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomerService {

    @Autowired
    lateinit var customersUsecase: CustomerUsecase

    @Autowired
    lateinit var dependentsUsecase: DependentsUsecase

    fun saveCustomer(customerDto: NewCustomerDto, transactionId: UUID): CustomerDto {
        val customer = customersUsecase.saveCustomer(customerDto)
        customersUsecase.addCustomerAddress(customerDto, customer)
        return getCustomerDto(customer)
    }

    fun saveDependent(registration: String, dependentDto: NewDependentDto, transactionId: UUID) {
        val customerByRegistration = customersUsecase.getCustomerByRegistration(registration, transactionId.toString())
        customerByRegistration.ifPresentOrElse(
            { customer ->
                dependentsUsecase.saveDependent(dependentDto, customer)
            },
            { throw CustomerNotFoundException("Customer not found") }
        )
    }

    fun getDependent(registration: String, dependentRegistration: String, transactionId: UUID): DependentDto {
        val customerByRegistration = customersUsecase.getCustomerByRegistration(registration, transactionId.toString())
        return customerByRegistration.map { customer ->
            dependentsUsecase.getDependent(dependentRegistration, transactionId.toString())
        }.orElseThrow { CustomerNotFoundException("Customer not found") }
    }

    @Throws(CustomerNotFoundException::class)
    fun getDependetsList(registration: String, transactionId: UUID): List<DependentDto> {
        val customerByRegistration = customersUsecase.getCustomerByRegistration(registration, transactionId.toString())
        return customerByRegistration.map { customer ->
            dependentsUsecase.getAllDependents(customer, transactionId.toString())
        }.orElseThrow { CustomerNotFoundException("Customer not found") }
    }

    @Throws(CustomerNotFoundException::class)
    fun getCustomerByRegistration(registration: String, transactionId: UUID): CustomerDto {
        val customerByRegistration = customersUsecase.getCustomerByRegistration(registration, transactionId.toString())
        return customerByRegistration.map(Mappers::getCustomerDto)
            .orElseThrow { CustomerNotFoundException("Customer not found") }
    }

    fun getAll(): List<CustomerDto> {
        return customersUsecase.getAll()
    }
}