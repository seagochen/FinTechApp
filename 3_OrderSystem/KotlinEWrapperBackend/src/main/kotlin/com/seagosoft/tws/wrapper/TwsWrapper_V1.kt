package com.seagosoft.tws.wrapper

// Import TWS API classes
import com.ib.client.*
import java.lang.Exception

// Import ZeroMQ classes
import com.seagosoft.tws.zeromq.ZeroMQServer


// Import Gson classes
import com.google.gson.Gson


// Kotlin class wrapper for TWS EWrapper interface
class TwsWrapper_V1 (twsServer: String, twsPort: Int, zeromqPort: Int): EWrapper {

    // Private variables
    private var client: EClientSocket? = null
    private var nextOrderId = 0
    private var readySignal: EReaderSignal? = null

    // ZeroMQ server
    private var zeromqServer: ZeroMQServer? = null

    // Constructor
    init {
        // Initialize the EClientSocket object
        readySignal = EJavaSignal()
        client = EClientSocket(this, readySignal)
        client?.eConnect(twsServer, twsPort, 0)

        // Create a zeromq socket
        zeromqServer = ZeroMQServer(zeromqPort)
        zeromqServer?.start()
    }

    override fun tickPrice(p0: Int, p1: Int, p2: Double, p3: TickAttrib?) {
        // Create a new TickPrice object
        val tickPrice = TickPriceData(p0, p1, p2)

        // Convert the object to JSON
        val gson = Gson()
        val json = gson.toJson(tickPrice)

        // Send the JSON message via ZeroMQ
        zeromqServer?.send("tickPrice", json)
    }

    override fun tickSize(p0: Int, p1: Int, p2: Decimal?) {
        TODO("Not yet implemented")
    }

    override fun tickOptionComputation(p0: Int, p1: Int, p2: Int, p3: Double, p4: Double, p5: Double, p6: Double, p7: Double, p8: Double, p9: Double, p10: Double) {
        TODO("Not yet implemented")
    }

    override fun tickGeneric(p0: Int, p1: Int, p2: Double) {
        TODO("Not yet implemented")
    }

    override fun tickString(p0: Int, p1: Int, p2: String?) {
        TODO("Not yet implemented")
    }

    override fun tickEFP(p0: Int, p1: Int, p2: Double, p3: String?, p4: Double, p5: Int, p6: String?, p7: Double, p8: Double) {
        TODO("Not yet implemented")
    }

    override fun orderStatus(p0: Int, p1: String?, p2: Decimal?, p3: Decimal?, p4: Double, p5: Int, p6: Int, p7: Double, p8: Int, p9: String?, p10: Double) {
        TODO("Not yet implemented")
    }

    override fun openOrder(p0: Int, p1: Contract?, p2: Order?, p3: OrderState?) {
        TODO("Not yet implemented")
    }

    override fun openOrderEnd() {
        TODO("Not yet implemented")
    }

    override fun updateAccountValue(p0: String?, p1: String?, p2: String?, p3: String?) {
        TODO("Not yet implemented")
    }

    override fun updatePortfolio(p0: Contract?, p1: Decimal?, p2: Double, p3: Double, p4: Double, p5: Double, p6: Double, p7: String?) {
        TODO("Not yet implemented")
    }

    override fun updateAccountTime(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun accountDownloadEnd(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun nextValidId(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun contractDetails(p0: Int, p1: ContractDetails?) {
        TODO("Not yet implemented")
    }

    override fun bondContractDetails(p0: Int, p1: ContractDetails?) {
        TODO("Not yet implemented")
    }

    override fun contractDetailsEnd(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun execDetails(p0: Int, p1: Contract?, p2: Execution?) {
        TODO("Not yet implemented")
    }

    override fun execDetailsEnd(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun updateMktDepth(p0: Int, p1: Int, p2: Int, p3: Int, p4: Double, p5: Decimal?) {
        TODO("Not yet implemented")
    }

    override fun updateMktDepthL2(p0: Int, p1: Int, p2: String?, p3: Int, p4: Int, p5: Double, p6: Decimal?, p7: Boolean) {
        TODO("Not yet implemented")
    }

    override fun updateNewsBulletin(p0: Int, p1: Int, p2: String?, p3: String?) {
        TODO("Not yet implemented")
    }

    override fun managedAccounts(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun receiveFA(p0: Int, p1: String?) {
        TODO("Not yet implemented")
    }

    override fun historicalData(p0: Int, p1: Bar?) {
        TODO("Not yet implemented")
    }

    override fun scannerParameters(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun scannerData(p0: Int, p1: Int, p2: ContractDetails?, p3: String?, p4: String?, p5: String?, p6: String?) {
        TODO("Not yet implemented")
    }

    override fun scannerDataEnd(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun realtimeBar(p0: Int, p1: Long, p2: Double, p3: Double, p4: Double, p5: Double, p6: Decimal?, p7: Decimal?, p8: Int) {
        TODO("Not yet implemented")
    }

    override fun currentTime(p0: Long) {
        TODO("Not yet implemented")
    }

    override fun fundamentalData(p0: Int, p1: String?) {
        TODO("Not yet implemented")
    }

    override fun deltaNeutralValidation(p0: Int, p1: DeltaNeutralContract?) {
        TODO("Not yet implemented")
    }

    override fun tickSnapshotEnd(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun marketDataType(p0: Int, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun commissionReport(p0: CommissionReport?) {
        TODO("Not yet implemented")
    }

    override fun position(p0: String?, p1: Contract?, p2: Decimal?, p3: Double) {
        TODO("Not yet implemented")
    }

    override fun positionEnd() {
        TODO("Not yet implemented")
    }

    override fun accountSummary(p0: Int, p1: String?, p2: String?, p3: String?, p4: String?) {
        TODO("Not yet implemented")
    }

    override fun accountSummaryEnd(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun verifyMessageAPI(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun verifyCompleted(p0: Boolean, p1: String?) {
        TODO("Not yet implemented")
    }

    override fun verifyAndAuthMessageAPI(p0: String?, p1: String?) {
        TODO("Not yet implemented")
    }

    override fun verifyAndAuthCompleted(p0: Boolean, p1: String?) {
        TODO("Not yet implemented")
    }

    override fun displayGroupList(p0: Int, p1: String?) {
        TODO("Not yet implemented")
    }

    override fun displayGroupUpdated(p0: Int, p1: String?) {
        TODO("Not yet implemented")
    }

    override fun error(p0: Exception?) {
        TODO("Not yet implemented")
    }

    override fun error(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun error(p0: Int, p1: Int, p2: String?, p3: String?) {
        TODO("Not yet implemented")
    }

    override fun connectionClosed() {
        TODO("Not yet implemented")
    }

    override fun connectAck() {
        TODO("Not yet implemented")
    }

    override fun positionMulti(p0: Int, p1: String?, p2: String?, p3: Contract?, p4: Decimal?, p5: Double) {
        TODO("Not yet implemented")
    }

    override fun positionMultiEnd(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun accountUpdateMulti(p0: Int, p1: String?, p2: String?, p3: String?, p4: String?, p5: String?) {
        TODO("Not yet implemented")
    }

    override fun accountUpdateMultiEnd(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun securityDefinitionOptionalParameter(p0: Int, p1: String?, p2: Int, p3: String?, p4: String?, p5: MutableSet<String>?, p6: MutableSet<Double>?) {
        TODO("Not yet implemented")
    }

    override fun securityDefinitionOptionalParameterEnd(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun softDollarTiers(p0: Int, p1: Array<out SoftDollarTier>?) {
        TODO("Not yet implemented")
    }

    override fun familyCodes(p0: Array<out FamilyCode>?) {
        TODO("Not yet implemented")
    }

    override fun symbolSamples(p0: Int, p1: Array<out ContractDescription>?) {
        TODO("Not yet implemented")
    }

    override fun historicalDataEnd(p0: Int, p1: String?, p2: String?) {
        TODO("Not yet implemented")
    }

    override fun mktDepthExchanges(p0: Array<out DepthMktDataDescription>?) {
        TODO("Not yet implemented")
    }

    override fun tickNews(p0: Int, p1: Long, p2: String?, p3: String?, p4: String?, p5: String?) {
        TODO("Not yet implemented")
    }

    override fun smartComponents(p0: Int, p1: MutableMap<Int, MutableMap.MutableEntry<String, Char>>?) {
        TODO("Not yet implemented")
    }

    override fun tickReqParams(p0: Int, p1: Double, p2: String?, p3: Int) {
        TODO("Not yet implemented")
    }

    override fun newsProviders(p0: Array<out NewsProvider>?) {
        TODO("Not yet implemented")
    }

    override fun newsArticle(p0: Int, p1: Int, p2: String?) {
        TODO("Not yet implemented")
    }

    override fun historicalNews(p0: Int, p1: String?, p2: String?, p3: String?, p4: String?) {
        TODO("Not yet implemented")
    }

    override fun historicalNewsEnd(p0: Int, p1: Boolean) {
        TODO("Not yet implemented")
    }

    override fun headTimestamp(p0: Int, p1: String?) {
        TODO("Not yet implemented")
    }

    override fun histogramData(p0: Int, p1: MutableList<HistogramEntry>?) {
        TODO("Not yet implemented")
    }

    override fun historicalDataUpdate(p0: Int, p1: Bar?) {
        TODO("Not yet implemented")
    }

    override fun rerouteMktDataReq(p0: Int, p1: Int, p2: String?) {
        TODO("Not yet implemented")
    }

    override fun rerouteMktDepthReq(p0: Int, p1: Int, p2: String?) {
        TODO("Not yet implemented")
    }

    override fun marketRule(p0: Int, p1: Array<out PriceIncrement>?) {
        TODO("Not yet implemented")
    }

    override fun pnl(p0: Int, p1: Double, p2: Double, p3: Double) {
        TODO("Not yet implemented")
    }

    override fun pnlSingle(p0: Int, p1: Decimal?, p2: Double, p3: Double, p4: Double, p5: Double) {
        TODO("Not yet implemented")
    }

    override fun historicalTicks(p0: Int, p1: MutableList<HistoricalTick>?, p2: Boolean) {
        TODO("Not yet implemented")
    }

    override fun historicalTicksBidAsk(p0: Int, p1: MutableList<HistoricalTickBidAsk>?, p2: Boolean) {
        TODO("Not yet implemented")
    }

    override fun historicalTicksLast(p0: Int, p1: MutableList<HistoricalTickLast>?, p2: Boolean) {
        TODO("Not yet implemented")
    }

    override fun tickByTickAllLast(p0: Int, p1: Int, p2: Long, p3: Double, p4: Decimal?, p5: TickAttribLast?, p6: String?, p7: String?) {
        TODO("Not yet implemented")
    }

    override fun tickByTickBidAsk(p0: Int, p1: Long, p2: Double, p3: Double, p4: Decimal?, p5: Decimal?, p6: TickAttribBidAsk?) {
        TODO("Not yet implemented")
    }

    override fun tickByTickMidPoint(p0: Int, p1: Long, p2: Double) {
        TODO("Not yet implemented")
    }

    override fun orderBound(p0: Long, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun completedOrder(p0: Contract?, p1: Order?, p2: OrderState?) {
        TODO("Not yet implemented")
    }

    override fun completedOrdersEnd() {
        TODO("Not yet implemented")
    }

    override fun replaceFAEnd(p0: Int, p1: String?) {
        TODO("Not yet implemented")
    }

    override fun wshMetaData(p0: Int, p1: String?) {
        TODO("Not yet implemented")
    }

    override fun wshEventData(p0: Int, p1: String?) {
        TODO("Not yet implemented")
    }

    override fun historicalSchedule(p0: Int, p1: String?, p2: String?, p3: String?, p4: MutableList<HistoricalSession>?) {
        TODO("Not yet implemented")
    }

    override fun userInfo(p0: Int, p1: String?) {
        TODO("Not yet implemented")
    }


}