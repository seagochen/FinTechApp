from flask import Flask
# from flask_sqlalchemy import SQLAlchemy

# Register the views
from app.apis.LedgerServiceAPIs import ledger_service


def create_app():
    # Create flask app
    app = Flask(__name__)

    # Register blueprint
    app.register_blueprint(ledger_service)

    return app
