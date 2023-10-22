import example

import unittest
from unittest.mock import patch

class TestExample(unittest.TestCase):

    @patch('example.send_request')
    @patch('example.validate_url')
    def test_valid_url(self, mock_url_validator, mock_request_sender):
        mock_request_sender.return_value = {'test': 1}
        response = example.execute_request('', {})
        self.assertDictEqual({'test': 1}, response)
        mock_url_validator.validate_url.called_with('')

    @patch('example.send_request')
    @patch('example.validate_url')
    def test_invalid_url(self, mock_url_validator, mock_request_sender):
        mock_request_sender.return_value = {}
        mock_url_validator.side_effect = ValueError("Invalid Url")
        response = example.execute_request('', {})
        self.assertIsNone(response)
        mock_url_validator.validate_url.called_with('')

    @patch('example.send_request')
    @patch('example.validate_url')
    def test_connection_error(self, mock_url_validator, mock_request_sender):
        mock_request_sender.side_effect = ConnectionError("Connection Failed")
        self.assertRaises(RuntimeError, example.execute_request, '', {})
        mock_url_validator.validate_url.called_with('')

    @patch('example.send_request')
    @patch('example.validate_url')
    def test_errors_in_response(self, mock_url_validator, mock_request_sender):
        mock_request_sender.return_value = {'error': {}}
        self.assertDictEqual({}, example.execute_request('', {}))


if __name__ == '__main__':
    unittest.main()