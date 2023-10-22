import logging

from url_utils import validate_url
from request_handler import send_request

def execute_request(url, body):
    try:
        validate_url(url)
        response = send_request(url, body)
        if "error" in response:
            logging.warning(f'Request failed with {response["error"]}')
            return {}
        return response
    except ValueError as e:
        logging.error(f'Invalid url: {url}')
    except ConnectionError as e:
        raise RuntimeError('Connection failed')