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

    @ParameterizedTest
    @ValueSource(strings = ["a", "level", "", "aa"])
    fun `isPalindrome should return true for palindromic string`(input: String) {
        assertTrue(palindromeUtils.isPalindrome(input))
    }

    @ParameterizedTest
    @ValueSource(strings = ["test", "ab", "invalid"])
    fun `isPalindrome should return false for non-palindromic string`(input: String) {
        assertFalse(palindromeUtils.isPalindrome(input))
    }

    @ParameterizedTest
    @ValueSource(strings = ["aA", "Level", "A"])
    fun `isPalindrome should return true for mixed case palindromic string`(input: String) {
        assertTrue(palindromeUtils.isPalindrome(input))
    }

    @ParameterizedTest
    @ValueSource(strings = ["Don't nod"])
    fun `isPalindrome should return true for palindromic strings with punctuation`(input: String) {
        assertTrue(palindromeUtils.isPalindrome(input))
    }
}