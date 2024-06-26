package br.com.primamed.customers.utils

import br.com.primamed.customers.adapters.controllers.dto.CustomerDto
import br.com.primamed.customers.adapters.controllers.dto.DependentDto
import br.com.primamed.customers.adapters.controllers.dto.NewCustomerDto
import br.com.primamed.customers.adapters.controllers.dto.NewDependentDto
import br.com.primamed.customers.adapters.repositories.entities.Address
import br.com.primamed.customers.adapters.repositories.entities.Customer
import br.com.primamed.customers.adapters.repositories.entities.Dependent
import br.com.primamed.customers.enum.CustomerStatus
import br.com.primamed.customers.enums.Gender
import br.com.primamed.customers.enums.KinshipDegree
import br.com.primamed.customers.enums.MaritalStatus
import br.com.primamed.customers.enums.Nationality
import br.com.primamed.customers.utils.Generate.Companion.registration
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class Mappers {

    companion object {
        fun toCustomer(dto: NewCustomerDto): Customer {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val birthdate = LocalDate.parse(dto.birthDate, formatter)
            val registration = registration(birthdate, dto.document, dto.codePlan)
            return Customer(
                name = dto.name,
                document = dto.document,
                birthDate = birthdate,
                cellPhone = dto.cellPhone,
                registration = registration,
                phoneNumber = dto.phoneNumber,
                gender = Gender.valueOf(dto.gender),
                nationality = Nationality.valueOf(dto.nationality),
                maritalStatus = MaritalStatus.valueOf(dto.maritalStatus),
                email = dto.email,
                createdAt = LocalDateTime.now(),
                codePlan = dto.codePlan,
                status = CustomerStatus.ACTIVE
            )
        }

        fun addAddress(dto: NewCustomerDto, customer: Customer): Address {
            return Address(
                street = dto.address.street,
                zipCode = dto.address.zipCode,
                addition = dto.address.addition,
                number = dto.address.number,
                district = dto.address.district,
                city = dto.address.city,
                state = dto.address.state,
                customer = customer
            )
        }

        fun toDependent(dto: NewDependentDto, customer: Customer): Dependent {
            val registration = registration(dto.birthDate, dto.document, customer.codePlan)
            return Dependent(
                name = dto.name,
                registration = registration,
                relation = KinshipDegree.valueOf(dto.relation),
                document = dto.document,
                birthDate = dto.birthDate,
                customer = customer,
                createdAt = LocalDateTime.now()
            )
        }

        fun getDependentDto(dependent: Dependent): DependentDto {
            return DependentDto(
                name = dependent.name,
                birthDate = dependent.birthDate,
                document = dependent.document,
                registration = dependent.registration
            )
        }

        fun getCustomerDto(customer: Customer): CustomerDto {
            return CustomerDto(
                name = customer.name,
                email = customer.email,
                cellPhone = customer.cellPhone,
                birthDate = customer.birthDate,
                registration = customer.registration
            )
        }


    }
}
