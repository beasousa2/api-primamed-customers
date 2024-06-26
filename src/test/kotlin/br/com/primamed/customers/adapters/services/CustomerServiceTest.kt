package br.com.primamed.customers.adapters.services

import br.com.primamed.customers.adapters.controllers.dto.*
import br.com.primamed.customers.adapters.repositories.entities.Customer
import br.com.primamed.customers.enum.CustomerStatus
import br.com.primamed.customers.enums.Gender
import br.com.primamed.customers.enums.MaritalStatus
import br.com.primamed.customers.enums.Nationality
import br.com.primamed.customers.exceptions.CustomerNotFoundException
import br.com.primamed.customers.usecases.CustomerUsecase
import br.com.primamed.customers.usecases.DependentsUsecase
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class CustomerServiceTest {

    @Mock
    lateinit var customersUsecase: CustomerUsecase

    @Mock
    lateinit var dependentsUsecase: DependentsUsecase

    @InjectMocks
    lateinit var customerService: CustomerService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should save customer and return CustomerDto`() {
        val newCustomerDto = newCustomerDto()
        val customer = customer()
        val customerDto = customerDto()
        val transactionId = UUID.randomUUID().toString()

        `when`(customersUsecase.saveCustomer(newCustomerDto)).thenReturn(customer)

        val result = customerService.saveCustomer(newCustomerDto, UUID.fromString(transactionId))

        assertEquals(customerDto, result)
        verify(customersUsecase).saveCustomer(newCustomerDto)
        verify(customersUsecase).addCustomerAddress(newCustomerDto, customer)
    }

    @Test
    fun `should save dependent when customer exists`() {
        val registration = "12345"
        val newDependentDto = newDependentDto()
        val customer = customer()
        val transactionId = UUID.randomUUID().toString()

        `when`(customersUsecase.getCustomerByRegistration(registration, transactionId)).thenReturn(Optional.of(customer))

        customerService.saveDependent(registration, newDependentDto, UUID.fromString(transactionId))

        verify(dependentsUsecase).saveDependent(newDependentDto, customer)
    }

    @Test
    fun `should throw CustomerNotFoundException when saving dependent and customer does not exist`() {
        val registration = "12345"
        val newDependentDto = newDependentDto()
        val transactionId = UUID.randomUUID().toString()

        `when`(customersUsecase.getCustomerByRegistration(registration, transactionId)).thenReturn(Optional.empty())

        assertThrows<CustomerNotFoundException> {
            customerService.saveDependent(registration, newDependentDto,UUID.fromString(transactionId))
        }

        verify(dependentsUsecase, never()).saveDependent(newDependentDto, customer())
    }

    @Test
    fun `should return dependent when customer and dependent exist`() {

        val customer = customer()
        val dependentDto = dependentDto()
        val transactionId = UUID.randomUUID().toString()

        `when`(customersUsecase.getCustomerByRegistration("12345", transactionId)).thenReturn(Optional.of(customer))
        `when`(dependentsUsecase.getDependent("54321", transactionId)).thenReturn(dependentDto)

        val result = customerService.getDependent("12345", "54321", UUID.fromString(transactionId))

        assertEquals(dependentDto, result)
    }

    @Test
    fun `should throw CustomerNotFoundException when getting dependent and customer does not exist`() {
        val registration = "12345"
        val dependentRegistration = "54321"
        val transactionId = UUID.randomUUID().toString()

        `when`(customersUsecase.getCustomerByRegistration(registration, transactionId)).thenReturn(Optional.empty())

        assertThrows<CustomerNotFoundException> {
            customerService.getDependent(registration, dependentRegistration, UUID.fromString(transactionId))
        }

        verify(dependentsUsecase, never()).getDependent(anyString(), anyString())
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
            birthDate = LocalDate.now().toString(),
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

    private fun customerDto() : CustomerDto {
        return CustomerDto(
            name = "Nome Teste",
            birthDate = LocalDate.now(),
            cellPhone = "123456",
            email = "email@example.com",
            registration = "12345"
        )
    }

    private fun newDependentDto() : NewDependentDto {
        return NewDependentDto(
            name = "Nome Teste",
            birthDate = LocalDate.now(),
            relation = "conjuge",
            document = "125436950",
            customerId = 1
        )
    }

    private fun dependentDto() : DependentDto {
        return DependentDto(
            name = "Nome Teste",
            birthDate = LocalDate.now(),
            relation = "conjuge",
            document = "125436950",
            registration = "54321"
        )
    }
}
