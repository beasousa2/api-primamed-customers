package br.com.primamed.customers.adapters.repositories

import br.com.primamed.customers.adapters.repositories.entities.Address
import org.springframework.data.jpa.repository.JpaRepository

interface AddressRepository : JpaRepository<Address, Long> {
}