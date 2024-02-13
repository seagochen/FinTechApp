import os
import configparser

from flask import Flask

# Register the views
from app.apis.LedgerServiceAPIs import ledger_service


def load_database(config_filepath):
    # Load config from file
    config = configparser.ConfigParser()
    config.read(config_filepath)

    # Get the database path
    database_path = config['SQLITE']['base_path']

    return database_path


def create_app(config_filepath='config.ini'):
    # Create flask app
    app = Flask(__name__)

    # load xml config from file
    if os.path.exists(config_filepath):
        # Save the database path to the session
        app.config['DATABASE'] = load_database(config_filepath)
    else:
        # Default config file path
        config_filepath = 'config.ini'

        # Create the default config
        config = configparser.ConfigParser()
        config['SQLITE'] = {'base_path': 'None'}

        # Save the default config to file
        with open(config_filepath, 'w') as configfile:
            config.write(configfile)

    # Register blueprint
    app.register_blueprint(ledger_service)

    return app
