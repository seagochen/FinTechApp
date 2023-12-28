from flask import Blueprint, jsonify, request

from ibapi.account_summary_tags import AccountSummaryTags

# import ib_api and api_thread from app.py
from app import ib_app

# Define the Blueprint here
accounts_blueprint = Blueprint('accounts', __name__)

@accounts_blueprint.route('/account_portfolio', methods=['GET'])
def enable_account_portfolio():
    global ib_app
    if ib_app is None or ib_app.nextOrderId is None:
        return jsonify({"status": "error", "message": "Not connected to TWS"})
    
    # Request account updates
    ib_app.reqAccountSummary(9001, "All", AccountSummaryTags.AllTags)

    # Renturn the message
    return jsonify({"status": "success", 
                    "message": "Account updates enabled."})

@accounts_blueprint.route('/account_portfolio', methods=['DELETE'])
def disable_account_portfolio():
    global ib_app
    if ib_app is None or ib_app.nextOrderId is None:
        return jsonify({"status": "error", "message": "Not connected to TWS"})
    
    # Request account updates
    ib_app.cancelAccountSummary(9001)

    # Renturn the message
    return jsonify({"status": "success", 
                    "message": "Account updates disabled."})

@accounts_blueprint.route('/account_updates', methods=['GET'])
def enable_account_updates():
    global ib_app
    if ib_app is None or ib_app.nextOrderId is None:
        return jsonify({"status": "error", "message": "Not connected to TWS"})

    # Request account updates
    ib_app.reqAccountUpdates(True, "")

    # Renturn the message
    return jsonify({"status": "success", 
                    "message": "Account updates enabled."})

@accounts_blueprint.route('/account_updates', methods=['DELETE'])
def disable_account_updates():
    global ib_app
    if ib_app is None or ib_app.nextOrderId is None:
        return jsonify({"status": "error", "message": "Not connected to TWS"})
    
    # Request account updates
    ib_app.reqAccountUpdates(False, "")

    # Renturn the message
    return jsonify({"status": "success", 
                    "message": "Account updates disabled."})

@accounts_blueprint.route('/family_code', methods=['GET'])
def get_family_code():
    global ib_app
    if ib_app is None or ib_app.nextOrderId is None:
        return jsonify({"status": "error", "message": "Not connected to TWS"})

    # Request family code
    ib_app.reqFamilyCodes()

    # Renturn the message
    return jsonify({"status": "success", 
                    "message": "Family code requested."})

@accounts_blueprint.route('/managed_accounts', methods=['GET'])
def get_managed_accounts():
    global ib_app
    if ib_app is None or ib_app.nextOrderId is None:
        return jsonify({"status": "error", "message": "Not connected to TWS"})

    # Request managed accounts
    ib_app.reqManagedAccts()

    # Renturn the message
    return jsonify({"status": "success", 
                    "message": "Managed accounts requested."})

@accounts_blueprint.route('/positions', methods=['GET'])
def enable_positions_update():
    global ib_app
    if ib_app is None or ib_app.nextOrderId is None:
        return jsonify({"status": "error", "message": "Not connected to TWS"})

    # Request positions
    ib_app.reqPositions()

    # Renturn the message
    return jsonify({"status": "success", 
                    "message": "Positions requested."})

@accounts_blueprint.route('/positions', methods=['DELETE'])
def disable_positions_update():
    global ib_app
    if ib_app is None or ib_app.nextOrderId is None:
        return jsonify({"status": "error", "message": "Not connected to TWS"})

    # Request positions
    ib_app.cancelPositions()

    # Renturn the message
    return jsonify({"status": "success", 
                    "message": "Positions cancelled."})

@accounts_blueprint.route('/account_profit_pl', methods=['GET'])
def enable_profit_pl_update():
    global ib_app
    if ib_app is None or ib_app.nextOrderId is None:
        return jsonify({"status": "error", "message": "Not connected to TWS"})

    # Get parameters from the request
    requestId = request.args.get('requestId', default=1000)
    account = request.args.get('account', default="DU1234567")
    modelCode = request.args.get('modelCode', default="")
    contractId = request.args.get('contractId', default=0, type=int)

    # Request P&L
    ib_app.reqPnLSingle(requestId, account, modelCode, contractId)

    # Renturn the message
    return jsonify({"status": "success", 
                    "message": "Position P&L requested."})

@accounts_blueprint.route('/account_profit_pl', methods=['DELETE'])
def disable_profit_pl_update():
    global ib_app
    if ib_app is None or ib_app.nextOrderId is None:
        return jsonify({"status": "error", "message": "Not connected to TWS"})

    # Get parameters from the request
    requestId = request.args.get('requestId', default=1000)

    # Request P&L
    ib_app.cancelPnLSingle(requestId)

    # Renturn the message
    return jsonify({"status": "success", 
                    "message": "Position P&L cancelled."})
