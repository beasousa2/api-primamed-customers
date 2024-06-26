package br.com.primamed.customers.adapters.controllers.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class CustomerDto (
    val name: String?,
    @JsonFormat(pattern = "dd/MM/yyyy")
    val birthDate: LocalDate?,
    val registration: String?,
    val cellPhone: String?,
    val email: String?

)
