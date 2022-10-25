import example

import unittest
from unittest.mock import patch

class TestExample(unittest.TestCase):

    @patch('example.send_request')
    @patch('example.validate_url')
    def test_valid_url(self, mock_url_validator, mock_request_sender):
        pass


if __name__ == '__main__':
    unittest.main()