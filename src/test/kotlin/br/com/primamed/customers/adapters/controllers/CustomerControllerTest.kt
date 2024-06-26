package br.com.primamed.customers.adapters.controllers

import br.com.primamed.customers.adapters.controllers.dto.*
import br.com.primamed.customers.adapters.repositories.entities.Customer
import br.com.primamed.customers.adapters.services.CustomerService
import br.com.primamed.customers.enum.CustomerStatus
import br.com.primamed.customers.enums.Gender
import br.com.primamed.customers.enums.MaritalStatus
import br.com.primamed.customers.enums.Nationality
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.time.LocalDate
import java.util.*

class CustomerControllerTest {

    @Mock
    lateinit var service: CustomerService

    @InjectMocks
    lateinit var customerController: CustomerController

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should add customer and return CustomerDto`() {
        val newCustomerDto = newCustomerDto()
        val customerDto = customerDto()
        val transactionId = UUID.randomUUID()

        `when`(service.saveCustomer(newCustomerDto, transactionId)).thenReturn(customerDto)

        val response: ResponseEntity<CustomerDto> = customerController.addCustomer(newCustomerDto, transactionId)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(customerDto, response.body)
        verify(service).saveCustomer(newCustomerDto, transactionId)
    }

    @Test
    fun `should get customer by registration and return CustomerDto`() {
        val registration = "12345"
        val customerDto = customerDto()
        val transactionId = UUID.randomUUID()

        `when`(service.getCustomerByRegistration(registration, transactionId)).thenReturn(customerDto)

        val response: ResponseEntity<CustomerDto> = customerController.getByDocument(registration, transactionId)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(customerDto, response.body)
        verify(service).getCustomerByRegistration(registration, transactionId)
    }

    @Test
    fun `should get all customers and return list of CustomerDto`() {
        val customers = listOf(customerDto())

        `when`(service.getAll()).thenReturn(customers)

        val response: ResponseEntity<List<CustomerDto>> = customerController.getAll()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(customers, response.body)
        verify(service).getAll()
    }

    @Test
    fun `should add dependent and return OK`() {
        val registration = "12345"
        val newDependentDto = newDependentDto()
        val transactionId = UUID.randomUUID()

        doNothing().`when`(service).saveDependent(registration, newDependentDto, transactionId)

        val response: ResponseEntity<Unit> = customerController.addDependent(registration, newDependentDto, transactionId)

        assertEquals(HttpStatus.OK, response.statusCode)
        verify(service).saveDependent(registration, newDependentDto, transactionId)
    }

    @Test
    fun `should get customer dependents and return list of DependentDto`() {
        val registration = "12345"
        val dependents = listOf(dependentDto())
        val transactionId = UUID.randomUUID()

        `when`(service.getDependetsList(registration, transactionId)).thenReturn(dependents)

        val response: ResponseEntity<List<DependentDto>> = customerController.getCustomerDependents(registration, transactionId)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(dependents, response.body)
        verify(service).getDependetsList(registration, transactionId)
    }

    @Test
    fun `should get dependent by registration and return DependentDto`() {
        val registration = "12345"
        val dependentRegistration = "54321"
        val dependentDto = dependentDto()
        val transactionId = UUID.randomUUID()

        `when`(service.getDependent(registration, dependentRegistration, transactionId)).thenReturn(dependentDto)

        val response: ResponseEntity<DependentDto> = customerController.getDependentByRegistration(registration, dependentRegistration, transactionId)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(dependentDto, response.body)
        verify(service).getDependent(registration, dependentRegistration, transactionId)
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
