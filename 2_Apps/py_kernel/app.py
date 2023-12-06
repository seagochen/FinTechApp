from flask import Flask, request, jsonify

from ibapi.contract import Contract
from ibapi.order import Order
import threading
import time

app = Flask(__name__)



# Global instance of your TestApp
ib_app = TestApp()
api_thread = None


@app.route('/config/set', methods=['GET'])
def set_config():
    global ib_app, api_thread
    ip = request.args.get('ip', default="127.0.0.1")
    port = request.args.get('port', default=7496, type=int)
    clientId = request.args.get('clientId', default=0, type=int)

    if not api_thread is None:
        ib_app.disconnect()
        api_thread.join()

    ib_app = TestApp()
    ib_app.connect(ip, port, clientId)

    api_thread = threading.Thread(target=ib_app.run, daemon=True)
    api_thread.start()
    time.sleep(1)  # Allow time for connection to establish

    return jsonify({"status": "success", "message": "Configuration set and connected"})


@app.route('/account/balance', methods=['GET'])
def account_balance():
    global ib_app
    if ib_app is None or ib_app.nextOrderId is None:
        return jsonify({"error": "Not connected to TWS"})

    # Request account values
    ib_app.reqAccountUpdates(True, "")

    # Add a delay or implement a more sophisticated method to wait for the response
    time.sleep(3)

    # Stop the account updates
    ib_app.reqAccountUpdates(False, "")

    # Return the account values
    return jsonify(ib_app.accountValues)


@app.route('/account/positions', methods=['GET'])
def account_summary():
    global ib_app
    if ib_app is None or ib_app.nextOrderId is None:
        return jsonify({"error": "Not connected to TWS"})

    # Request positions
    ib_app.reqPositions()

    # Add a delay or implement a more sophisticated method to wait for the response
    time.sleep(3)

    # Return the positions
    return jsonify(ib_app.positions)


@app.route('/contract/<action>', methods=['GET'])
def trade_contract(action):
    global ib_app
    if action not in ['buy', 'sell']:
        return jsonify({"error": "Invalid action"})

    if ib_app is None or ib_app.nextOrderId is None:
        return jsonify({"error": "Not connected to TWS"})

    symbol = request.args.get('symbol')
    secType = request.args.get('secType', 'STK')
    exchange = request.args.get('exchange', 'SMART')
    currency = request.args.get('currency', 'USD')
    totalQuantity = int(request.args.get('totalQuantity', 1))
    orderType = request.args.get('orderType', 'MKT')
    lmtPrice = float(request.args.get('lmtPrice', 0))

    contract = Contract()
    contract.symbol = symbol
    contract.secType = secType
    contract.exchange = exchange
    contract.currency = currency

    order = Order()
    order.action = "BUY" if action == 'buy' else "SELL"
    order.totalQuantity = totalQuantity
    order.orderType = orderType
    if orderType == 'LMT':
        order.lmtPrice = lmtPrice

    ib_app.placeOrder(ib_app.nextOrderId, contract, order)
    ib_app.nextOrderId += 1

    return jsonify({"status": "success", "message": f"Order to {action} {totalQuantity} of {symbol} placed"})


@app.route('/contract/status', methods=['GET'])
def order_status():
    global ib_app
    if ib_app is None:
        return jsonify({"error": "Not connected to TWS"})

    # Return a mock response for now
    return jsonify({"status": "success", "orders": ib_app.order_responses})


if __name__ == '__main__':
    app.run(debug=True)
