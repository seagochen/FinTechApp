from flask import Flask
from routes_connection import connection_blueprint
from routes_accounts import accounts_blueprint
from routes_bulletins import bulletins_blueprint
from routes_option import options_blueprint
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
@app.after_request
def after_request(response):
    response.headers.add('Access-Control-Allow-Origin', 'http://localhost:3000')
    response.headers.add('Access-Control-Allow-Headers', 'Content-Type,Authorization')
    response.headers.add('Access-Control-Allow-Methods', 'GET,POST,DELETE')
    response.headers.add('Access-Control-Allow-Credentials', 'true')
    return response


# Run the app on port 5000
if __name__ == '__main__':
    app.run(host='127.0.0.1', port=5000)
