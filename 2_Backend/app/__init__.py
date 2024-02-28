from flask import Flask

from app.tws.routes_connection import connection_blueprint
from app.tws.routes_accounts import accounts_blueprint
from app.tws.routes_bulletins import bulletins_blueprint
from app.tws.routes_option import options_blueprint
from app.tws.routes_stock import stock_blueprint


def create_app():

    # Create flask app
    app = Flask(__name__)

    # Register blueprint
    from app.Views import bp
    app.register_blueprint(bp)

    # Register the connections for the tws
    app.register_blueprint(connection_blueprint, url_prefix='/api')
    app.register_blueprint(accounts_blueprint, url_prefix='/api')
    app.register_blueprint(bulletins_blueprint, url_prefix='/api')
    app.register_blueprint(options_blueprint, url_prefix='/api')
    app.register_blueprint(stock_blueprint, url_prefix='/api')

    return app