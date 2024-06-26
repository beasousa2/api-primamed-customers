package br.com.primamed.customers.adapters.repositories.entities

import br.com.primamed.customers.enums.KinshipDegree
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(name = "cust_dependent")
class Dependent(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val name: String,
    val birthDate: LocalDate,
    val document: String,
    val registration: String,
    val relation: KinshipDegree,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "customer_id")
    val customer: Customer,
    val createdAt: LocalDateTime,
) {
}