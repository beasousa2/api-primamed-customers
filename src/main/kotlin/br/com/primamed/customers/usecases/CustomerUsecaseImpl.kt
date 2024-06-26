package br.com.primamed.customers.usecases

import br.com.primamed.customers.adapters.controllers.dto.CustomerDto
import br.com.primamed.customers.adapters.controllers.dto.NewCustomerDto
import br.com.primamed.customers.adapters.repositories.AddressRepository
import br.com.primamed.customers.adapters.repositories.CustomerRepository
import br.com.primamed.customers.adapters.repositories.entities.Address
import br.com.primamed.customers.adapters.repositories.entities.Customer
import br.com.primamed.customers.enums.Gender
import br.com.primamed.customers.enums.MaritalStatus
import br.com.primamed.customers.enums.Nationality
import br.com.primamed.customers.utils.Mappers.Companion.getCustomerDto
import br.com.primamed.customers.utils.Mappers.Companion.toCustomer
import java.util.Optional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Component
class CustomerUsecaseImpl : CustomerUsecase {

    @Autowired
    lateinit var customerRepository: CustomerRepository

    @Autowired
    lateinit var addressRepository: AddressRepository

    @Transactional
    override fun saveCustomer(newCustomerDto: NewCustomerDto): Customer {
        var customerToSave : Customer = toCustomer(newCustomerDto)
        return customerRepository.save(customerToSave)

    }

    override fun getCustomerByRegistration(document: String, transactionId: String): Optional<Customer> {
        return customerRepository.findByRegistration(document)
    }

    @Transactional
    override fun addCustomerAddress(newCustomerDto: NewCustomerDto, customer: Customer) {
        val address = addAddress(newCustomerDto, customer)
        addressRepository.save(address)
    }

    override fun getAll(): List<CustomerDto> {
        val listCustomer: List<Customer> = customerRepository.findAll()
        return listCustomer.map { customer -> getCustomerDto(customer) }

    }

    private fun addAddress(newCustomerDto: NewCustomerDto, customer: Customer) : Address {
        return Address(
            street = newCustomerDto.address.street,
            city = newCustomerDto.address.city,
            zipCode = newCustomerDto.address.zipCode,
            addition = newCustomerDto.address.addition,
            number = newCustomerDto.address.number,
            district = newCustomerDto.address.district,
            state = newCustomerDto.address.state,
            customer = customer
        )
    }
}