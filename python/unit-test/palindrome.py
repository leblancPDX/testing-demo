

def is_palindrome(input) -> bool:
    if type(input) != str:
        raise TypeError("Input must be a string")
    lowercase_input = [c.lower() for c in input if c.isalpha()]
    return lowercase_input == lowercase_input[::-1]

