import xmltodict
import os

CONFIG_FILE = 'config.ini'


def load_xml_to_dict(file_path):
    with open(file_path, 'r') as file:
        xml_content = file.read()
        dictionary = xmltodict.parse(xml_content)
        return dictionary


def save_dict_to_xml(dictionary, file_path):
    with open(file_path, 'w') as file:
        xml_content = xmltodict.unparse(dictionary, pretty=True)
        file.write(xml_content)


def create_default_config(config_file: str):
    # Create default config
    default_config = {
        'config': {
            'database_path': 'database.db',
            'tws_client': '127.0.0.1',
            'tws_port': '7497',
        }
    }

    # Save config to file
    save_dict_to_xml(default_config, config_file)
