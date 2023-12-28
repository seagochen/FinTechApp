from flask import Blueprint, jsonify, request

# import ib_api and api_thread from app.py
from app import ib_app

# Define the Blueprint here
options_blueprint = Blueprint('options', __name__)

@options_blueprint.route('/query_option_chain', methods=['GET'])
def query_option_chain():
    global ib_app
    if ib_app is None or ib_app.nextOrderId is None:
        return jsonify({"status": "error", "message": "Not connected to TWS"})

    # Get parameters from the request
    requestId = request.args.get('requestId', default=1000)
    underlyingSymbol = request.args.get('underlyingSymbol')
    futFopExchange = request.args.get('futFopExchange')
    underlyingSecType = request.args.get('underlyingSecType')
    underlyingConId = request.args.get('underlyingConId')

    # Request account updates
    ib_app.reqSecDefOptParams(requestId, underlyingSymbol, futFopExchange, underlyingSecType, underlyingConId)

    # Renturn the message
    return jsonify({"status": "success", 
                    "message": "Options enabled."})
