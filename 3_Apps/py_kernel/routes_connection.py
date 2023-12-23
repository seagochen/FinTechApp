from flask import Blueprint, jsonify, request
from TWSClientWrapper_V1 import TWSClientWrapper_V1 as IBClient
import threading
import time

# import ib_api and api_thread from app.py
from app import ib_app, api_thread

# Define the Blueprint here
connection_blueprint = Blueprint('connection', __name__)

# Define the Flask routes here
@connection_blueprint.route('/connection', methods=['GET'])
def connect_tws():
    global ib_app, api_thread
    ip = request.args.get('ip', default="127.0.0.1")
    port = request.args.get('port', default=7496, type=int)

    # if the client is already connected, disconnect it first
    if not api_thread is None:
        ib_app.disconnect()
        api_thread.join()

    # Create a new instance of TWSClientWrapper_V1
    ib_app = IBClient(ip, port)
    
    # Create a new thread to run the TWSClientWrapper_V1
    api_thread = threading.Thread(target=ib_app.run, daemon=True)
    api_thread.start()

    # Allow time for connection to establish
    time.sleep(1)

    # Get the connection status
    if ib_app.nextOrderId is None:
        return jsonify({"status": "error", "message": "Not connected to TWS"})
    else:
        return jsonify({"status": "success", 
                        "message": "You can use port 5555 to listen to the events."})

@connection_blueprint.route('/connection', methods=['DELETE'])
def disconnect_tws():
    global ib_app
    if ib_app is None or ib_app.nextOrderId is None:
        return jsonify({"status": "error", "message": "Not connected to TWS"})
    
    # Disconnect the client
    ib_app.disconnect()

    # Renturn the message
    return jsonify({"status": "success", 
                    "message": "Disconnected from TWS"})