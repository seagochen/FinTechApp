from flask import Flask

# Import the tws service blueprints
from app.tws.TwsConnectionAPI import connection_blueprint
from app.tws.TwsAccountsAPI import accounts_blueprint
from app.tws.TwsBulletinsAPI import bulletins_blueprint
from app.tws.TwsOptionAPI import options_blueprint
from app.tws.TwsStockAPI import stock_blueprint

# Import the TWS EWrapper and EClient
from app.tws.TWSClientWrapper_V1 import TWSClientWrapper_V1

# Import the ledger service blueprints
from app.ledger.LedgerServiceAPI import ledger_blueprint

# Import the configuration file
from app.utility.FileConfigHandler import *

# Define the global variable for the threading object
api_thread = None

# Define ib_app for the TWS connection
ib_app = TWSClientWrapper_V1()


def create_app():

    # Create the configuration file if it does not exist
    if not os.path.exists(CONFIG_FILE):
        create_default_config(CONFIG_FILE)

    # Create flask app
    app = Flask(__name__)

    # Register the ledger service blueprints
    app.register_blueprint(ledger_blueprint, url_prefix='/ledger_api')

    # Register the tws service blueprints
    app.register_blueprint(connection_blueprint, url_prefix='/tws_api')
    app.register_blueprint(accounts_blueprint, url_prefix='/tws_api')
    app.register_blueprint(bulletins_blueprint, url_prefix='/tws_api')
    app.register_blueprint(options_blueprint, url_prefix='/tws_api')
    app.register_blueprint(stock_blueprint, url_prefix='/tws_api')

    return app
