import unittest
import shapes
import io
import sys

from math import pi

class TestShapes(unittest.TestCase):

    def test_circle_area(self):
        self.assertAlmostEqual(shapes.Circle(1).area(), pi)
        self.assertAlmostEqual(shapes.Circle(0).area(), 0)
        self.assertAlmostEqual(shapes.Circle(2.1).area(), pi* 2.1**2)

    def test_circle_value_error(self):
        self.assertRaises(ValueError, shapes.Circle, -2)

    def test_circle_type_error(self):
        self.assertRaises(TypeError, shapes.Circle, True)
        self.assertRaises(TypeError, shapes.Circle, 'test')
        self.assertRaises(TypeError, shapes.Circle, 2+1j)

    def test_circle_name(self):
        self.assertEqual('Circle', shapes.Circle(1).name)

    def test_rectangle_area(self):
        self.assertAlmostEqual(shapes.Rectangle(1, 1).area(), 1)
        self.assertAlmostEqual(shapes.Rectangle(0, 1).area(), 0)
        self.assertAlmostEqual(shapes.Rectangle(2.1, 1).area(), 2.1)

    def test_rectangle_value_error(self):
        self.assertRaises(ValueError, shapes.Rectangle, -2, 1)

    def test_rectangle_type_error(self):
        self.assertRaises(TypeError, shapes.Rectangle, True, True)
        self.assertRaises(TypeError, shapes.Rectangle, 'test', 1)
        self.assertRaises(TypeError, shapes.Rectangle, 2+1j, 1)

    def test_rectangle_name(self):
        self.assertEqual('Rectangle', shapes.Rectangle(1, 1).name)

    def test_square_area(self):
        self.assertAlmostEqual(shapes.Square(1).area(), 1)
        self.assertAlmostEqual(shapes.Square(0).area(), 0)
        self.assertAlmostEqual(shapes.Square(2.1).area(), 2.1*2.1)

    def test_square_value_error(self):
        self.assertRaises(ValueError, shapes.Square, -2)

    def test_square_type_error(self):
        self.assertRaises(TypeError, shapes.Square, True)
        self.assertRaises(TypeError, shapes.Square, 'test')
        self.assertRaises(TypeError, shapes.Square, 2+1j)

    def test_square_name(self):
        self.assertEqual('Square', shapes.Square(1).name)

    def test_shape_name(self):
        self.assertEqual('Generic Shape', shapes.Shape().name)

    def test_shape_area(self):
        self.assertIsNone(shapes.Shape().area())

    def test_print_name(self):
        captured_output = io.StringIO()
        sys.stdout = captured_output
        circle = shapes.Circle(1)
        circle.print_name()
        sys.stdout = sys.__stdout__
        self.assertEqual("Circle\n", captured_output.getvalue())