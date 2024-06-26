package br.com.primamed.customers.adapters.controllers.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class AddressDto(
    @NotNull(message = "Street is required")
    @Size(min = 10)
    val street: String,

    @NotNull(message = "Zip code is required")
    @Size(min = 6, max = 7)
    @Pattern(regexp = "\\d{8}", message = "Zip code format is invalid")
    val zipCode: String,

    @NotNull(message = "Number is required")
    val number: String,

    @NotNull(message = "District is required")
    val district: String,

    val addition: String,

    @NotNull(message = "City is required")
    val city: String,

    @NotNull(message = "State is required")
    @Size(min = 2, max = 2, message = "State must have only 2 characters")
    val state: String
)
