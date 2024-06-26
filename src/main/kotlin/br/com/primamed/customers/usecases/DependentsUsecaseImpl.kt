package br.com.primamed.customers.usecases.impl

import br.com.primamed.customers.adapters.controllers.dto.DependentDto
import br.com.primamed.customers.adapters.controllers.dto.NewDependentDto
import br.com.primamed.customers.adapters.repositories.DependentRepository
import br.com.primamed.customers.adapters.repositories.entities.Customer
import br.com.primamed.customers.exceptions.CustomerNotFoundException
import br.com.primamed.customers.exceptions.DependentNotFoundException
import br.com.primamed.customers.usecases.DependentsUsecase
import br.com.primamed.customers.utils.Mappers
import br.com.primamed.customers.utils.Mappers.Companion.getDependentDto
import br.com.primamed.customers.utils.Mappers.Companion.toDependent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.slf4j.Logger
import org.slf4j.LoggerFactory


@Component
class DependentsUsecaseImpl : DependentsUsecase {
    
    @Autowired
    lateinit var repository: DependentRepository

    private val logger: Logger = LoggerFactory.getLogger(DependentsUsecaseImpl::class.java)

    override fun saveDependent(dto: NewDependentDto, customer: Customer): DependentDto {
        val dependent = toDependent(dto, customer)
        val dependentDto = repository.save(dependent)
        return getDependentDto(dependentDto)
    }

    @Throws(DependentNotFoundException::class)
    override fun getDependent(registration: String, transactionId: String): DependentDto {
        logger.info("Buscando dependente")
        val dependent = repository.findByRegistration(registration)

        val dependentDto = dependent.map(Mappers::getDependentDto)

            .orElseThrow {
                logger.error("Erro ao buscar dependente. Transacao = " + transactionId)
                DependentNotFoundException("Dependent not found") }

        logger.info("Retornando dependente")
        return dependentDto
    }

    @Throws(DependentNotFoundException::class)
    override fun getAllDependents(customer: Customer, transactionId: String): List<DependentDto> {
        logger.info("Buscando lista de dependentes")
        val dependentList = repository.findAllByCustomer(customer)

        if (dependentList.isEmpty()) {
            logger.error("Erro ao buscar dependentes: lista vazia. Transacao = " + transactionId)
            throw DependentNotFoundException("Customer has no dependents")
        }

        logger.info("Retornando lista de dependentes")
        return dependentList.map(Mappers::getDependentDto)

    }
}
