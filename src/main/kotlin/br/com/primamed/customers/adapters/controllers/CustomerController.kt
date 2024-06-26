package br.com.primamed.customers.adapters.controllers

import br.com.primamed.customers.adapters.controllers.dto.*
import br.com.primamed.customers.adapters.repositories.entities.Customer
import br.com.primamed.customers.adapters.services.CustomerService
import br.com.primamed.customers.usecases.CustomerUsecase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/customers")
class CustomerController {

    @Autowired
    lateinit var service: CustomerService

    @Operation(summary = "Add a new customer")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Customer created", content = [Content(mediaType = "application/json", schema = Schema(implementation = CustomerDto::class))]),
            ApiResponse(responseCode = "400", description = "Bad Request", content = []),
            ApiResponse(responseCode = "401", description = "Unauthorized", content = []),
            ApiResponse(responseCode = "500", description = "Error creating customer", content = [])
        ]
    )
    @PostMapping
    fun addCustomer(@RequestBody @Validated customerDto: NewCustomerDto, @RequestParam transactionId: UUID): ResponseEntity<CustomerDto> {
        val customer = service.saveCustomer(customerDto, transactionId)
        return ResponseEntity.ok().body(customer)
    }

    @Operation(summary = "Find customer by registration number")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Customer found by registration", content = [Content(mediaType = "application/json", schema = Schema(implementation = CustomerDto::class))]),
            ApiResponse(responseCode = "400", description = "Bad Request", content = []),
            ApiResponse(responseCode = "404", description = "Not Found", content = []),
            ApiResponse(responseCode = "500", description = "Internal Server Error", content = [])
        ]
    )
    @GetMapping("/{registration}")
    fun getByDocument(@PathVariable registration: String, @RequestParam transactionId: UUID): ResponseEntity<CustomerDto> {
        val customer = service.getCustomerByRegistration(registration, transactionId)
        return ResponseEntity.ok().body(customer)
    }

    @Operation(summary = "Return all customers")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Customers list", content = [Content(mediaType = "application/json", schema = Schema(implementation = CustomerDto::class))]),
            ApiResponse(responseCode = "400", description = "Bad Request", content = []),
            ApiResponse(responseCode = "404", description = "Not Found", content = []),
            ApiResponse(responseCode = "500", description = "Internal Server Error", content = [])
        ]
    )
    @GetMapping("")
    fun getAll(): ResponseEntity<List<CustomerDto>> {
        val customers = service.getAll()
        return ResponseEntity.ok().body(customers)
    }

    @Operation(summary = "Add customer dependent")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Dependent created", content = [Content(mediaType = "application/json", schema = Schema(implementation = NewDependentDto::class))]),
            ApiResponse(responseCode = "400", description = "Bad Request", content = []),
            ApiResponse(responseCode = "404", description = "Not Found", content = []),
            ApiResponse(responseCode = "500", description = "Internal Server Error", content = [])
        ]
    )
    @PostMapping("/{registration}/dependents")
    fun addDependent(@PathVariable registration: String, @RequestBody dependentDto: NewDependentDto, @RequestParam transactionId: UUID): ResponseEntity<Unit> {
        service.saveDependent(registration, dependentDto, transactionId)
        return ResponseEntity.ok().body(Unit)
    }

    @Operation(summary = "Find customer dependents")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Customer dependents found", content = [Content(mediaType = "application/json", schema = Schema(implementation = ListDependentsDto::class))]),
            ApiResponse(responseCode = "400", description = "Bad Request", content = []),
            ApiResponse(responseCode = "404", description = "Not Found", content = []),
            ApiResponse(responseCode = "500", description = "Internal Server Error", content = [])
        ]
    )
    @GetMapping("/{registration}/dependents")
    fun getCustomerDependents(@PathVariable registration: String, @RequestParam transactionId: UUID): ResponseEntity<List<DependentDto>> {
        val dependetsList = service.getDependetsList(registration, transactionId)
        return ResponseEntity.ok().body(dependetsList)
    }

    @Operation(summary = "Find dependent by registration")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Dependent found by registration", content = [Content(mediaType = "application/json", schema = Schema(implementation = DependentDto::class))]),
            ApiResponse(responseCode = "400", description = "Bad Request", content = []),
            ApiResponse(responseCode = "404", description = "Not Found", content = []),
            ApiResponse(responseCode = "500", description = "Internal Server Error", content = [])
        ]
    )
    @GetMapping("/{registration}/dependents/{dependentRegistration}")
    fun getDependentByRegistration(@PathVariable registration: String, @PathVariable dependentRegistration: String, @RequestParam transactionId: UUID): ResponseEntity<DependentDto> {
        val dependentDto = service.getDependent(registration, dependentRegistration, transactionId)
        return ResponseEntity.ok().body(dependentDto)
    }
}