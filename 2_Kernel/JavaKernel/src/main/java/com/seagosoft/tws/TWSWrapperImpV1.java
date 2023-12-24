package com.seagosoft.tws;

import com.ib.client.*;

import com.google.gson.Gson;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.seagosoft.tws.data.*;
import com.seagosoft.zmq.ZeroMQServerHandler;

public class TWSWrapperImpV1 implements EWrapper {

    private EReaderSignal readerSignal;
    private EClientSocket clientSocket;
    protected int currentOrderId = -1;

    private ZeroMQServerHandler zmqServerHandler;


    /**
     * Constructor
     * @param twsIp
     * @param twsPort
     * @param clientId
     * @param zeroMqServerUrl
     * @param zeroMqPort
     */
    public TWSWrapperImpV1(String twsIp, int twsPort, int clientId, String zeroMqServerUrl, int zeroMqPort) {

        // Create the client and connect to TWS
        readerSignal = new EJavaSignal();
        clientSocket = new EClientSocket(this, readerSignal);
        
        // Create the zmq server handler
        zmqServerHandler = new ZeroMQServerHandler(zeroMqServerUrl, zeroMqPort);

        // Connect to TWS
        clientSocket.eConnect(twsIp, twsPort, clientId);
    }

    /**
     *  Overloaded constructor with default values for clientId, zeroMqServerUrl, and zeroMqPort
     */
    public TWSWrapperImpV1(String twsIp, int twsPort) {
        this(twsIp, twsPort, 1, "127.0.0.1", 5555);
    }


    /**
     *
     * @param tickerId
     * @param field
     * @param price
     * @param attribs
     */
    @Override
    public void tickPrice(int tickerId, int field, double price, TickAttrib attribs) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new TickPriceData(tickerId, field, price, attribs));
            zmqServerHandler.send("TickPrice", json);
        } else {
            System.out.println("TickPrice: " + EWrapperMsgGenerator.tickPrice( tickerId, field, price, attribs));
        }
    }

    /**
     *
     * @param tickerId
     * @param field
     * @param size
     */
    @Override
    public void tickSize(int tickerId, int field, Decimal size) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new TickSizeData(tickerId, field, size));
            zmqServerHandler.send("TickSize", json);
        } else {
            System.out.println("TickSize: " + EWrapperMsgGenerator.tickSize( tickerId, field, size));
        }
    }

    /**
     *
     * @param tickerId
     * @param field
     * @param tickAttrib
     * @param impliedVol
     * @param delta
     * @param optPrice
     * @param pvDividend
     * @param gamma
     * @param vega
     * @param theta
     * @param undPrice
     */
    @Override
    public void tickOptionComputation(int tickerId, int field, int tickAttrib, double impliedVol, double delta, double optPrice,
                                      double pvDividend, double gamma, double vega, double theta, double undPrice) {

        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new TickOptionComputationData(tickerId, field, tickAttrib, impliedVol, delta, optPrice,
                    pvDividend, gamma, vega, theta, undPrice));
            zmqServerHandler.send("TickOptionComputation", json);
        } else {
            System.out.println("TickOptionComputation: " + 
            EWrapperMsgGenerator.tickOptionComputation( tickerId, field, tickAttrib, impliedVol, 
            delta, optPrice, pvDividend, gamma, vega, theta, undPrice));
        }
    }

    /**
     * @param tickerId
     * @param tickType
     * @param value
     */
    @Override
    public void tickGeneric(int tickerId, int tickType, double value) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new tickGenericData(tickerId, tickType, value));
            zmqServerHandler.send("TickGeneric", json);
        } else {
            System.out.println("TickGeneric: " + EWrapperMsgGenerator.tickGeneric(tickerId, tickType, value));
        }
    }


    /**
     * @param tickerId
     * @param tickType
     * @param value
     */
    @Override
    public void tickString(int tickerId, int tickType, String value) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new tickStringData(tickerId, tickType, value));
            zmqServerHandler.send("TickString", json);
        } else {
            System.out.println("TickString: " + EWrapperMsgGenerator.tickString(tickerId, tickType, value));
        }
    }


    /**
     * 
     * @param tickerId
     * @param tickType
     * @param basisPoints
     * @param formattedBasisPoints
     * @param impliedFuture
     * @param holdDays
     * @param futureExpiry
     * @param dividendImpact
     * @param dividendsToExpiry
     */
    @Override
    public void tickEFP(int tickerId, int tickType, double basisPoints, String formattedBasisPoints, double impliedFuture, int holdDays,
                        String futureExpiry, double dividendImpact, double dividendsToExpiry) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new TickEFPData(tickerId, tickType, basisPoints, formattedBasisPoints, impliedFuture,
                    holdDays, futureExpiry, dividendImpact, dividendsToExpiry));
            zmqServerHandler.send("TickEFP", json);
        } else {
            System.out.println("TickEFP: " + EWrapperMsgGenerator.tickEFP(tickerId, tickType, basisPoints, formattedBasisPoints, impliedFuture,
                    holdDays, futureExpiry, dividendImpact, dividendsToExpiry));
        }
    }

    /**
     *
     * @param orderId
     * @param status
     * @param filled
     * @param remaining
     * @param avgFillPrice
     * @param permId
     * @param parentId
     * @param lastFillPrice
     * @param clientId
     * @param whyHeld
     * @param mktCapPrice
     */
    @Override
    public void orderStatus(int orderId, String status,
                            Decimal filled, Decimal remaining, double avgFillPrice,
                            int permId, int parentId, double lastFillPrice, int clientId,
                            String whyHeld, double mktCapPrice) {

        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new OrderStatusData(orderId, status, filled, remaining,
                    avgFillPrice, permId, parentId, lastFillPrice, clientId, whyHeld, mktCapPrice));
            zmqServerHandler.send("OrderStatus", json);
        } else {
            System.out.println("OrderStatus: " +
                    EWrapperMsgGenerator.orderStatus(orderId, status, filled, remaining,
                            avgFillPrice, permId, parentId, lastFillPrice, clientId, whyHeld, mktCapPrice));
        }

        System.out.println(EWrapperMsgGenerator.orderStatus( orderId, status, filled, remaining, avgFillPrice, permId, parentId, lastFillPrice, clientId, whyHeld, mktCapPrice));
    }

    /**
     *
     * @param orderId
     * @param contract
     * @param order
     * @param orderState
     */
    @Override
    public void openOrder(int orderId, Contract contract, Order order, OrderState orderState) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new OpenOrderData(orderId, contract, order, orderState));
            zmqServerHandler.send("OpenOrder", json);
        } else {
            System.out.println("OpenOrder: " +
                    EWrapperMsgGenerator.openOrder(orderId, contract, order, orderState));
        }
    }

    /**
     *
     */
    @Override
    public void openOrderEnd() {
        if (zmqServerHandler != null) {
            zmqServerHandler.send("OpenOrderEnd", "");
        } else {
            System.out.println("OpenOrderEnd");
        }
    }

    /**
     *
     * @param key
     * @param value
     * @param currency
     * @param accountName
     */
    @Override
    public void updateAccountValue(String key, String value, String currency, String accountName) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new UpdateAccountValueData(key, value, currency, accountName));
            zmqServerHandler.send("UpdateAccountValue", json);
        } else {
            System.out.println("UpdateAccountValue: " +
                    EWrapperMsgGenerator.updateAccountValue(key, value, currency, accountName));
        }
    }

    /**
     *
     * @param contract
     * @param position
     * @param marketPrice
     * @param marketValue
     * @param averageCost
     * @param unrealizedPNL
     * @param realizedPNL
     * @param accountName
     */
    @Override
    public void updatePortfolio(Contract contract,
                                Decimal position,
                                double marketPrice,
                                double marketValue,
                                double averageCost,
                                double unrealizedPNL,
                                double realizedPNL,
                                String accountName) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new UpdatePortfolioData(contract, position, marketPrice, marketValue,
                    averageCost, unrealizedPNL, realizedPNL, accountName));
            zmqServerHandler.send("UpdatePortfolio", json);
        } else {
            System.out.println("UpdatePortfolio: " +
                    EWrapperMsgGenerator.updatePortfolio(contract, position, marketPrice, marketValue,
                            averageCost, unrealizedPNL, realizedPNL, accountName));
        }
    }


    /**
     * @param timestamp
     */
    @Override
    public void updateAccountTime(String timestamp) {
        if (zmqServerHandler != null) {
            zmqServerHandler.send("UpdateAccountTime", timestamp);
        } else {
            System.out.println("UpdateAccountTime: " + EWrapperMsgGenerator.updateAccountTime(timestamp));
        }
    }

    /**
     * @param account
     */
    @Override
    public void accountDownloadEnd(String account) {
        if (zmqServerHandler != null) {
            zmqServerHandler.send("AccountDownloadEnd", account);
        } else {
            System.out.println("AccountDownloadEnd: " + EWrapperMsgGenerator.accountDownloadEnd(account));
        }
    }

    /**
     * @param numIds
     */
    @Override
    public void nextValidId(int numIds) {
        currentOrderId = numIds;
        if (zmqServerHandler != null) {
            zmqServerHandler.send("NextValidId", Integer.toString(numIds));
        } else {
            System.out.println("NextValidId: " + EWrapperMsgGenerator.nextValidId(numIds));
        }
    }

    /**
     * @param reqId
     * @param contractDetails
     */
    @Override
    public void contractDetails(int reqId, ContractDetails contractDetails) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new ContractDetailsData(reqId, contractDetails));
            zmqServerHandler.send("ContractDetails", json);
        } else {
            System.out.println("ContractDetails: " + EWrapperMsgGenerator.contractDetails(reqId, contractDetails));
        }
    }

    /**
     * @param reqId
     * @param contractDetails
     */
    @Override
    public void bondContractDetails(int reqId, ContractDetails contractDetails) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new BondContractDetailsData(reqId, contractDetails));
            zmqServerHandler.send("BondContractDetails", json);
        } else {
            System.out.println("BondContractDetails: " + EWrapperMsgGenerator.bondContractDetails(reqId, contractDetails));
        }
    }

    /**
     * @param reqId
     */
    @Override
    public void contractDetailsEnd(int reqId) {
        if (zmqServerHandler != null) {
            zmqServerHandler.send("ContractDetailsEnd", Integer.toString(reqId));
        } else {
            System.out.println("ContractDetailsEnd: " + EWrapperMsgGenerator.contractDetailsEnd(reqId));
        }
    }

    /**
     * @param reqId
     * @param contract
     * @param execution
     */
    @Override
    public void execDetails(int reqId, Contract contract, Execution execution) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new ExecDetailsData(reqId, contract, execution));
            zmqServerHandler.send("ExecDetails", json);
        } else {
            System.out.println("ExecDetails: " + EWrapperMsgGenerator.execDetails(reqId, contract, execution));
        }
    }

    /**
     * @param reqId
     */
    @Override
    public void execDetailsEnd(int reqId) {
        if (zmqServerHandler != null) {
            zmqServerHandler.send("ExecDetailsEnd", Integer.toString(reqId));
        } else {
            System.out.println("ExecDetailsEnd: " + EWrapperMsgGenerator.execDetailsEnd(reqId));
        }
    }


    /**
     *
     * @param tickerId
     * @param position
     * @param operation
     * @param side
     * @param price
     * @param size
     */
    @Override
    public void updateMktDepth(int tickerId, int position, int operation, int side, double price, Decimal size) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new UpdateMktDepthData(tickerId, position, operation, side, price, size));
            zmqServerHandler.send("UpdateMktDepth", json);
        } else {
            System.out.println("UpdateMktDepth: " +
                    EWrapperMsgGenerator.updateMktDepth(tickerId, position, operation, side, price, size));
        }
    }

    /**
     *
     * @param tickerId
     * @param position
     * @param marketMaker
     * @param operation
     * @param side
     * @param price
     * @param size
     * @param isSmartDepth
     */
    @Override
    public void updateMktDepthL2(int tickerId, int position, String marketMaker,
                                 int operation, int side, double price, Decimal size,
                                 boolean isSmartDepth) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new UpdateMktDepthL2Data(tickerId, position, marketMaker, operation, side, price, size, isSmartDepth));
            zmqServerHandler.send("UpdateMktDepthL2", json);
        } else {
            System.out.println("UpdateMktDepthL2: " +
                    EWrapperMsgGenerator.updateMktDepthL2(tickerId, position, marketMaker, operation, side, price, size, isSmartDepth));
        }
    }


    /**
     *
     * @param msgId
     * @param msgType
     * @param message
     * @param origExchange
     */
    @Override
    public void updateNewsBulletin(int msgId, int msgType, String message, String origExchange) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new UpdateNewsBulletinData(msgId, msgType, message, origExchange));
            zmqServerHandler.send("UpdateNewsBulletin", json);
        } else {
            System.out.println("UpdateNewsBulletin: " +
                    EWrapperMsgGenerator.updateNewsBulletin(msgId, msgType, message, origExchange));
        }
    }

    /**
     *
     * @param accountsList
     */
    @Override
    public void managedAccounts(String accountsList) {
        if (zmqServerHandler != null) {
            zmqServerHandler.send("ManagedAccounts", accountsList);
        } else {
            System.out.println("ManagedAccounts: " + EWrapperMsgGenerator.managedAccounts(accountsList));
        }
    }

    /**
     *
     * @param faDataType
     * @param xml
     */
    @Override
    public void receiveFA(int faDataType, String xml) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new ReceiveFAData(faDataType, xml));
            zmqServerHandler.send("ReceiveFA", json);
        } else {
            System.out.println("ReceiveFA: " + EWrapperMsgGenerator.receiveFA(faDataType, xml));
        }
    }

    /**
     * @param reqId
     * @param bar
     */
    @Override
    public void historicalData(int reqId, Bar bar) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new HistoricalDataData(reqId, bar));
            zmqServerHandler.send("HistoricalData", json);
        } else {
            System.out.println("HistoricalData:  " + EWrapperMsgGenerator.historicalData(reqId, bar.time(), bar.open(),
                    bar.high(), bar.low(), bar.close(), bar.volume(), bar.count(), bar.wap()));
        }
    }

    /**
     * @param xml
     */
    @Override
    public void scannerParameters(String xml) {
        if (zmqServerHandler != null) {
            zmqServerHandler.send("ScannerParameters", xml);
        } else {
            System.out.println("ScannerParameters: " + EWrapperMsgGenerator.scannerParameters(xml));
        }
    }


    /**
     *
     * @param reqId
     * @param rank
     * @param contractDetails
     * @param distance
     * @param benchmark
     * @param projection
     * @param legsStr
     */
    @Override
    public void scannerData(int reqId, int rank, ContractDetails contractDetails, String distance,
                            String benchmark, String projection, String legsStr) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new ScannerDataData(reqId, rank, contractDetails,
                    distance, benchmark, projection, legsStr));
            zmqServerHandler.send("ScannerData", json);
        } else {
            System.out.println("ScannerData: " + EWrapperMsgGenerator.scannerData(reqId, rank, contractDetails,
                    distance, benchmark, projection, legsStr));
        }
    }

    /**
     * @param reqId
     */
    @Override
    public void scannerDataEnd(int reqId) {
        if (zmqServerHandler != null) {
            zmqServerHandler.send("ScannerDataEnd", Integer.toString(reqId));
        } else {
            System.out.println("ScannerDataEnd: " + EWrapperMsgGenerator.scannerDataEnd(reqId));
        }
    }


    /**
     *
     * @param reqId
     * @param time
     * @param open
     * @param high
     * @param low
     * @param close
     * @param volume
     * @param wap
     * @param count
     */
    @Override
    public void realtimeBar(int reqId, long time, double open, double high, double low,
                            double close, Decimal volume, Decimal wap, int count) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new RealTimeBarData(reqId, time, open, high, low, close, volume, wap, count));
            zmqServerHandler.send("RealTimeBar", json);
        } else {
            System.out.println("RealTimeBar: " +
                    EWrapperMsgGenerator.realtimeBar(reqId, time, open, high, low, close, volume, wap, count));
        }
    }


    /**
     * @param time
     */
    @Override
    public void currentTime(long time) {
        if (zmqServerHandler != null) {
            zmqServerHandler.send("CurrentTime", Long.toString(time));
        } else {
            System.out.println("CurrentTime: " + EWrapperMsgGenerator.currentTime(time));
        }
    }

    /**
     *
     * @param reqId
     * @param data
     */
    @Override
    public void fundamentalData(int reqId, String data) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new FundamentalDataData(reqId, data));
            zmqServerHandler.send("FundamentalData", json);
        } else {
            System.out.println("FundamentalData: " + EWrapperMsgGenerator.fundamentalData(reqId, data));
        }
    }

    /**
     *
     * @param reqId
     * @param underComp
     */
    @Override
    public void deltaNeutralValidation(int reqId, DeltaNeutralContract underComp) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new DeltaNeutralValidationData(reqId, underComp));
            zmqServerHandler.send("DeltaNeutralValidation", json);
        } else {
            System.out.println("DeltaNeutralValidation: " + EWrapperMsgGenerator.deltaNeutralValidation(reqId, underComp));
        }
    }

    /**
     *
     * @param reqId
     */
    @Override
    public void tickSnapshotEnd(int reqId) {
        if (zmqServerHandler != null) {
            zmqServerHandler.send("TickSnapshotEnd", Integer.toString(reqId));
        } else {
            System.out.println("TickSnapshotEnd: " + EWrapperMsgGenerator.tickSnapshotEnd(reqId));
        }
    }

    /**
     *
     * @param reqId
     * @param marketDataType
     */
    @Override
    public void marketDataType(int reqId, int marketDataType) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new MarketDataTypeData(reqId, marketDataType));
            zmqServerHandler.send("MarketDataType", json);
        } else {
            System.out.println("MarketDataType: " + EWrapperMsgGenerator.marketDataType(reqId, marketDataType));
        }
    }


    /**
     * @param commissionReport
     */
    @Override
    public void commissionReport(CommissionReport commissionReport) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new CommissionReportData(commissionReport));
            zmqServerHandler.send("CommissionReport", json);
        } else {
            System.out.println("CommissionReport: " + EWrapperMsgGenerator.commissionReport(commissionReport));
        }
    }

    /**
     *
     * @param account
     * @param contract
     * @param pos
     * @param avgCost
     */
    @Override
    public void position(String account, Contract contract, Decimal pos, double avgCost) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new PositionData(account, contract, pos, avgCost));
            zmqServerHandler.send("Position", json);
        } else {
            System.out.println("Position: " + EWrapperMsgGenerator.position(account, contract, pos, avgCost));
        }
    }

    /**
     *
     */
    @Override
    public void positionEnd() {
        if (zmqServerHandler != null) {
            zmqServerHandler.send("PositionEnd", "");
        } else {
            System.out.println("PositionEnd");
        }
    }

    /**
     *
     * @param reqId
     * @param account
     * @param tag
     * @param value
     * @param currency
     */
    @Override
    public void accountSummary(int reqId, String account, String tag, String value, String currency) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new AccountSummaryData(reqId, account, tag, value, currency));
            zmqServerHandler.send("AccountSummary", json);
        } else {
            System.out.println("AccountSummary: " + EWrapperMsgGenerator.accountSummary(reqId, account, tag, value, currency));
        }
    }

    /**
     *
     * @param reqId
     */
    @Override
    public void accountSummaryEnd(int reqId) {
        if (zmqServerHandler != null) {
            zmqServerHandler.send("AccountSummaryEnd", Integer.toString(reqId));
        } else {
            System.out.println("AccountSummaryEnd: " + EWrapperMsgGenerator.accountSummaryEnd(reqId));
        }
    }


    /**
     *
     * @param s
     */
    @Override
    public void verifyMessageAPI(String s) {
        if (zmqServerHandler != null) {
            zmqServerHandler.send("VerifyMessageAPI", s);
        } else {
            System.out.println("VerifyMessageAPI: " + s);
        }
    }


    /**
     *
     * @param b
     * @param s
     */
    @Override
    public void verifyCompleted(boolean b, String s) {
        if (zmqServerHandler != null) {
            zmqServerHandler.send("VerifyCompleted", Boolean.toString(b) + "," + s);
        } else {
            System.out.println("VerifyCompleted: " + b + "," + s);
        }
    }

    /**
     *
     * @param s
     * @param s1
     */
    @Override
    public void verifyAndAuthMessageAPI(String s, String s1) {
        if (zmqServerHandler != null) {
            zmqServerHandler.send("VerifyAndAuthMessageAPI", s + "," + s1);
        } else {
            System.out.println("VerifyAndAuthMessageAPI: " + s + "," + s1);
        }
    }

    /**
     *
     * @param b
     * @param s
     */
    @Override
    public void verifyAndAuthCompleted(boolean b, String s) {
        if (zmqServerHandler != null) {
            zmqServerHandler.send("VerifyAndAuthCompleted", Boolean.toString(b) + "," + s);
        } else {
            System.out.println("VerifyAndAuthCompleted: " + b + "," + s);
        }
    }

    /**
     * @param requestId
     * @param groups
     */
    @Override
    public void displayGroupList(int requestId, String groups) {
        if (zmqServerHandler != null) {
            zmqServerHandler.send("DisplayGroupList", Integer.toString(requestId) + "," + groups);
        } else {
            System.out.println("DisplayGroupList: " + requestId + "," + groups);
        }
    }

    /**
     * @param requestId
     * @param contractInfo
     */
    @Override
    public void displayGroupUpdated(int requestId, String contractInfo) {
        if (zmqServerHandler != null) {
            zmqServerHandler.send("DisplayGroupUpdated", Integer.toString(requestId) + "," + contractInfo);
        } else {
            System.out.println("DisplayGroupUpdated: " + requestId + "," + contractInfo);
        }
    }

    /**
     * @param e
     */
    @Override
    public void error(Exception e) {
        if (zmqServerHandler != null) {
            zmqServerHandler.send("Error", e.toString());
        } else {
            System.out.println("Error: " + e.toString());
        }
    }

    /**
     * @param s
     */
    @Override
    public void error(String s) {
        if (zmqServerHandler != null) {
            zmqServerHandler.send("Error", s);
        } else {
            System.out.println("Error: " + s);
        }
    }

    /**
     *
     * @param reqId
     * @param errorCode
     * @param errorMsg
     * @param advancedOrderRejectJson
     */
    @Override
    public void error(int reqId, int errorCode, String errorMsg, String advancedOrderRejectJson) {
        String str = "Error. Id: " + reqId + ", Code: " + errorCode + ", Msg: " + errorMsg;
        if (advancedOrderRejectJson != null) {
            str += (", AdvancedOrderRejectJson: " + advancedOrderRejectJson);
        }

        if (zmqServerHandler != null) {
            zmqServerHandler.send("Error", str);
        } else {
            System.out.println("Error: " + str);
        }

    }

    /**
     *
     */
    @Override
    public void connectionClosed() {
        if (zmqServerHandler != null) {
            zmqServerHandler.send("ConnectionClosed", "");
        } else {
            System.out.println("ConnectionClosed");
        }
    }

    /**
     *
     */
    @Override
    public void connectAck() {
        if (zmqServerHandler != null) {
            zmqServerHandler.send("ConnectAck", "");
        } else {
            System.out.println("ConnectAck");
        }
    }


    /**
     *
     * @param reqId
     * @param account
     * @param modelCode
     * @param contract
     * @param pos
     * @param avgCost
     */
    @Override
    public void positionMulti(int reqId, String account, String modelCode,
                              Contract contract, Decimal pos, double avgCost) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new PositionMultiData(reqId, account, modelCode, contract, pos, avgCost));
            zmqServerHandler.send("PositionMulti", json);
        } else {
            System.out.println("PositionMulti: " +
                    EWrapperMsgGenerator.positionMulti(reqId, account, modelCode, contract, pos, avgCost));
        }
    }

    /**
     * @param requestId
     */
    @Override
    public void positionMultiEnd(int requestId) {
        if (zmqServerHandler != null) {
            zmqServerHandler.send("PositionMultiEnd", Integer.toString(requestId));
        } else {
            System.out.println("PositionMultiEnd: " + EWrapperMsgGenerator.positionMultiEnd(requestId));
        }
    }


    /**
     *
     * @param reqId
     * @param account
     * @param modelCode
     * @param key
     * @param value
     * @param currency
     */
    @Override
    public void accountUpdateMulti(int reqId, String account, String modelCode,
                                   String key, String value, String currency) {

        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new AccountUpdateMultiData(reqId, account, modelCode, key, value, currency));
            zmqServerHandler.send("AccountUpdateMulti", json);
        } else {
            System.out.println("AccountUpdateMulti: " +
                    EWrapperMsgGenerator.accountUpdateMulti(reqId, account, modelCode, key, value, currency));
        }
    }

    /**
     * @param requestId
     */
    @Override
    public void accountUpdateMultiEnd(int requestId) {
        if (zmqServerHandler != null) {
            zmqServerHandler.send("AccountUpdateMultiEnd", Integer.toString(requestId));
        } else {
            System.out.println("AccountUpdateMultiEnd: " + EWrapperMsgGenerator.accountUpdateMultiEnd(requestId));
        }
    }

    /**
     *
     * @param reqId
     * @param exchange
     * @param underlyingConId
     * @param tradingClass
     * @param multiplier
     * @param expirations
     * @param strikes
     */
    @Override
    public void securityDefinitionOptionalParameter(int reqId,
                                                    String exchange,
                                                    int underlyingConId,
                                                    String tradingClass,
                                                    String multiplier,
                                                    Set expirations,
                                                    Set strikes) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new SecurityDefinitionOptionalParameter(reqId, exchange, underlyingConId,
                    tradingClass, multiplier, expirations, strikes));
            zmqServerHandler.send("SecurityDefinitionOptionalParameter", json);
        } else {
            System.out.println("SecurityDefinitionOptionalParameter: " +
                    EWrapperMsgGenerator.securityDefinitionOptionalParameter(reqId, exchange, underlyingConId,
                            tradingClass, multiplier, expirations, strikes));
        }
    }

    /**
     * @param reqId
     */
    @Override
    public void securityDefinitionOptionalParameterEnd(int reqId) {
        if (zmqServerHandler != null) {
            zmqServerHandler.send("SecurityDefinitionOptionalParameterEnd", Integer.toString(reqId));
        } else {
            System.out.println("SecurityDefinitionOptionalParameterEnd: " +
                    EWrapperMsgGenerator.securityDefinitionOptionalParameterEnd(reqId));
        }
    }

    /**
     * @param reqId
     * @param softDollarTiers
     */
    @Override
    public void softDollarTiers(int reqId, SoftDollarTier[] softDollarTiers) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new SoftDollarTiersData(reqId, softDollarTiers));
            zmqServerHandler.send("SoftDollarTiers", json);
        } else {
            System.out.println("SoftDollarTiers: " +
                    EWrapperMsgGenerator.softDollarTiers(reqId, softDollarTiers));
        }
    }

    /**
     * @param familyCodes
     */
    @Override
    public void familyCodes(FamilyCode[] familyCodes) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new FamilyCodesData(familyCodes));
            zmqServerHandler.send("FamilyCodes", json);
        } else {
            System.out.println("FamilyCodes: " +
                    EWrapperMsgGenerator.familyCodes(familyCodes));
        }
    }

    /**
     * @param reqId
     * @param contractDescriptions
     */
    @Override
    public void symbolSamples(int reqId, ContractDescription[] contractDescriptions) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new SymbolSamplesData(reqId, contractDescriptions));
            zmqServerHandler.send("SymbolSamples", json);
        } else {
            System.out.println("SymbolSamples: " +
                    EWrapperMsgGenerator.symbolSamples(reqId, contractDescriptions));
        }
    }

    /**
     *
     * @param reqId
     * @param startDateStr
     * @param endDateStr
     */
    @Override
    public void historicalDataEnd(int reqId, String startDateStr, String endDateStr) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new HistoricalDataEndData(reqId, startDateStr, endDateStr));
            zmqServerHandler.send("HistoricalDataEnd", json);
        } else {
            System.out.println("HistoricalDataEnd: " +
                    EWrapperMsgGenerator.historicalDataEnd(reqId, startDateStr, endDateStr));
        }
    }

    /**
     * @param depthMktDataDescriptions
     */
    @Override
    public void mktDepthExchanges(DepthMktDataDescription[] depthMktDataDescriptions) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new MktDepthExchangesData(depthMktDataDescriptions));
            zmqServerHandler.send("MktDepthExchanges", json);
        } else {
            System.out.println("MktDepthExchanges: " +
                    EWrapperMsgGenerator.mktDepthExchanges(depthMktDataDescriptions));
        }
    }


    /**
     *
     * @param tickerId
     * @param timeStamp
     * @param providerCode
     * @param articleId
     * @param headline
     * @param extraData
     */
    @Override
    public void tickNews(int tickerId, long timeStamp, String providerCode,
                         String articleId, String headline, String extraData) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new TickNewsData(tickerId, timeStamp, providerCode, articleId, headline, extraData));
            zmqServerHandler.send("TickNews", json);
        } else {
            System.out.println("TickNews: " +
                    EWrapperMsgGenerator.tickNews(tickerId, timeStamp, providerCode, articleId, headline, extraData));
        }
    }


    /**
     *
     * @param reqId
     * @param theMap
     */
    @Override
    public void smartComponents(int reqId, Map<Integer, Entry<String, Character>> theMap) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new SmartComponentsData(reqId, theMap));
            zmqServerHandler.send("SmartComponents", json);
        } else {
            System.out.println("SmartComponents: " +
                    EWrapperMsgGenerator.smartComponents(reqId, theMap));
        }
    }

    /**
     *
     * @param tickerId
     * @param minTick
     * @param bboExchange
     * @param snapshotPermissions
     */
    @Override
    public void tickReqParams(int tickerId, double minTick, String bboExchange, int snapshotPermissions) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new TickReqParamsData(tickerId, minTick, bboExchange, snapshotPermissions));
            zmqServerHandler.send("TickReqParams", json);
        } else {
            System.out.println("TickReqParams: " +
                    EWrapperMsgGenerator.tickReqParams(tickerId, minTick, bboExchange, snapshotPermissions));
        }
    }

    /**
     * @param newsProviders
     */
    @Override
    public void newsProviders(NewsProvider[] newsProviders) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new NewsProvidersData(newsProviders));
            zmqServerHandler.send("NewsProviders", json);
        } else {
            System.out.println("NewsProviders: " +
                    EWrapperMsgGenerator.newsProviders(newsProviders));
        }
    }


    /**
     *
     * @param requestId
     * @param articleType
     * @param articleText
     */
    @Override
    public void newsArticle(int requestId, int articleType, String articleText) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new NewsArticleData(requestId, articleType, articleText));
            zmqServerHandler.send("NewsArticle", json);
        } else {
            System.out.println("NewsArticle: " +
                    EWrapperMsgGenerator.newsArticle(requestId, articleType, articleText));
        }
    }

    /**
     *
     * @param requestId
     * @param time
     * @param providerCode
     * @param articleId
     * @param headline
     */
    @Override
    public void historicalNews(int requestId, String time, String providerCode,
                               String articleId, String headline) {

        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new HistoricalNewsData(requestId, time, providerCode, articleId, headline));
            zmqServerHandler.send("HistoricalNews", json);
        } else {
            System.out.println("HistoricalNews: " +
                    EWrapperMsgGenerator.historicalNews(requestId, time, providerCode, articleId, headline));
        }
    }

    /**
     * @param requestId
     * @param hasMore
     */
    @Override
    public void historicalNewsEnd(int requestId, boolean hasMore) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new HistoricalNewsEndData(requestId, hasMore));
            zmqServerHandler.send("HistoricalNewsEnd", json);
        } else {
            System.out.println("HistoricalNewsEnd: " +
                    EWrapperMsgGenerator.historicalNewsEnd(requestId, hasMore));
        }
    }

    /**
     *
     * @param requestId
     * @param headTimestamp
     */
    @Override
    public void headTimestamp(int requestId, String headTimestamp) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new HeadTimestampData(requestId, headTimestamp));
            zmqServerHandler.send("HeadTimestamp", json);
        } else {
            System.out.println("HeadTimestamp: " +
                    EWrapperMsgGenerator.headTimestamp(requestId, headTimestamp));
        }
    }

    /**
     * @param requestId
     * @param list
     */
    @Override
    public void histogramData(int requestId, List<HistogramEntry> list) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new HistogramDataData(requestId, list));
            zmqServerHandler.send("HistogramData", json);
        } else {
            System.out.println("HistogramData: " +
                    EWrapperMsgGenerator.histogramData(requestId, list));
        }
    }

    /**
     * @param reqId
     * @param bar
     */
    @Override
    public void historicalDataUpdate(int reqId, Bar bar) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new HistoricalDataUpdateData(reqId, bar));
            zmqServerHandler.send("HistoricalDataUpdate", json);
        } else {
            System.out.println("HistoricalDataUpdate: " +
                    EWrapperMsgGenerator.historicalData(reqId bar.time(), bar.open(), bar.high(), bar.low(),
                            bar.close(), bar.volume(), bar.count(), bar.wap()));
        }
    }

    /**
     *
     * @param reqId
     * @param conId
     * @param exchange
     */
    @Override
    public void rerouteMktDataReq(int reqId, int conId, String exchange) {
        if (zmqServerHandler != null) {
             Gson gson = new Gson();
            String json = gson.toJson(new RerouteMktDataReqData(reqId, conId, exchange));
            zmqServerHandler.send("RerouteMktDataReq", json);
        } else {
            System.out.println("RerouteMktDataReq: " +
                    EWrapperMsgGenerator.rerouteMktDataReq(reqId, conId, exchange));
        }
    }

    /**
     *
     * @param reqId
     * @param conId
     * @param exchange
     */
    @Override
    public void rerouteMktDepthReq(int reqId, int conId, String exchange) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new RerouteMktDepthReqData(reqId, conId, exchange));
            zmqServerHandler.send("RerouteMktDepthReq", json);
        } else {
            System.out.println("RerouteMktDepthReq: " +
                    EWrapperMsgGenerator.rerouteMktDepthReq(reqId, conId, exchange));
        }
        System.out.println(EWrapperMsgGenerator.rerouteMktDepthReq(reqId, conId, exchange));
    }


    /**
     *
     * @param marketRuleId
     * @param priceIncrements
     */
    @Override
    public void marketRule(int marketRuleId, PriceIncrement[] priceIncrements) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new MarketRuleData(marketRuleId, priceIncrements));
            zmqServerHandler.send("MarketRule", json);
        } else {
            System.out.println("MarketRule: " +
                    EWrapperMsgGenerator.marketRule(marketRuleId, priceIncrements));
        }
    }

    /**
     * @param i
     * @param v
     * @param v1
     * @param v2
     */
    @Override
    public void pnl(int i, double v, double v1, double v2) {

    }

    /**
     * @param i
     * @param decimal
     * @param v
     * @param v1
     * @param v2
     * @param v3
     */
    @Override
    public void pnlSingle(int i, Decimal decimal, double v, double v1, double v2, double v3) {

    }

    /**
     * @param i
     * @param list
     * @param b
     */
    @Override
    public void historicalTicks(int i, List<HistoricalTick> list, boolean b) {

    }

    /**
     *
     * @param reqId
     * @param ticks
     * @param done
     */
    @Override
    public void historicalTicksBidAsk(int reqId, List<HistoricalTickBidAsk> ticks, boolean done) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new HistoricalTicksBidAskData(reqId, ticks, done));
            zmqServerHandler.send("HistoricalTicksBidAsk", json);
        } else {

            for (HistoricalTickBidAsk tick : ticks) {
                System.out.println("HistoricalTicksBidAsk: " +
                        EWrapperMsgGenerator.historicalTickBidAsk(reqId, tick.time(),
                                tick.tickAttribBidAsk(), tick.priceBid(),
                                tick.priceAsk(), tick.sizeBid(),
                                tick.sizeAsk()));
            }
        }
    }

    /**
     *
     * @param reqId
     * @param ticks
     * @param done
     */
    @Override
    public void historicalTicksLast(int reqId, List<HistoricalTickLast> ticks, boolean done) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new HistoricalTicksLastData(reqId, ticks, done));
            zmqServerHandler.send("HistoricalTicksLast", json);
        } else {

            for (HistoricalTickLast tick : ticks) {
                System.out.println("HistoricalTicksLast: " +
                        EWrapperMsgGenerator.historicalTickLast(reqId, tick.time(),
                                tick.tickAttribLast(), tick.price(),
                                tick.size(), tick.exchange(),
                                tick.specialConditions()));
            }
        }
    }

    /**
     *
     * @param reqId
     * @param tickType
     * @param time
     * @param price
     * @param size
     * @param tickAttribLast
     * @param exchange
     * @param specialConditions
     */
    @Override
    public void tickByTickAllLast(int reqId, int tickType, long time,
                                  double price, Decimal size, TickAttribLast tickAttribLast,
                                  String exchange, String specialConditions) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new TickByTickAllLastData(reqId, tickType, time, price, size,
                    tickAttribLast, exchange, specialConditions));
            zmqServerHandler.send("TickByTickAllLast", json);
        } else {
            System.out.println("TickByTickAllLast: " +
                    EWrapperMsgGenerator.tickByTickAllLast(reqId, tickType, time, price,
                            size, tickAttribLast, exchange, specialConditions));
        }
    }

    /**
     *
     * @param reqId
     * @param time
     * @param bidPrice
     * @param askPrice
     * @param bidSize
     * @param askSize
     * @param tickAttribBidAsk
     */
    @Override
    public void tickByTickBidAsk(int reqId,
                                 long time,
                                 double bidPrice,
                                 double askPrice,
                                 Decimal bidSize,
                                 Decimal askSize,
                                 TickAttribBidAsk tickAttribBidAsk) {

        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new TickByTickBidAskData(reqId, time, bidPrice, askPrice,
                    bidSize, askSize, tickAttribBidAsk));
            zmqServerHandler.send("TickByTickBidAsk", json);
        } else {
            System.out.println("TickByTickBidAsk:" +
                    EWrapperMsgGenerator.tickByTickBidAsk(reqId, time, bidPrice, askPrice,
                            bidSize, askSize, tickAttribBidAsk));
        }
    }


    /**
     *
     * @param reqId
     * @param time
     * @param midPoint
     */
    @Override
    public void tickByTickMidPoint(int reqId, long time, double midPoint) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new TickByTickMidPointData(reqId, time, midPoint));
            zmqServerHandler.send("TickByTickMidPoint", json);
        } else {
            System.out.println("TickByTickMidPoint: " +
                    EWrapperMsgGenerator.tickByTickMidPoint(reqId, time, midPoint));
        }
    }


    /**
     *
     * @param orderId
     * @param apiClientId
     * @param apiOrderId
     */
    @Override
    public void orderBound(long orderId, int apiClientId, int apiOrderId) {
        if (zmqServerHandler != null) {
            zmqServerHandler.send("OrderBound",
                    Long.toString(orderId) + "," + Integer.toString(apiClientId) + "," + Integer.toString(apiOrderId));
        } else {
            System.out.println("OrderBound: " +
                    EWrapperMsgGenerator.orderBound(orderId, apiClientId, apiOrderId));
        }
    }

    /**
     * @param contract
     * @param order
     * @param orderState
     */
    @Override
    public void completedOrder(Contract contract, Order order, OrderState orderState) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new CompletedOrderData(contract, order, orderState));
            zmqServerHandler.send("CompletedOrder", json);
        } else {
            System.out.println("CompletedOrder: " +
                    EWrapperMsgGenerator.completedOrder(contract, order, orderState));
        }
    }

    /**
     *
     */
    @Override
    public void completedOrdersEnd() {
        if (zmqServerHandler != null) {
            zmqServerHandler.send("CompletedOrdersEnd", "");
        } else {
            System.out.println("CompletedOrdersEnd");
        }
    }

    /**
     * @param reqId
     * @param s
     */
    @Override
    public void replaceFAEnd(int reqId, String s) {
        if (zmqServerHandler != null) {
            zmqServerHandler.send("ReplaceFAEnd", Integer.toString(reqId) + "," + s);
        } else {
            System.out.println("ReplaceFAEnd: " + reqId + "," + s);
        }
    }

    /**
     * @param i
     * @param s
     */
    @Override
    public void wshMetaData(int i, String s) {
        if (zmqServerHandler != null) {
            zmqServerHandler.send("WshMetaData", Integer.toString(i) + "," + s);
        } else {
            System.out.println("WshMetaData: " + i + "," + s);
        }
    }

    /**
     * @param requestId
     * @param requestId
     */
    @Override
    public void wshEventData(int requestId, String dataJson) {
        if (zmqServerHandler != null) {
            zmqServerHandler.send("WshEventData", Integer.toString(requestId) + "," + dataJson);
        } else {
            System.out.println("WshEventData: " + requestId + "," + dataJson);
        }
    }


    /**
     *
     * @param reqId
     * @param startDateTime
     * @param endDateTime
     * @param timeZone
     * @param sessions
     */
    @Override
    public void historicalSchedule(int reqId, String startDateTime, String endDateTime, String timeZone, List sessions) {
        if (zmqServerHandler != null) {
            Gson gson = new Gson();
            String json = gson.toJson(new HistoricalScheduleData(reqId, startDateTime, endDateTime, timeZone, sessions));
            zmqServerHandler.send("HistoricalSchedule", json);
        } else {
            System.out.println("HistoricalSchedule: " +
                    EWrapperMsgGenerator.historicalSchedule(reqId, startDateTime, endDateTime, timeZone, sessions));
        }
    }

    /**
     * @param reqId
     * @param whiteBrandingId
     */
    @Override
    public void userInfo(int reqId, String whiteBrandingId) {
        if (zmqServerHandler != null) {
            zmqServerHandler.send("UserInfo", Integer.toString(reqId) + "," + whiteBrandingId);
        } else {
            System.out.println("UserInfo: " + reqId + "," + whiteBrandingId);
        }
    }
}


