import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class PalindromeUtilsTest {

    private val palindromeUtils = PalindromeUtils()

    @Test
    fun `isPalindrome should return true for single character string`() {
        assertTrue(palindromeUtils.isPalindrome("z"))
    }

}