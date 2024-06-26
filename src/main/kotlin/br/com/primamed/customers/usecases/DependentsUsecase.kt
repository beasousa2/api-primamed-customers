package br.com.primamed.customers.usecases

import br.com.primamed.customers.adapters.controllers.dto.DependentDto
import br.com.primamed.customers.adapters.controllers.dto.NewDependentDto
import br.com.primamed.customers.adapters.repositories.entities.Customer

interface DependentsUsecase {

    fun saveDependent(dto : NewDependentDto, customer : Customer) : DependentDto

    fun getDependent(registration: String, transactionId: String): DependentDto

    fun getAllDependents(customer: Customer, transactionId: String): List<DependentDto>


}