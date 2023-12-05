from ibapi.client import EClient
from ibapi.wrapper import EWrapper
from ibapi.contract import Contract
from ibapi.order import Order
import threading
import time


class TestApp(EWrapper, EClient):
    def __init__(self):
        EClient.__init__(self, self)

    def error(self, reqId, errorCode, errorString, advancedOrderRejectJson=None):
        print("Error: ", reqId, " ", errorCode, " ", errorString, " ", advancedOrderRejectJson)

    def nextValidId(self, orderId: int):
        self.nextOrderId = orderId
        self.start()

    def accountSummary(self, reqId, account, tag, value, currency):
        """ This method is called to report an account summary """
        print("Account:", account, "Tag:", tag, "Value:", value, "Currency:", currency)
        if tag == "TotalCashValue":
            print(f"Total Cash Value: {value} {currency}")

    def accountSummaryEnd(self, reqId):
        """ This method is called after all account summary data for a request are received """
        print("Account summary retrieval complete.")
        self.disconnect()

    def place_order(self, symbol, action, quantity):
        contract = Contract()
        contract.symbol = symbol
        contract.secType = "STK"
        contract.exchange = "SMART"
        contract.currency = "USD"

        order = Order()
        order.action = action
        order.totalQuantity = quantity
        order.orderType = "MKT"  # Market order

        self.placeOrder(self.nextOrderId, contract, order)
        self.nextOrderId += 1

    def start(self):
        contract = Contract()
        contract.symbol = "AAPL"
        contract.secType = "STK"
        contract.exchange = "SMART"
        contract.currency = "USD"

        order = Order()
        order.action = "BUY"
        order.totalQuantity = 1
        order.orderType = "LMT"
        order.lmtPrice = 100

        self.placeOrder(self.nextOrderId, contract, order)
        self.nextOrderId += 1

        time.sleep(3)
        self.disconnect()

    def position(self, account, contract, position, avgCost):
        """ This method is called to report a position in an account """
        print("Account:", account, "Contract:", contract.symbol, "Position:", position, "Average Cost:", avgCost)

    def positionEnd(self):
        """ This method is called after all position data for a request are received """
        print("Position data retrieval complete.")
        self.disconnect()


def main():
    app = TestApp()
    app.connect("127.0.0.1", 7496, 0)

    # Start a separate thread to run the communication loop
    api_thread = threading.Thread(target=app.run, daemon=True)
    api_thread.start()

    # Wait for connection to establish
    time.sleep(1)

    # Request positions
    # app.reqPositions()

    # Request account summary
    # The request ID can be any number, and the "All" parameter specifies that we want all accounts
    # app.reqAccountSummary(1, "All", "$LEDGER")

    while api_thread.is_alive():
        time.sleep(1)


if __name__ == "__main__":
    main()
