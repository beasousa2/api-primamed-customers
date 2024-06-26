package br.com.primamed.customers.adapters.repositories


import br.com.primamed.customers.adapters.repositories.entities.Customer
import br.com.primamed.customers.adapters.repositories.entities.Dependent
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface DependentRepository: JpaRepository<Dependent, Long> {

    fun findByRegistration(registration: String): Optional<Dependent>
    fun findAllByCustomer(customer: Customer): List<Dependent>
}