import unittest
from palindrome import is_palindrome

class TestPalindrome(unittest.TestCase):

    def test_palindrome(self):
        self.assertTrue(is_palindrome(''))
        self.assertTrue(is_palindrome('a'))
        self.assertTrue(is_palindrome('AA'))
        self.assertTrue(is_palindrome('racecar'))

    def test_not_palindrome(self):
        self.assertFalse(is_palindrome('ab'))
        self.assertFalse(is_palindrome('test'))

    def test_type_error(self):
        self.assertRaises(TypeError, is_palindrome, True)
        self.assertRaises(TypeError, is_palindrome, 4)
        self.assertRaises(TypeError, is_palindrome, [])
        self.assertRaises(TypeError, is_palindrome, {})
        self.assertRaises(TypeError, is_palindrome, None)
    
    def test_mixed_case(self):
        self.assertTrue(is_palindrome('aA'))

    def test_ignore_symbols(self):
        self.assertTrue(is_palindrome(',a#$%&*(a'))
        self.assertTrue(is_palindrome("Don't nod!"))

if __name__ == "__main__":
    unittest.main()