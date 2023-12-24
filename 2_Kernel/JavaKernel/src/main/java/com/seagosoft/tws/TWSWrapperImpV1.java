package com.seagosoft.tws;

import com.ib.client.*;

import com.google.gson.Gson;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
     * @param s
     * @param contract
     * @param decimal
     * @param v
     */
    @Override
    public void position(String s, Contract contract, Decimal decimal, double v) {

    }

    /**
     *
     */
    @Override
    public void positionEnd() {

    }

    /**
     * @param i
     * @param s
     * @param s1
     * @param s2
     * @param s3
     */
    @Override
    public void accountSummary(int i, String s, String s1, String s2, String s3) {

    }

    /**
     * @param i
     */
    @Override
    public void accountSummaryEnd(int i) {

    }

    /**
     * @param s
     */
    @Override
    public void verifyMessageAPI(String s) {

    }

    /**
     * @param b
     * @param s
     */
    @Override
    public void verifyCompleted(boolean b, String s) {

    }

    /**
     * @param s
     * @param s1
     */
    @Override
    public void verifyAndAuthMessageAPI(String s, String s1) {

    }

    /**
     * @param b
     * @param s
     */
    @Override
    public void verifyAndAuthCompleted(boolean b, String s) {

    }

    /**
     * @param i
     * @param s
     */
    @Override
    public void displayGroupList(int i, String s) {

    }

    /**
     * @param i
     * @param s
     */
    @Override
    public void displayGroupUpdated(int i, String s) {

    }

    /**
     * @param e
     */
    @Override
    public void error(Exception e) {

    }

    /**
     * @param s
     */
    @Override
    public void error(String s) {

    }

    /**
     * @param i
     * @param i1
     * @param s
     * @param s1
     */
    @Override
    public void error(int i, int i1, String s, String s1) {

    }

    /**
     *
     */
    @Override
    public void connectionClosed() {

    }

    /**
     *
     */
    @Override
    public void connectAck() {

    }

    /**
     * @param i
     * @param s
     * @param s1
     * @param contract
     * @param decimal
     * @param v
     */
    @Override
    public void positionMulti(int i, String s, String s1, Contract contract, Decimal decimal, double v) {

    }

    /**
     * @param i
     */
    @Override
    public void positionMultiEnd(int i) {

    }

    /**
     * @param i
     * @param s
     * @param s1
     * @param s2
     * @param s3
     * @param s4
     */
    @Override
    public void accountUpdateMulti(int i, String s, String s1, String s2, String s3, String s4) {

    }

    /**
     * @param i
     */
    @Override
    public void accountUpdateMultiEnd(int i) {

    }

    /**
     * @param i
     * @param s
     * @param i1
     * @param s1
     * @param s2
     * @param set
     * @param set1
     */
    @Override
    public void securityDefinitionOptionalParameter(int i, String s, int i1, String s1, String s2, Set<String> set, Set<Double> set1) {

    }

    /**
     * @param i
     */
    @Override
    public void securityDefinitionOptionalParameterEnd(int i) {

    }

    /**
     * @param i
     * @param softDollarTiers
     */
    @Override
    public void softDollarTiers(int i, SoftDollarTier[] softDollarTiers) {

    }

    /**
     * @param familyCodes
     */
    @Override
    public void familyCodes(FamilyCode[] familyCodes) {

    }

    /**
     * @param i
     * @param contractDescriptions
     */
    @Override
    public void symbolSamples(int i, ContractDescription[] contractDescriptions) {

    }

    /**
     * @param i
     * @param s
     * @param s1
     */
    @Override
    public void historicalDataEnd(int i, String s, String s1) {

    }

    /**
     * @param depthMktDataDescriptions
     */
    @Override
    public void mktDepthExchanges(DepthMktDataDescription[] depthMktDataDescriptions) {

    }

    /**
     * @param i
     * @param l
     * @param s
     * @param s1
     * @param s2
     * @param s3
     */
    @Override
    public void tickNews(int i, long l, String s, String s1, String s2, String s3) {

    }

    /**
     * @param i
     * @param map
     */
    @Override
    public void smartComponents(int i, Map<Integer, Map.Entry<String, Character>> map) {

    }

    /**
     * @param i
     * @param v
     * @param s
     * @param i1
     */
    @Override
    public void tickReqParams(int i, double v, String s, int i1) {

    }

    /**
     * @param newsProviders
     */
    @Override
    public void newsProviders(NewsProvider[] newsProviders) {

    }

    /**
     * @param i
     * @param i1
     * @param s
     */
    @Override
    public void newsArticle(int i, int i1, String s) {

    }

    /**
     * @param i
     * @param s
     * @param s1
     * @param s2
     * @param s3
     */
    @Override
    public void historicalNews(int i, String s, String s1, String s2, String s3) {

    }

    /**
     * @param i
     * @param b
     */
    @Override
    public void historicalNewsEnd(int i, boolean b) {

    }

    /**
     * @param i
     * @param s
     */
    @Override
    public void headTimestamp(int i, String s) {

    }

    /**
     * @param i
     * @param list
     */
    @Override
    public void histogramData(int i, List<HistogramEntry> list) {

    }

    /**
     * @param i
     * @param bar
     */
    @Override
    public void historicalDataUpdate(int i, Bar bar) {

    }

    /**
     * @param i
     * @param i1
     * @param s
     */
    @Override
    public void rerouteMktDataReq(int i, int i1, String s) {

    }

    /**
     * @param i
     * @param i1
     * @param s
     */
    @Override
    public void rerouteMktDepthReq(int i, int i1, String s) {

    }

    /**
     * @param i
     * @param priceIncrements
     */
    @Override
    public void marketRule(int i, PriceIncrement[] priceIncrements) {

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
     * @param i
     * @param list
     * @param b
     */
    @Override
    public void historicalTicksBidAsk(int i, List<HistoricalTickBidAsk> list, boolean b) {

    }

    /**
     * @param i
     * @param list
     * @param b
     */
    @Override
    public void historicalTicksLast(int i, List<HistoricalTickLast> list, boolean b) {

    }

    /**
     * @param i
     * @param i1
     * @param l
     * @param v
     * @param decimal
     * @param tickAttribLast
     * @param s
     * @param s1
     */
    @Override
    public void tickByTickAllLast(int i, int i1, long l, double v, Decimal decimal, TickAttribLast tickAttribLast, String s, String s1) {

    }

    /**
     * @param i
     * @param l
     * @param v
     * @param v1
     * @param decimal
     * @param decimal1
     * @param tickAttribBidAsk
     */
    @Override
    public void tickByTickBidAsk(int i, long l, double v, double v1, Decimal decimal, Decimal decimal1, TickAttribBidAsk tickAttribBidAsk) {

    }

    /**
     * @param i
     * @param l
     * @param v
     */
    @Override
    public void tickByTickMidPoint(int i, long l, double v) {

    }

    /**
     * @param l
     * @param i
     * @param i1
     */
    @Override
    public void orderBound(long l, int i, int i1) {

    }

    /**
     * @param contract
     * @param order
     * @param orderState
     */
    @Override
    public void completedOrder(Contract contract, Order order, OrderState orderState) {

    }

    /**
     *
     */
    @Override
    public void completedOrdersEnd() {

    }

    /**
     * @param i
     * @param s
     */
    @Override
    public void replaceFAEnd(int i, String s) {

    }

    /**
     * @param i
     * @param s
     */
    @Override
    public void wshMetaData(int i, String s) {

    }

    /**
     * @param i
     * @param s
     */
    @Override
    public void wshEventData(int i, String s) {

    }

    /**
     * @param i
     * @param s
     * @param s1
     * @param s2
     * @param list
     */
    @Override
    public void historicalSchedule(int i, String s, String s1, String s2, List<HistoricalSession> list) {

    }

    /**
     * @param i
     * @param s
     */
    @Override
    public void userInfo(int i, String s) {

    }
}


