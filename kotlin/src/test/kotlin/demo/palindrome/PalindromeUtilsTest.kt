package demo.palindrome

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class PalindromeUtilsTest {

    private val palindromeUtils = PalindromeUtils()

    @Test
    fun `isPalindrome should return true for an empty string`() {
        assertTrue(palindromeUtils.isPalindrome(""))
    }

    @ParameterizedTest
    @ValueSource(strings = ["a", "b"])
    fun `isPalindrome should return true for a single character string`(input: String) {
        assertTrue(palindromeUtils.isPalindrome(input))
    }
}
