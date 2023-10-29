import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class PalindromeUtilTest {

    private val palindromeUtil = PalindromeUtil()

    @Test
    fun `isPalindrome should return true for palindromic string`() {
        assertTrue(palindromeUtil.isPalindrome("a"))
    }
}