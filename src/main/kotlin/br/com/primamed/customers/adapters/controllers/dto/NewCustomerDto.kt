package br.com.primamed.customers.adapters.controllers.dto

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

class NewCustomerDto (

    @NotNull(message = "Name is required")
    @Size(min = 10, max = 100, message = "Name must be between 10 and 100 characters")
    @Schema(description = "Nome completo do cliente.", example = "Estela Ferreira")
    @org.jetbrains.annotations.NotNull
    val name: String,

    @NotNull(message = "Birthdate is required")
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "America/Sao_Paulo")
    val birthDate: String,

    @NotNull(message = "Document is required")
    val document: String,

    @NotNull(message = "Gender is required")
    val gender: String,

    @NotNull(message = "Cell phone is required")
    @NotBlank(message = "Cell phone can't be empty")
    @Pattern(regexp = "\\d{10,11}", message = "Cell phone format is invalid")
    val cellPhone: String,

    val phoneNumber: String,

    @NotNull(message = "Nationality is required")
    val nationality: String,

    @NotNull(message = "MaritalStatus is required")
    @NotBlank
    val maritalStatus: String,

    @NotNull(message = "CodePlan is required")
    @NotBlank
    val codePlan: String,

    @NotNull(message = "Email is required")
    @NotBlank(message = "Email can't be empty")
    val email: String,

    @NotBlank(message = "Name is required")
    val address: AddressDto
)