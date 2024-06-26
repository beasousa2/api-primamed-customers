package br.com.primamed.customers.usecases

import br.com.primamed.customers.adapters.controllers.dto.AddressDto
import br.com.primamed.customers.adapters.controllers.dto.NewCustomerDto
import br.com.primamed.customers.adapters.repositories.AddressRepository
import br.com.primamed.customers.adapters.repositories.CustomerRepository
import br.com.primamed.customers.adapters.repositories.entities.Address
import br.com.primamed.customers.adapters.repositories.entities.Customer
import br.com.primamed.customers.enum.CustomerStatus
import br.com.primamed.customers.enums.Gender
import br.com.primamed.customers.enums.MaritalStatus
import br.com.primamed.customers.enums.Nationality
import br.com.primamed.customers.utils.Mappers
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class CustomerUsecaseImplTest {

    @Mock
    lateinit var customerRepository: CustomerRepository

    @Mock
    lateinit var addressRepository: AddressRepository

    @InjectMocks
    lateinit var customerUsecase: CustomerUsecaseImpl

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should save customer`() {
        val newCustomerDto = newCustomerDto()
        val customer = customer()

        `when`(customerRepository.save(any(Customer::class.java))).thenReturn(customer)

        val result = customerUsecase.saveCustomer(newCustomerDto)

        assertEquals(customer, result)
        verify(customerRepository).save(any(Customer::class.java))
    }

    @Test
    fun `should get customer by registration`() {
        val registration = "12345"
        val customer = customer()

        `when`(customerRepository.findByRegistration(registration)).thenReturn(Optional.of(customer))

        val result = customerUsecase.getCustomerByRegistration(registration, UUID.randomUUID().toString())

        assertEquals(Optional.of(customer), result)
        verify(customerRepository).findByRegistration(registration)
    }

    @Test
    fun `should add customer address`() {
        val newCustomerDto = newCustomerDto()
        val customer = customer()
        val address = Address(
            street = newCustomerDto.address.street,
            city = newCustomerDto.address.city,
            zipCode = newCustomerDto.address.zipCode,
            addition = newCustomerDto.address.addition,
            number = newCustomerDto.address.number,
            district = newCustomerDto.address.district,
            state = newCustomerDto.address.state,
            customer = customer
        )

        `when`(addressRepository.save(any(Address::class.java))).thenReturn(address)

        customerUsecase.addCustomerAddress(newCustomerDto, customer)

        verify(addressRepository).save(any(Address::class.java))
    }

    @Test
    fun `should get all customers`() {
        val customers = listOf(customer())

        val customerDtos = customers.map { customer -> Mappers.getCustomerDto(customer) }

        `when`(customerRepository.findAll()).thenReturn(customers)

        val result = customerUsecase.getAll()

        assertEquals(customerDtos, result)
        verify(customerRepository).findAll()
    }

    private fun customer() : Customer {
        return Customer(
            id = 1,
            name = "Nome Teste",
            birthDate = LocalDate.now(),
            document = "123456789",
            gender = Gender.FEMININO,
            cellPhone = "123456",
            registration = "12345",
            nationality = Nationality.BRASILEIRO,
            maritalStatus = MaritalStatus.SOLTEIRO,
            email = "email@example.com",
            address = null,
            createdAt = LocalDateTime.now(),
            codePlan = "1",
            status = CustomerStatus.ACTIVE,
            phoneNumber = null
        )
    }

    private fun newCustomerDto() : NewCustomerDto {
        return NewCustomerDto(
            name = "Nome Teste",
            birthDate = "25/06/2024",
            document = "123456789",
            gender = Gender.FEMININO.name,
            cellPhone = "123456",
            nationality = Nationality.BRASILEIRO.name,
            maritalStatus = MaritalStatus.SOLTEIRO.name,
            email = "email@example.com",
            address = addressDto(),
            codePlan = "1",
            phoneNumber = ""
        )
    }

    private fun addressDto() : AddressDto {
        return AddressDto(
            street = "Rua teste",
            number = "15",
            state = "teste",
            city = "teste",
            district = "teste",
            zipCode = "teste",
            addition = ""
        )
    }
}
