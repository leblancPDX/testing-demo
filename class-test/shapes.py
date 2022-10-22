
from math import pi

class Shape():
    name = 'Generic Shape'

    def print_name(self):
        print(self.name)

    def area(self):
        return None

    def _check_number(self, value, field_name):
        if type(value) not in [int, float]:
            raise TypeError(f'{field_name} must be a number')
        if value < 0:
            raise ValueError(f'{field_name} must be non-negative')
        return type(value) not in [int, float]

class Rectangle(Shape):
    name = 'Rectangle'

    def __init__(self, width, height):
        self._check_number(width, "Width")
        self._check_number(height, "Height")
        self.width = width
        self.height = height

    def area(self):
        return self.width * self.height

class Square(Shape):
    name = 'Square'

    def __init__(self, width):
        self._check_number(width, "Width")
        self.width = width

    def area(self):
        return self.width * self.width

class Circle(Shape):
    name = 'Circle'

    def __init__(self, radius):
        self._check_number(radius, "Radius")
        self.radius = radius

    def area(self):
        return pi * (self.radius ** 2)
