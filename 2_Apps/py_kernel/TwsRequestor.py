from decimal import Decimal

from ibapi.client import EClient
from ibapi.contract import Contract
from ibapi.utils import decimalMaxString, floatMaxString
from ibapi.wrapper import EWrapper


# TestApp for interacting with TWS API
class TestApp(EWrapper, EClient):
    def __init__(self):
        EClient.__init__(self, self)
        self.nextOrderId = None
        self.feedback = []

    def error(self, reqId, errorCode, errorString, advancedOrderRejectJson=None):
        print("Error: ", reqId, " ", errorCode, " ", errorString, " ", advancedOrderRejectJson)

    def cleanup(self):
        self.feedback = []

    """
    以下函数用于请求账户信息
    EClient reqAccountSummary() 之后，返回的信息
    例如：EClient.reqAccountSummary(9001, "All", AccountSummaryTags.AllTags)
    
    以下函数用户停止请求账户信息
    EClient::cancelAccountSummary() 之后的返回函数
    
    接下来的函数是reqAccountSummary调用之后的返回函数
    """

    def accountSummary(self, reqId: int, account: str, tag: str, value: str, currency: str):
        """
        Receives the account information.
        This method will receive the account information just as it appears in the TWS’ Account Summary Window.
        """
        self.feedback.append({
            "method": "accountSummary",
            "reqId": reqId,
            "account": account,
            "tag": tag,
            "value": value,
            "currency": currency
        })

    def accountSummaryEnd(self, reqId: int):
        """
        This method is called once all account information for a given request are received.
        """
        self.feedback.append({
            "method": "accountSummaryEnd",
            "reqId": reqId
        })

    """
    
    """




    def updatePortfolio(self, contract: Contract, position: Decimal, marketPrice: float, marketValue: float,
                        averageCost: float, unrealizedPNL: float, realizedPNL: float, accountName: str):
        """
        该函数在通过调用
        """
        self.portfolio[contract.symbol] = {
            "position": decimalMaxString(position),
            "marketPrice": floatMaxString(marketPrice),
            "marketValue": floatMaxString(marketValue),
            "averageCost": floatMaxString(averageCost),
            "unrealizedPNL": floatMaxString(unrealizedPNL),
            "realizedPNL": floatMaxString(realizedPNL),
            "accountName": accountName
        }

    def position(self, account, contract, position, avgCost):
        self.positions[contract.symbol] = {"position": position, "avgCost": avgCost}

    def nextValidId(self, orderId: int):
        self.nextOrderId = orderId

    def orderStatus(self, orderId, status, filled, remaining, avgFillPrice, permId,
                    parentId, lastFillPrice, clientId, whyHeld, mktCapPrice):
        self.order_responses.append({
            "orderId": orderId,
            "status": status,
            "filled": filled,
            "remaining": remaining,
            "avgFillPrice": avgFillPrice
        })
