import xmltodict
import os


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
            'database_path': 'database.db'
        }
    }

    # Save config to file
    save_dict_to_xml(default_config, config_file)


class FileConfigHandler:

    def __init__(self, config_file: str):
        # Parameters will be used to store the configuration
        self.config = None
        self.database_path = None

        # Load config from file
        if os.path.exists(config_file):
            self.load_config(config_file)
        else:
            create_default_config(config_file)
            self.load_config(config_file)

    def load_config(self, config_file: str):
        # Load config from file
        self.config = load_xml_to_dict(config_file)

        # Get the database path
        self.database_path = self.config['config']['database_path']

    def save_config(self, config_file: str):
        # Save config to file
        save_dict_to_xml(self.config, config_file)
