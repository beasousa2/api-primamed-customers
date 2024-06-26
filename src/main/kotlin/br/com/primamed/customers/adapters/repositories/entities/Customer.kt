package br.com.primamed.customers.adapters.repositories.entities

import br.com.primamed.customers.enum.CustomerStatus
import br.com.primamed.customers.enums.Gender
import br.com.primamed.customers.enums.MaritalStatus
import br.com.primamed.customers.enums.Nationality
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(name = "customer")
class Customer(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        val name: String,
        val birthDate: LocalDate,
        val document: String,
        val gender: Gender,
        var cellPhone: String,
        val registration : String,
        var phoneNumber: String?,
        val nationality: Nationality,
        val maritalStatus: MaritalStatus,
        val email: String,
        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "address_id")
        val address: Address? = null,
        var createdAt : LocalDateTime,
        val codePlan: String,
        val status: CustomerStatus
)
