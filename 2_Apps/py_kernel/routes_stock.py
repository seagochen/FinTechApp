from flask import Blueprint, jsonify, request

# import ib_api and api_thread from app.py
from app import ib_app

# Define the Blueprint here
stock_blueprint = Blueprint('stock', __name__)

@stock_blueprint.route('/query_stock_contract', methods=['GET'])
def query_stock_contract():
    global ib_app
    if ib_app is None or ib_app.nextOrderId is None:
        return jsonify({"status": "error", "message": "Not connected to TWS"})
    
    # Get parameters from the request
    requestId = request.args.get('requestId', default=1000)
    pattern = request.args.get('pattern')

    # Query the stock contract
    ib_app.reqMatchingSymbols(requestId, pattern)

    # Return the contract
    return jsonify({"status": "success", 
                    "message": "Stock contract returned."})
