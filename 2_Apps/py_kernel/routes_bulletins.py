from flask import Blueprint, jsonify, request

# import ib_api and api_thread from app.py
from app import ib_app

# Define the Blueprint here
bulletins_blueprint = Blueprint('bulletins', __name__)

@bulletins_blueprint.route('/bulletins', methods=['GET'])
def enable_bulletins():
    global ib_app
    if ib_app is None or ib_app.nextOrderId is None:
        return jsonify({"status": "error", "message": "Not connected to TWS"})

    # Request account updates
    ib_app.reqNewsBulletins(True)  # Get all bulletins (not just those marked for trading)

    # Renturn the message
    return jsonify({"status": "success", 
                    "message": "Bulletins enabled."})


@bulletins_blueprint.route('/bulletins', methods=['DELETE'])
def disable_bulletins():
    global ib_app
    if ib_app is None or ib_app.nextOrderId is None:
        return jsonify({"status": "error", "message": "Not connected to TWS"})
    
    # Request account updates
    ib_app.cancelNewsBulletins()

    # Renturn the message
    return jsonify({"status": "success", 
                    "message": "Bulletins disabled."})
