package br.com.primamed.customers.adapters.repositories

import br.com.primamed.customers.adapters.repositories.entities.Customer
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface CustomerRepository : JpaRepository<Customer, Long> {
    fun findByRegistration(registration: String): Optional<Customer>
}