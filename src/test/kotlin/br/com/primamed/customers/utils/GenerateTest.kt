import br.com.primamed.customers.utils.Generate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class GenerateTest {

    @Test
    fun testRegistration() {
        val birthdate = LocalDate.of(1990, 5, 15)
        val document = "12345678900"
        val plan = "A"

        val result = Generate.registration(birthdate, document, plan)

        assertEquals("A1590123", result)
    }
}
