package br.com.primamed.customers.adapters.controllers.dto

import java.time.LocalDate

data class DependentDto(
    val name: String? = null,
    val birthDate: LocalDate? = null,
    val relation: String? = null,
    val registration: String? = null,
    val document: String? = null
)
