package br.com.primamed.customers.usecases.impl

import br.com.primamed.customers.adapters.controllers.dto.*
import br.com.primamed.customers.adapters.repositories.DependentRepository
import br.com.primamed.customers.adapters.repositories.entities.Customer
import br.com.primamed.customers.adapters.repositories.entities.Dependent
import br.com.primamed.customers.enum.CustomerStatus
import br.com.primamed.customers.enums.Gender
import br.com.primamed.customers.enums.KinshipDegree
import br.com.primamed.customers.enums.MaritalStatus
import br.com.primamed.customers.enums.Nationality
import br.com.primamed.customers.exceptions.DependentNotFoundException
import br.com.primamed.customers.utils.Mappers
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

class DependentsUsecaseImplTest {

    @Mock
    lateinit var repository: DependentRepository

    @InjectMocks
    lateinit var dependentsUsecase: DependentsUsecaseImpl

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should save dependent`() {
        val newDependentDto = newDependentDto()
        val customer = customer()
        val dependent = dependent()

        `when`(repository.save(any(Dependent::class.java))).thenReturn(dependent)

        dependentsUsecase.saveDependent(newDependentDto, customer)


        verify(repository).save(any(Dependent::class.java))
    }

    @Test
    fun `should get dependent by registration`() {
        val registration = "54321"
        val transactionId = UUID.randomUUID().toString()
        val dependent = dependent()
        val dependentDto = Mappers.getDependentDto(dependent)

        `when`(repository.findByRegistration(registration)).thenReturn(Optional.of(dependent))

        val result = dependentsUsecase.getDependent(registration, transactionId)

        assertEquals(dependentDto, result)
        verify(repository).findByRegistration(registration)
    }

    @Test
    fun `should throw DependentNotFoundException when dependent not found`() {
        val registration = "54321"
        val transactionId = UUID.randomUUID().toString()

        `when`(repository.findByRegistration(registration)).thenReturn(Optional.empty())

        assertThrows<DependentNotFoundException> {
            dependentsUsecase.getDependent(registration, transactionId)
        }

        verify(repository).findByRegistration(registration)
    }

    @Test
    fun `should get all dependents of a customer`() {
        val customer = customer()
        val dependents = listOf(dependent())
        val dependentDtos = dependents.map { Mappers.getDependentDto(it) }

        `when`(repository.findAllByCustomer(customer)).thenReturn(dependents)

        val result = dependentsUsecase.getAllDependents(customer, UUID.randomUUID().toString())

        assertEquals(dependentDtos, result)
        verify(repository).findAllByCustomer(customer)
    }

    @Test
    fun `should throw DependentNotFoundException when customer has no dependents`() {
        val customer = customer()
        val transactionId = UUID.randomUUID().toString()

        `when`(repository.findAllByCustomer(customer)).thenReturn(emptyList())

        assertThrows<DependentNotFoundException> {
            dependentsUsecase.getAllDependents(customer, transactionId)
        }

        verify(repository).findAllByCustomer(customer)
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



    private fun newDependentDto() : NewDependentDto {
        return NewDependentDto(
            name = "Dependent 1",
            birthDate = LocalDate.now(),
            relation = KinshipDegree.CONJUGE.name,
            document = "123456789",
            customerId = 1
        )
    }

    private fun dependentDto() : DependentDto {
        return DependentDto(
            name = "Dependent 1",
            birthDate = LocalDate.now(),
            relation = KinshipDegree.CONJUGE.name,
            document = "123456789",
            registration = "54321"
        )
    }

    private fun dependent() : Dependent {
        return Dependent(
            id = 1,
            name = "Dependent 1",
            birthDate = LocalDate.now(),
            document = "123456789",
            registration = "54321",
            customer = customer(),
            relation = KinshipDegree.CONJUGE,
            createdAt = LocalDateTime.now()
        )
    }
}
