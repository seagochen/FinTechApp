from flask import Flask
from routes_connection import connection_blueprint
from routes_accounts import accounts_blueprint
from routes_bulletins import bulletins_blueprint
from routes_options import options_blueprint
from routes_stock import stock_blueprint

# Define TWSClientWrapper_V1 here
ib_app = None
api_thread = None

app = Flask(__name__)

# Register the blueprints
app.register_blueprint(connection_blueprint, url_prefix='/api')
app.register_blueprint(accounts_blueprint, url_prefix='/api')
app.register_blueprint(bulletins_blueprint, url_prefix='/api')
app.register_blueprint(options_blueprint, url_prefix='/api')
app.register_blueprint(stock_blueprint, url_prefix='/api')

# Enable CORS
from flask_cors import CORS
CORS(app)

# Run the app
app.run(debug=True, port=5000)