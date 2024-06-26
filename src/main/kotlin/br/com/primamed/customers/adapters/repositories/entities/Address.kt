package br.com.primamed.customers.adapters.repositories.entities

import jakarta.persistence.*

@Entity(name = "cust_address")
class Address(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        var street: String,
        var zipCode: String,
        var number: String,
        var district: String,
        var addition: String,
        var city: String,
        var state: String,
        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "customer_id")
        var customer: Customer? = null
)