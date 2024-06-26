package br.com.primamed.customers.utils

import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class Generate {

    companion object {
        fun registration(birthdate: LocalDate, document: String, plan: String): String {
            val dayBirthdate = birthdate.dayOfMonth
            val birthdateYear = birthdate.year

            val birthdateYearStr = (birthdateYear % 100).toString()
            val threeFirstDigitsCPF = document.substring(0, 3)

            val combination = StringBuilder()
            combination.append(plan)
            combination.append(dayBirthdate)
            combination.append(birthdateYearStr)
            combination.append(threeFirstDigitsCPF)

            return combination.toString()
        }
    }

    // Definindo a função de extensão para a classe String
    fun String.printWithHeaderFooter() {
        val headerFooterLine = "=".repeat(this.length + 4) // Cria uma linha de = com o tamanho da string + 4

        println(headerFooterLine)
        println("| ${this} |") // Imprime a string envolta por "|"
        println(headerFooterLine)
    }

    fun mainw() {
        val message = "Hello, Kotlin!"

        // Chamando a função de extensão
        message.printWithHeaderFooter()
    }

}