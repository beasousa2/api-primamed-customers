package br.com.primamed.customers.adapters.controllers.dto

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

data class NewDependentDto(
    @NotNull
    @NotBlank
    val name: String,

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    val birthDate: LocalDate,

    @NotNull
    val relation: String,

    @NotNull
    val document: String,

    @NotNull
    val customerId: Long
)
