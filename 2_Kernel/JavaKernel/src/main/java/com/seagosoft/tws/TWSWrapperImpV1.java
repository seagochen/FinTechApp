package com.seagosoft.tws;

import com.ib.client.*;

import com.google.gson.Gson;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.seagosoft.tws.data.TickOptionComputationData;
import com.seagosoft.tws.data.TickPriceData;
import com.seagosoft.tws.data.TickSizeData;
import com.seagosoft.tws.data.tickGenericData;
import com.seagosoft.tws.data.tickStringData;
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
            zmqServerHandler.send("tickPrice", json);
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
            zmqServerHandler.send("tickSize", json);
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
            zmqServerHandler.send("tickOptionComputation", json);
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
            zmqServerHandler.send("tickGeneric", json);
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
            zmqServerHandler.send("tickString", json);
        } else {
            System.out.println("Tick String: " + EWrapperMsgGenerator.tickString(tickerId, tickType, value));
        }
    }

    /**
     * @param i
     * @param i1
     * @param v
     * @param s
     * @param v1
     * @param i2
     * @param s1
     * @param v2
     * @param v3
     */
    @Override
    public void tickEFP(int i, int i1, double v, String s, double v1, int i2, String s1, double v2, double v3) {

    }

    /**
     * @param i
     * @param s
     * @param decimal
     * @param decimal1
     * @param v
     * @param i1
     * @param i2
     * @param v1
     * @param i3
     * @param s1
     * @param v2
     */
    @Override
    public void orderStatus(int i, String s, Decimal decimal, Decimal decimal1, double v, int i1, int i2, double v1, int i3, String s1, double v2) {

    }

    /**
     * @param i
     * @param contract
     * @param order
     * @param orderState
     */
    @Override
    public void openOrder(int i, Contract contract, Order order, OrderState orderState) {

    }

    /**
     *
     */
    @Override
    public void openOrderEnd() {

    }

    /**
     * @param s
     * @param s1
     * @param s2
     * @param s3
     */
    @Override
    public void updateAccountValue(String s, String s1, String s2, String s3) {

    }

    /**
     * @param contract
     * @param decimal
     * @param v
     * @param v1
     * @param v2
     * @param v3
     * @param v4
     * @param s
     */
    @Override
    public void updatePortfolio(Contract contract, Decimal decimal, double v, double v1, double v2, double v3, double v4, String s) {

    }

    /**
     * @param s
     */
    @Override
    public void updateAccountTime(String s) {

    }

    /**
     * @param s
     */
    @Override
    public void accountDownloadEnd(String s) {

    }

    /**
     * @param i
     */
    @Override
    public void nextValidId(int i) {

    }

    /**
     * @param i
     * @param contractDetails
     */
    @Override
    public void contractDetails(int i, ContractDetails contractDetails) {

    }

    /**
     * @param i
     * @param contractDetails
     */
    @Override
    public void bondContractDetails(int i, ContractDetails contractDetails) {

    }

    /**
     * @param i
     */
    @Override
    public void contractDetailsEnd(int i) {

    }

    /**
     * @param i
     * @param contract
     * @param execution
     */
    @Override
    public void execDetails(int i, Contract contract, Execution execution) {

    }

    /**
     * @param i
     */
    @Override
    public void execDetailsEnd(int i) {

    }

    /**
     * @param i
     * @param i1
     * @param i2
     * @param i3
     * @param v
     * @param decimal
     */
    @Override
    public void updateMktDepth(int i, int i1, int i2, int i3, double v, Decimal decimal) {

    }

    /**
     * @param i
     * @param i1
     * @param s
     * @param i2
     * @param i3
     * @param v
     * @param decimal
     * @param b
     */
    @Override
    public void updateMktDepthL2(int i, int i1, String s, int i2, int i3, double v, Decimal decimal, boolean b) {

    }

    /**
     * @param i
     * @param i1
     * @param s
     * @param s1
     */
    @Override
    public void updateNewsBulletin(int i, int i1, String s, String s1) {

    }

    /**
     * @param s
     */
    @Override
    public void managedAccounts(String s) {

    }

    /**
     * @param i
     * @param s
     */
    @Override
    public void receiveFA(int i, String s) {

    }

    /**
     * @param i
     * @param bar
     */
    @Override
    public void historicalData(int i, Bar bar) {

    }

    /**
     * @param s
     */
    @Override
    public void scannerParameters(String s) {

    }

    /**
     * @param i
     * @param i1
     * @param contractDetails
     * @param s
     * @param s1
     * @param s2
     * @param s3
     */
    @Override
    public void scannerData(int i, int i1, ContractDetails contractDetails, String s, String s1, String s2, String s3) {

    }

    /**
     * @param i
     */
    @Override
    public void scannerDataEnd(int i) {

    }

    /**
     * @param i
     * @param l
     * @param v
     * @param v1
     * @param v2
     * @param v3
     * @param decimal
     * @param decimal1
     * @param i1
     */
    @Override
    public void realtimeBar(int i, long l, double v, double v1, double v2, double v3, Decimal decimal, Decimal decimal1, int i1) {

    }

    /**
     * @param l
     */
    @Override
    public void currentTime(long l) {

    }

    /**
     * @param i
     * @param s
     */
    @Override
    public void fundamentalData(int i, String s) {

    }

    /**
     * @param i
     * @param deltaNeutralContract
     */
    @Override
    public void deltaNeutralValidation(int i, DeltaNeutralContract deltaNeutralContract) {

    }

    /**
     * @param i
     */
    @Override
    public void tickSnapshotEnd(int i) {

    }

    /**
     * @param i
     * @param i1
     */
    @Override
    public void marketDataType(int i, int i1) {

    }

    /**
     * @param commissionReport
     */
    @Override
    public void commissionReport(CommissionReport commissionReport) {

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


