from decimal import Decimal

from ibapi.client import EClient
from ibapi.contract import Contract
from ibapi.utils import decimalMaxString, floatMaxString
from ibapi.wrapper import EWrapper

import zmq

class TWSClientWrapper_V1(EWrapper, EClient):

    def __init__(self, zmq_socket):
        EClient.__init__(self, self)
        self.nextOrderId = None
        self.zmq_socket = zmq_socket

    def error(self, reqId, errorCode, errorString, advancedOrderRejectJson=None):
        if self.zmq_socket is not None:
            self.zmq_socket.send_json({
                "method": "error",
                "reqId": reqId,
                "errorCode": errorCode,
                "errorString": errorString,
                "advancedOrderRejectJson": advancedOrderRejectJson
            })
        else:
            print("Error: ", reqId, " ", errorCode, " ", errorString, " ", advancedOrderRejectJson)

    """
    以下函数用于请求账户信息
    EClient reqAccountSummary() 之后，返回的信息
    例如：EClient.reqAccountSummary(9001, "All", AccountSummaryTags.AllTags)

    以下函数用户停止请求账户信息
    EClient::cancelAccountSummary() 之后的返回函数

    接下来的函数是reqAccountSummary调用之后的返回函数
    """
