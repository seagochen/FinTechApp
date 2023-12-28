from decimal import Decimal

from ibapi.execution import Execution
from ibapi.order import Order
from ibapi.order_state import OrderState
from ibapi.ticktype import TickType

from ibapi.client import EClient
from ibapi.contract import Contract, ContractDetails
from ibapi.utils import decimalMaxString, floatMaxString, intMaxString
from ibapi.wrapper import EWrapper
from ibapi.common import HistogramDataList, ListOfDepthExchanges, ListOfHistoricalTickLast, SetOfString, SetOfFloat, \
    ListOfFamilyCode, ListOfContractDescription, FaDataType, BarData, ListOfHistoricalSessions, SmartComponentMap, \
    TickAttrib, TickAttribBidAsk, TickAttribLast, TickerId, OrderId, ListOfPriceIncrements

from ZeroMQHandler import ZeroMQPublisher as zmq
from Utilities import dict_to_json, list_to_json


class TWSClientWrapper_V1(EWrapper, EClient):

    def __init__(self, tws_ip, tws_port, tws_clientId=0, zmq_ip="127.0.0.1", zmq_port=1618):
        EClient.__init__(self, self)
        self.nextOrderId = None

        # Connect to ZeroMQ
        self.zmq_socket = zmq(zmq_ip, zmq_port)

        # Connect to TWS
        self.connect(tws_ip, tws_port, tws_clientId)

    def error(self, reqId, errorCode, errorString, advancedOrderRejectJson=None):
        if self.zmq_socket is not None:

            self.zmq_socket.send("error", dict_to_json({
                "reqId": reqId,
                "errorCode": errorCode,
                "errorString": errorString,
                "advancedOrderRejectJson": advancedOrderRejectJson
            }))
        else:
            print("Error. Id: ", reqId, " Code: ", errorCode, " Msg: ", errorString)


    ##################################### Account Portfolio Data #####################################

    def accountSummary(self, reqId: int, account: str, tag: str, value: str, currency: str):
        if self.zmq_socket is not None:

            self.zmq_socket.send("accountSummary", dict_to_json({
                "reqId": reqId,
                "account": account,
                "tag": tag,
                "value": value,
                "currency": currency
            }))
        else:
            print("AccountSummary. ReqId:", reqId, "Account:", account,
                  "Tag: ", tag, "Value:", value, "Currency:", currency)

    def accountSummaryEnd(self, reqId: int):
        if self.zmq_socket is not None:

            self.zmq_socket.send("accountSummaryEnd", dict_to_json({
                "reqId": reqId
            }))
        else:
            print("AccountSummaryEnd. Req Id: ", reqId)

    def updateAccountValue(self, key: str, val: str, currency: str, accountName: str):
        if self.zmq_socket is not None:

            self.zmq_socket.send("updateAccountValue", dict_to_json({
                "key": key,
                "val": val,
                "currency": currency,
                "accountName": accountName
            }))
        else:
            print("UpdateAccountValue. Key:", key, "Value:", val,
                  "Currency:", currency, "AccountName:", accountName)

    def updatePortfolio(self, contract: Contract, position: float, marketPrice: float, marketValue:
    float, averageCost: float, unrealizedPNL: float, realizedPNL: float, accountName: str):
        if self.zmq_socket is not None:

            self.zmq_socket.send("updatePortfolio", dict_to_json({
                "contract": contract,
                "position": position,
                "marketPrice": marketPrice,
                "marketValue": marketValue,
                "averageCost": averageCost,
                "unrealizedPNL": unrealizedPNL,
                "realizedPNL": realizedPNL,
                "accountName": accountName
            }))
        else:
            print("UpdatePortfolio.", "Symbol:", contract.symbol, "SecType:", contract.secType, "Exchange:",
                  contract.exchange, "Position:", position, "MarketPrice:", marketPrice,
                  "MarketValue:", marketValue, "AverageCost:", averageCost,
                  "UnrealizedPNL:", unrealizedPNL, "RealizedPNL:", realizedPNL,
                  "AccountName:", accountName)

    def updateAccountTime(self, timeStamp: str):
        if self.zmq_socket is not None:

            self.zmq_socket.send("updateAccountTime", dict_to_json({
                "timeStamp": timeStamp
            }))
        else:
            print("UpdateAccountTime. Time:", timeStamp)

    def accountDownloadEnd(self, accountName: str):
        if self.zmq_socket is not None:

            self.zmq_socket.send("accountDownloadEnd", dict_to_json({
                "accountName": accountName
            }))
        else:
            print("Account download finished:", accountName)

    def accountUpdateMulti(self, reqId: int, account: str, modelCode: str, key: str, value: str, currency: str):
        if self.zmq_socket is not None:

            self.zmq_socket.send("accountUpdateMulti", dict_to_json({
                "reqId": reqId,
                "account": account,
                "modelCode": modelCode,
                "key": key,
                "value": value,
                "currency": currency
            }))
        else:
            print("UpdateMultiAccountValue. ReqId:", reqId, "Account:", account,
                  "ModelCode:", modelCode, "Key:", key, "Value:", value, "Currency:", currency)

    def accountUpdateMultiEnd(self, reqId: int):
        if self.zmq_socket is not None:

            self.zmq_socket.send("accountUpdateMultiEnd", dict_to_json({
                "reqId": reqId
            }))
        else:
            print("AccountUpdateMultiEnd. ReqId:", reqId)

    def familyCodes(self, familyCodes: ListOfFamilyCode):

        if self.zmq_socket is not None:

            list_familyCodes = []
            for familyCode in familyCodes:
                list_familyCodes.append(familyCode)

            self.zmq_socket.send("familyCodes", dict_to_json({
                "familyCodes": list_to_json(list_familyCodes),
            }))
        else:
            for familyCode in familyCodes:
                print("FamilyCode.", familyCode)

    def managedAccounts(self, accountsList: str):
        if self.zmq_socket is not None:

            self.zmq_socket.send("managedAccounts", dict_to_json({
                "accountsList": accountsList
            }))
        else:
            print("Account list: ", accountsList)

    def position(self, account: str, contract: Contract, position: Decimal, avgCost: float):
        if self.zmq_socket is not None:

            self.zmq_socket.send("position", dict_to_json({
                "account": account,
                "contract": contract,
                "position": position,
                "avgCost": avgCost
            }))
        else:
            print("Position.", "Account:", account, "Symbol:", contract.symbol, "SecType:",
                  contract.secType, "Currency:", contract.currency,
                  "Position:", position, "Avg cost:", avgCost)

    def positionEnd(self):
        if self.zmq_socket is not None:

            self.zmq_socket.send("positionEnd", "")
        else:
            print("PositionEnd")

    def positionMulti(self, reqId: int, account: str, modelCode: str, contract: Contract, pos: Decimal, avgCost: float):
        if self.zmq_socket is not None:

            self.zmq_socket.send("positionMulti", dict_to_json({
                "reqId": reqId,
                "account": account,
                "modelCode": modelCode,
                "contract": contract,
                "pos": pos,
                "avgCost": avgCost
            }))
        else:
            print("PositionMulti.", "ReqId:", reqId, "Account:", account,
                  "ModelCode:", modelCode, "Symbol:", contract.symbol, "SecType:",
                  contract.secType, "Currency:", contract.currency,
                  "Position:", pos, "Avg cost:", avgCost)

    def pnlSingle(self, reqId: int, pos: Decimal, dailyPnL: float, unrealizedPnL: float, realizedPnL: float,
                  value: float):
        if self.zmq_socket is not None:

            self.zmq_socket.send("pnlSingle", dict_to_json({
                "reqId": reqId,
                "pos": pos,
                "dailyPnL": dailyPnL,
                "unrealizedPnL": unrealizedPnL,
                "realizedPnL": realizedPnL,
                "value": value
            }))
        else:
            print("PnLSingle. ReqId:", reqId, "Position:", pos,
                  "DailyPnL:", dailyPnL, "UnrealizedPnL:", unrealizedPnL,
                  "RealizedPnL:", realizedPnL, "Value:", value)

    def pnl(self, reqId: int, dailyPnL: float, unrealizedPnL: float, realizedPnL: float):
        if self.zmq_socket is not None:

            self.zmq_socket.send("pnl", dict_to_json({
                "reqId": reqId,
                "dailyPnL": dailyPnL,
                "unrealizedPnL": unrealizedPnL,
                "realizedPnL": realizedPnL
            }))
        else:
            print("PnL. ReqId:", reqId, "DailyPnL:", dailyPnL,
                  "UnrealizedPnL:", unrealizedPnL, "RealizedPnL:", realizedPnL)

    def userInfo(self, reqId: int, whiteBrandingId: str):
        if self.zmq_socket is not None:

            self.zmq_socket.send("userInfo", dict_to_json({
                "reqId": reqId,
                "whiteBrandingId": whiteBrandingId
            }))
        else:
            print("User Info. ReqId:", reqId, "White Branding Id:", whiteBrandingId)

    ##################################### Bulletins #####################################

    def updateNewsBulletin(self, msgId: int, msgType: int, newsMessage: str, originExch: str):
        if self.zmq_socket is not None:

            self.zmq_socket.send("updateNewsBulletin", dict_to_json({
                "msgId": msgId,
                "msgType": msgType,
                "newsMessage": newsMessage,
                "originExch": originExch
            }))
        else:
            print("News Bulletins. MsgId:", msgId, "Type:", msgType, "Message:", newsMessage,
                  "Exchange of Origin:", originExch)

    ##################################### Option Chains #####################################

    def securityDefinitionOptionParameter(self, reqId: int, exchange: str, underlyingConId: int, tradingClass: str,
                                          multiplier: str, expirations: SetOfString, strikes: SetOfFloat):
        if self.zmq_socket is not None:

            self.zmq_socket.send("securityDefinitionOptionParameter", dict_to_json({
                "reqId": reqId,
                "exchange": exchange,
                "underlyingConId": underlyingConId,
                "tradingClass": tradingClass,
                "multiplier": multiplier,
                "expirations": expirations,
                "strikes": strikes
            }))
        else:
            print("SecurityDefinitionOptionParameter.", "ReqId:", reqId, "Exchange:", exchange, "Underlying conId:",
                  underlyingConId, "TradingClass:", tradingClass, "Multiplier:", multiplier, "Expirations:",
                  expirations,
                  "Strikes:", str(strikes))

    ##################################### Stock Symbol Search #####################################

    def symbolSamples(self, reqId: int, contractDescriptions: ListOfContractDescription):
        if self.zmq_socket is not None:

            list_contractDescriptions = []
            for contractDescription in contractDescriptions:
                list_contractDescriptions.append(contractDescription)

            self.zmq_socket.send("symbolSamples", dict_to_json({
                "reqId": reqId,
                "contractDescriptions": list_to_json(list_contractDescriptions)
            }))
        else:
            print("SymbolSamples. ReqId:", reqId, "Contract descriptions:", len(contractDescriptions))
            for contractDescription in contractDescriptions:
                print("Contract description:", contractDescription)

    ##################################### Financial Advisors #####################################

    def receiveFA(self, faData: FaDataType, cxml: str):

        if self.zmq_socket is not None:

            self.zmq_socket.send("receiveFA", dict_to_json({
                "faData": faData,
                "cxml": cxml
            }))
        else:
            print("Receiving FA: ", faData)
            open('fa.xml', 'w').write(cxml)

    ##################################### Market Data Delay #####################################

    def headTimestamp(self, reqId, headTimestamp):

        if self.zmq_socket is not None:

            self.zmq_socket.send("headTimestamp", dict_to_json({
                "reqId": reqId,
                "headTimestamp": headTimestamp
            }))
        else:
            print("HeadTimestamp. ReqId:", reqId, "HeadTimeStamp:", headTimestamp)

    def historicalData(self, reqId: int, bar: BarData):

        if self.zmq_socket is not None:

            self.zmq_socket.send("historicalData", dict_to_json({
                "reqId": reqId,
                "bar": bar
            }))
        else:
            print("HistoricalData. ReqId:", reqId, "BarData.", bar)

    def historicalSchedule(self, reqId: int, startDateTime: str, endDateTime: str, timeZone: str,
                           sessions: ListOfHistoricalSessions):
        if self.zmq_socket is not None:

            list_sessions = []
            for session in sessions:
                list_sessions.append(session)

            self.zmq_socket.send("historicalSchedule", dict_to_json({
                "reqId": reqId,
                "startDateTime": startDateTime,
                "endDateTime": endDateTime,
                "timeZone": timeZone,
                "sessions": list_to_json(list_sessions)
            }))

    def historicalDataUpdate(self, reqId: int, bar: BarData):
        if self.zmq_socket is not None:

            self.zmq_socket.send("historicalDataUpdate", dict_to_json({
                "reqId": reqId,
                "bar": bar
            }))
        else:
            print("HistoricalDataUpdate. ReqId:", reqId, "BarData.", bar)

    def historicalDataEnd(self, reqId: int, start: str, end: str):
        if self.zmq_socket is not None:

            self.zmq_socket.send("historicalDataEnd", dict_to_json({
                "reqId": reqId,
                "start": start,
                "end": end
            }))
        else:
            print("HistoricalDataEnd. ReqId:", reqId, "from", start, "to", end)

    def histogramData(self, reqId: int, items: HistogramDataList):

        if self.zmq_socket is not None:

            self.zmq_socket.send("histogramData", dict_to_json({
                "reqId": reqId,
                "items": items
            }))
        else:
            print("HistogramData. ReqId:", reqId, "HistogramDataList.", items)

    def historicalTicks(self, reqId: int, ticks: ListOfHistoricalTickLast, done: bool):
        if self.zmq_socket is not None:

            list_ticks = []
            for tick in ticks:
                list_ticks.append(tick)

            self.zmq_socket.send("historicalTicks", dict_to_json({
                "reqId": reqId,
                "ticks": list_to_json(list_ticks),
                "done": done
            }))
        else:
            for tick in ticks:
                print("HistoricalTickLast. ReqId:", reqId, tick)

    def historicalTicksBidAsk(self, reqId: int, ticks: ListOfHistoricalTickLast, done: bool):
        if self.zmq_socket is not None:

            list_ticks = []
            for tick in ticks:
                list_ticks.append(tick)

            self.zmq_socket.send("historicalTicksBidAsk", dict_to_json({
                "reqId": reqId,
                "ticks": list_to_json(list_ticks),
                "done": done
            }))
        else:
            for tick in ticks:
                print("HistoricalTickBidAsk. ReqId:", reqId, tick)

    def historicalTicksLast(self, reqId: int, ticks: ListOfHistoricalTickLast, done: bool):
        if self.zmq_socket is not None:

            list_ticks = []
            for tick in ticks:
                list_ticks.append(tick)

            self.zmq_socket.send("historicalTicksLast", dict_to_json({
                "reqId": reqId,
                "ticks": list_to_json(list_ticks),
                "done": done
            }))
        else:
            for tick in ticks:
                print("HistoricalTickLast. ReqId:", reqId, tick)

    ##################################### Market Data Live #####################################

    def realtimeBar(self, reqId: TickerId, time: int, open_: float, high: float, low: float, close: float,
                    volume: Decimal, wap: Decimal, count: int):

        if self.zmq_socket is not None:

            self.zmq_socket.send("realtimeBar", dict_to_json({
                "reqId": reqId,
                "time": time,
                "open": open_,
                "high": high,
                "low": low,
                "close": close,
                "volume": volume,
                "wap": wap,
                "count": count
            }))
        else:
            print("RealTimeBars. ReqId:", reqId, "Time:", time, "Open:", open_, "High:", high, "Low:", low, "Close:",
                  close, "Volume:", volume, "WAP:", wap, "Count:", count)

    def smartComponents(self, reqId: int, smartComponentMap: SmartComponentMap):

        if self.zmq_socket is not None:

            self.zmq_socket.send("smartComponents", dict_to_json({
                "reqId": reqId,
                "smartComponentMap": smartComponentMap
            }))
        else:
            print("SmartComponents:")
            for smartComponent in smartComponentMap:
                print("SmartComponent.", smartComponent)

    def mktDepthExchanges(self, depthMktDataDescriptions: ListOfDepthExchanges):

        if self.zmq_socket is not None:

            list_depthMktDataDescriptions = []
            for depthMktDataDescription in depthMktDataDescriptions:
                list_depthMktDataDescriptions.append(depthMktDataDescription)

            self.zmq_socket.send("mktDepthExchanges", dict_to_json({
                "depthMktDataDescriptions": list_to_json(list_depthMktDataDescriptions)
            }))

    def updateMktDepth(self, reqId: TickerId, position: int, operation: int, side: int, price: float, size: Decimal):

        if self.zmq_socket is not None:

            self.zmq_socket.send("updateMktDepth", dict_to_json({
                "reqId": reqId,
                "position": position,
                "operation": operation,
                "side": side,
                "price": price,
                "size": decimalMaxString(size)
            }))
        else:
            print("UpdateMarketDepth. ReqId:", reqId, "Position:", position, "Operation:",
                  operation, "Side:", side, "Price:", price, "Size:", decimalMaxString(size))

    def updateMktDepthL2(self, reqId: TickerId, position: int, marketMaker: str, operation: int, side: int,
                         price: float, size: Decimal, isSmartDepth: bool):

        if self.zmq_socket is not None:

            self.zmq_socket.send("updateMktDepthL2", dict_to_json({
                "reqId": reqId,
                "position": position,
                "marketMaker": marketMaker,
                "operation": operation,
                "side": side,
                "price": price,
                "size": decimalMaxString(size),
                "isSmartDepth": isSmartDepth
            }))
        else:
            print("UpdateMarketDepthL2. ReqId:", reqId, "Position:", position, "MarketMaker:", marketMaker,
                  "Operation:",
                  operation, "Side:", side, "Price:", price, "Size:", decimalMaxString(size), "isSmartDepth:",
                  isSmartDepth)

    def tickOptionComputation(self, reqId: TickerId, tickType: TickType, tickAttrib: int, impliedVol: float,
                              delta: float, optPrice: float, pvDividend: float, gamma: float, vega: float, theta: float,
                              undPrice: float):

        if self.zmq_socket is not None:

            self.zmq_socket.send("tickOptionComputation", dict_to_json({
                "reqId": reqId,
                "tickType": tickType,
                "tickAttrib": intMaxString(tickAttrib),
                "impliedVol": floatMaxString(impliedVol),
                "delta": floatMaxString(delta),
                "optPrice": floatMaxString(optPrice),
                "pvDividend": floatMaxString(pvDividend),
                "gamma": floatMaxString(gamma),
                "vega": floatMaxString(vega),
                "theta": floatMaxString(theta),
                "undPrice": floatMaxString(theta)
            }))

        else:
            print("TickOptionComputation. TickerId:", reqId, "TickType:", tickType, "TickAttrib:",
                  intMaxString(tickAttrib),
                  "ImpliedVolatility:", floatMaxString(impliedVol), "Delta:", floatMaxString(delta), "OptionPrice:",
                  floatMaxString(optPrice),
                  "pvDividend:", floatMaxString(pvDividend), "Gamma:", floatMaxString(gamma), "Vega:",
                  floatMaxString(vega), "Theta:", floatMaxString(theta),
                  "UnderlyingPrice:", floatMaxString(undPrice))

    def tickSnapshotEnd(self, reqId: int):
        if self.zmq_socket is not None:

            self.zmq_socket.send("tickSnapshotEnd", dict_to_json({
                "reqId": reqId
            }))
        else:
            print("TickSnapshotEnd. TickerId:", reqId)

    def tickGeneric(self, reqId: TickerId, tickType: TickType, value: float):
        if self.zmq_socket is not None:

            self.zmq_socket.send("tickGeneric", dict_to_json({
                "reqId": reqId,
                "tickType": tickType,
                "value": floatMaxString(value)
            }))
        else:
            print("TickGeneric. TickerId:", reqId, "TickType:", tickType, "Value:", floatMaxString(value))

    def tickPrice(self, reqId: TickerId, tickType: TickType, price: float, attrib: TickAttrib):
        if self.zmq_socket is not None:

            self.zmq_socket.send("tickPrice", dict_to_json({
                "reqId": reqId,
                "tickType": tickType,
                "price": floatMaxString(price),
                "attrib": attrib
            }))
        else:
            print("TickPrice. TickerId:", reqId, "TickType:", tickType, "Price:", floatMaxString(price), "Attribs:",
                  attrib)

    def tickSize(self, reqId: TickerId, tickType: TickType, size: Decimal):
        if self.zmq_socket is not None:

            self.zmq_socket.send("tickSize", dict_to_json({
                "reqId": reqId,
                "tickType": tickType,
                "size": decimalMaxString(size)
            }))
        else:
            print("TickSize. TickerId:", reqId, "TickType:", tickType, "Size:", decimalMaxString(size))

    def tickString(self, reqId: TickerId, tickType: TickType, value: str):
        if self.zmq_socket is not None:

            self.zmq_socket.send("tickString", dict_to_json({
                "reqId": reqId,
                "tickType": tickType,
                "value": value
            }))
        else:
            print("TickString. TickerId:", reqId, "Type:", tickType, "Value:", value)

    def tickReqParams(self, tickerId: int, minTick: float, bboExchange: str, snapshotPermissions: int):
        if self.zmq_socket is not None:

            self.zmq_socket.send("tickReqParams", dict_to_json({
                "tickerId": tickerId,
                "minTick": floatMaxString(minTick),
                "bboExchange": bboExchange,
                "snapshotPermissions": intMaxString(snapshotPermissions)
            }))
        else:
            print("TickReqParams. TickerId:", tickerId, "MinTick:", floatMaxString(minTick),
                  "BboExchange:", bboExchange, "SnapshotPermissions:", intMaxString(snapshotPermissions))

    def rerouteMktDataReq(self, reqId: int, conId: int, exchange: str):
        if self.zmq_socket is not None:

            self.zmq_socket.send("rerouteMktDataReq", dict_to_json({
                "reqId": reqId,
                "conId": conId,
                "exchange": exchange
            }))
        else:
            print("Re-route market data request. ReqId:", reqId, "ConId:", conId, "Exchange:", exchange)

    def rerouteMktDepthReq(self, reqId: int, conId: int, exchange: str):
        if self.zmq_socket is not None:

            self.zmq_socket.send("rerouteMktDepthReq", dict_to_json({
                "reqId": reqId,
                "conId": conId,
                "exchange": exchange
            }))
        else:
            print("Re-route market depth request. ReqId:", reqId, "ConId:", conId, "Exchange:", exchange)

    def tickByTickAllLast(self, reqId: int, tickType: int, time: int, price: float, size: Decimal,
                          tickAtrribLast: TickAttribLast, exchange: str, specialConditions: str):
        if self.zmq_socket is not None:

            self.zmq_socket.send("tickByTickAllLast", dict_to_json({
                "reqId": reqId,
                "tickType": tickType,
                "time": time,
                "price": floatMaxString(price),
                "size": size,
                "tickAtrribLast": tickAtrribLast,
                "exchange": exchange,
                "specialConditions": specialConditions
            }))
        else:
            print("TickByTickAllLast. ReqId:", reqId, "TickType:", tickType, "Time:", time, "Price:",
                  floatMaxString(price),
                  "Size:", size, "TickAttribLast:", tickAtrribLast, "Exchange:", exchange, "SpecialConditions:",
                  specialConditions)

    def tickByTickBidAsk(self, reqId: int, time: int, bidPrice: float, askPrice: float, bidSize: Decimal,
                         askSize: Decimal, tickAttribBidAsk: TickAttribBidAsk):
        if self.zmq_socket is not None:

            self.zmq_socket.send("tickByTickBidAsk", dict_to_json({
                "reqId": reqId,
                "time": time,
                "bidPrice": decimalMaxString(bidPrice),
                "askPrice": decimalMaxString(askPrice),
                "bidSize": bidSize,
                "askSize": askSize,
                "tickAttribBidAsk": tickAttribBidAsk
            }))
        else:
            print("TickByTickBidAsk. ReqId:", reqId, "Time:", time, "BidPrice:", decimalMaxString(bidPrice),
                  "AskPrice:", decimalMaxString(askPrice),
                  "BidSize:", bidSize, "AskSize:", askSize, "TickAttribBidAsk:", tickAttribBidAsk)

    def tickByTickMidPoint(self, reqId: int, time: int, midPoint: float):
        if self.zmq_socket is not None:

            self.zmq_socket.send("tickByTickMidPoint", dict_to_json({
                "reqId": reqId,
                "time": time,
                "midPoint": floatMaxString(midPoint)
            }))
        else:
            print("TickByTickMidPoint. ReqId:", reqId, "Time:", time, "MidPoint:", floatMaxString(midPoint))

    ##################################### Market Scanner #####################################

    def scannerParameters(self, xml: str):

        if self.zmq_socket is not None:

            self.zmq_socket.send("scannerParameters", dict_to_json({
                "xml": xml
            }))
        else:
            open('log/scanner.xml', 'w').write(xml)
            print("ScannerParameters received.")

    def scannerData(self, reqId: int, rank: int, contractDetails: ContractDetails, distance: str, benchmark: str,
                    projection: str, legsStr: str):
        if self.zmq_socket is not None:

            self.zmq_socket.send("scannerData", dict_to_json({
                "reqId": reqId,
                "rank": rank,
                "contractDetails": contractDetails,
                "distance": distance,
                "benchmark": benchmark,
                "projection": projection,
                "legsStr": legsStr
            }))
        else:
            print("ScannerData. ReqId:", reqId, "Rank:", rank, "Symbol:", contractDetails.contract.symbol,
                  "SecType:", contractDetails.contract.secType,
                  "Currency:", contractDetails.contract.currency, "Distance:", distance, "Benchmark:", benchmark,
                  "Projection:", projection, "Legs String:", legsStr)

    def scannerDataEnd(self, reqId: int):

        if self.zmq_socket is not None:

            self.zmq_socket.send("scannerDataEnd", dict_to_json({
                "reqId": reqId
            }))
        else:
            print("ScannerDataEnd. Req Id:", reqId)

    ##################################### Order Management #####################################

    def execDetails(self, reqId: int, contract: Contract, execution: Execution):

        if self.zmq_socket is not None:
            self.zmq_socket.send("execDetails", dict_to_json({
                "reqId": reqId,
                "symbol": contract.symbol,
                "secType": contract.secType,
                "currency": contract.currency,
                "execution": execution}))
        else:
            print("ExecDetails. ReqId:", reqId, "Symbol:", contract.symbol, "SecType:", contract.secType,
                  "Currency:", contract.currency, execution)

    def execDetailsEnd(self, reqId: int):

        if self.zmq_socket is not None:

            self.zmq_socket.send("execDetailsEnd", dict_to_json({
                "reqId": reqId
            }))
        else:
            print("ExecDetailsEnd. ReqId:", reqId)

    def openOrder(self, orderId: OrderId, contract: Contract, order: Order, orderState: OrderState):

        if self.zmq_socket is not None:

            self.zmq_socket.send("openOrder", dict_to_json({
                "orderId": orderId,
                "symbol": contract.symbol,
                "secType": contract.secType,
                "currency": contract.currency,
                "Action": order.action,
                "OrderType": order.orderType,
                "TotalQty": order.totalQuantity,
                "LmtPrice": order.lmtPrice,
                "AuxPrice": order.auxPrice,
                "OrderState": orderState
            }))
        else:
            print("OpenOrder. PermId:", order.permId, "ClientId:", order.clientId, " OrderId:", orderId,
                  "Account:", order.account, "Symbol:", contract.symbol, "SecType:", contract.secType,
                  "Exchange:", contract.exchange, "Action:", order.action, "OrderType:", order.orderType,
                  "TotalQty:", order.totalQuantity, "CashQty:", order.cashQty,
                  "LmtPrice:", order.lmtPrice, "AuxPrice:", order.auxPrice, "OrderState:", orderState)

    def orderStatus(self, orderId: OrderId, status: str, filled: Decimal, remaining: Decimal,
                    avgFillPrice: float, permId: int, parentId: int, lastFillPrice: float,
                    clientId: int, whyHeld: str, mktCapPrice: float):

        super().orderStatus(orderId, status, filled, remaining, avgFillPrice, permId,
                            parentId, lastFillPrice, clientId, whyHeld, mktCapPrice)

        if self.zmq_socket is not None:

            self.zmq_socket.send("orderStatus", dict_to_json({
                "orderId": orderId,
                "status": status,
                "filled": filled,
                "remaining": remaining,
                "avgFillPrice": avgFillPrice,
                "permId": permId,
                "parentId": parentId,
                "lastFillPrice": lastFillPrice,
                "clientId": clientId,
                "whyHeld": whyHeld,
                "mktCapPrice": mktCapPrice
            }))
        else:

            print("OrderStatus. Id:", orderId, "Status:", status, "Filled:", filled,
                  "Remaining:", remaining, "AvgFillPrice:", avgFillPrice, "PermId:", permId,
                  "ParentId:", parentId, "LastFillPrice:", lastFillPrice, "ClientId:", clientId,
                  "WhyHeld:", whyHeld, "MktCapPrice:", mktCapPrice)

    def orderBound(self, orderId: int, apiClientId: int, apiOrderId: int):

        if self.zmq_socket is not None:

            self.zmq_socket.send("orderBound", dict_to_json({
                "orderId": orderId,
                "apiClientId": apiClientId,
                "apiOrderId": apiOrderId
            }))
        else:
            print("OrderBound. OrderId:", orderId, "ApiClientId:", apiClientId, "ApiOrderId:", apiOrderId)

    ##################################### Orders #####################################

    def marketRule(self, marketRuleId: int, priceIncrements: ListOfPriceIncrements):

        if self.zmq_socket is not None:

            list_priceIncrements = []
            for priceIncrement in priceIncrements:
                list_priceIncrements.append(priceIncrement)

            self.zmq_socket.send("marketRule", dict_to_json({
                "marketRuleId": marketRuleId,
                "priceIncrements": list_to_json(list_priceIncrements)
            }))

        else:
            print("Market Rule Id: ", marketRuleId)
            for priceIncrement in priceIncrements:
                print("Price Increment.", priceIncrement)

    ##################################### News #####################################

    def tickNews(self, tickerId: int, timeStamp: int, providerCode: str, articleId: str, headline: str, extraData: str):

        if self.zmq_socket is not None:

            self.zmq_socket.send("tickNews", dict_to_json({
                "tickerId": tickerId,
                "timeStamp": timeStamp,
                "providerCode": providerCode,
                "articleId": articleId,
                "headline": headline,
                "extraData": extraData
            }))
        else:
            print("TickNews. TickerId:", tickerId, "TimeStamp:", timeStamp, "ProviderCode:", providerCode,
                  "ArticleId:", articleId, "Headline:", headline, "ExtraData:", extraData)

    def historicalNews(self, requestId: int, time: int, providerCode: str, articleId: str, headline: str):

        if self.zmq_socket is not None:

            self.zmq_socket.send("historicalNews", dict_to_json({
                "requestId": requestId,
                "time": time,
                "providerCode": providerCode,
                "articleId": articleId,
                "headline": headline
            }))
        else:
            print("HistoricalNews. ReqId:", requestId, "Time:", time, "ProviderCode:", providerCode,
                "ArticleId:", articleId, "Headline:", headline)

    def historicalNewsEnd(self, reqId: int, hasMore: bool):

        if self.zmq_socket is not None:

            self.zmq_socket.send("historicalNewsEnd", dict_to_json({
                "reqId": reqId,
                "hasMore": hasMore
            }))
        else:
            print("HistoricalDataEnd. ReqId:", reqId, "HasMore:", hasMore)

    def newsArticle(self, requestId: int, articleType: int, articleText: str):

        if self.zmq_socket is not None:

            self.zmq_socket.send("newsArticle", dict_to_json({
                "requestId": requestId,
                "articleType": articleType,
                "articleText": articleText
            }))
        else:
            print("NewsArticle. ReqId:", requestId, "ArticleType:", articleType, "ArticleText:", articleText)

    ##################################### Next Valid Id #####################################

    def nextValidId(self, orderId: int):
        self.nextOrderId = orderId
        print("NextValidId:", orderId)

    ##################################### TWS UI Display Groups #####################################

    def displayGroupList(self, reqId: int, groups: str):
        if self.zmq_socket is not None:

            self.zmq_socket.send("displayGroupList", dict_to_json({
                "reqId": reqId,
                "groups": groups
            }))
        else:
            print("DisplayGroupList. ReqId:", reqId, "Groups", groups)

    def displayGroupUpdated(self, reqId: int, contractInfo: str):
        if self.zmq_socket is not None:

            self.zmq_socket.send("displayGroupUpdated", dict_to_json({
                "reqId": reqId,
                "contractInfo": contractInfo
            }))
        else:
            print("displayGroupUpdated. ReqId:", reqId, "ContractInfo:", contractInfo)

    ##################################### Wallstreet Horizons #####################################

    def wshMetaData(self, reqId: int, dataJson: str):
        if self.zmq_socket is not None:

            self.zmq_socket.send("wshMetaData", dict_to_json({
                "reqId": reqId,
                "dataJson": dataJson
            }))
        else:
            print("wshMetaData. ReqId:", reqId, "DataJson:", dataJson)

    def wshEventData(self, reqId: int, dataJson: str):

        if self.zmq_socket is not None:

            self.zmq_socket.send("wshEventData", dict_to_json({
                "reqId": reqId,
                "dataJson": dataJson
            }))
        else:
            print("wshEventData. ReqId:", reqId, "DataJson:", dataJson)
