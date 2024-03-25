package com.seagosoft.tws.wrapper

// Import ZeroMQ classes
import com.seagosoft.tws.zeromq.ZeroMQServer

// Import IB classes
import com.ib.client.*

// Kotlin class wrapper for TWS EWrapper interface
class TwsWrapper_V1 (twsServer: String, twsPort: Int, zeromqPort: Int): EWrapper {

    // Private variables
    private var client: EClientSocket? = null
    private var readySignal: EReaderSignal? = null
    private var currentOrderId: Int = 0

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
        zeromqServer?.start() ?: run {
            println("ZeroMQ server not started")
        }

        // Print a message to the console
        println("TwsWrapper_V1 initialized")
    }


    /**
     *
     * @param tickerId
     * @param field
     * @param price
     * @param attribs
     */
    override fun tickPrice(tickerId: Int, field: Int, price: Double, attribs: TickAttrib?) {
        zeromqServer?.send("TickPrice", EWrapperMsgGenerator.tickPrice(tickerId, field, price, attribs)) ?: run {
            println("TickPrice: " + EWrapperMsgGenerator.tickPrice(tickerId, field, price, attribs))
        }
    }

    /**
     *
     * @param tickerId
     * @param field
     * @param size
     */
    override fun tickSize(tickerId: Int, field: Int, size: Decimal?) {
        zeromqServer?.send("TickSize", EWrapperMsgGenerator.tickSize(tickerId, field, size)) ?: run {
            println("TickSize: " + EWrapperMsgGenerator.tickSize(tickerId, field, size))
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
    override fun tickOptionComputation(
        tickerId: Int, field: Int, tickAttrib: Int, impliedVol: Double, delta: Double, optPrice: Double,
        pvDividend: Double, gamma: Double, vega: Double, theta: Double, undPrice: Double
    ) {
        zeromqServer?.send("TickOptionComputation", EWrapperMsgGenerator.tickOptionComputation(
            tickerId, field, tickAttrib, impliedVol, delta, optPrice,
            pvDividend, gamma, vega, theta, undPrice
        )) ?: run {
            println(
                "TickOptionComputation: " +
                        EWrapperMsgGenerator.tickOptionComputation(
                            tickerId, field, tickAttrib, impliedVol, delta, optPrice,
                            pvDividend, gamma, vega, theta, undPrice
                        )
            )
        }
    }

    /**
     * @param tickerId
     * @param tickType
     * @param value
     */
    override fun tickGeneric(tickerId: Int, tickType: Int, value: Double) {
        zeromqServer?.send("TickGeneric", EWrapperMsgGenerator.tickGeneric(tickerId, tickType, value)) ?: run {
            println("TickGeneric: " + EWrapperMsgGenerator.tickGeneric(tickerId, tickType, value))
        }
    }


    /**
     * @param tickerId
     * @param tickType
     * @param value
     */
    override fun tickString(tickerId: Int, tickType: Int, value: String?) {
        zeromqServer?.send("TickString", EWrapperMsgGenerator.tickString(tickerId, tickType, value)) ?: run {
            println("TickString: " + EWrapperMsgGenerator.tickString(tickerId, tickType, value))
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
    override fun tickEFP(
        tickerId: Int,
        tickType: Int,
        basisPoints: Double,
        formattedBasisPoints: String?,
        impliedFuture: Double,
        holdDays: Int,
        futureExpiry: String?,
        dividendImpact: Double,
        dividendsToExpiry: Double
    ) {
        zeromqServer?.send("TickEFP", EWrapperMsgGenerator.tickEFP(
            tickerId, tickType, basisPoints, formattedBasisPoints, impliedFuture,
            holdDays, futureExpiry, dividendImpact, dividendsToExpiry
        )) ?: run {
            println(
                "TickEFP: " +
                        EWrapperMsgGenerator.tickEFP(
                            tickerId, tickType, basisPoints, formattedBasisPoints, impliedFuture,
                            holdDays, futureExpiry, dividendImpact, dividendsToExpiry
                        )
            )
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
    override fun orderStatus(
        orderId: Int, status: String?,
        filled: Decimal?, remaining: Decimal?, avgFillPrice: Double,
        permId: Int, parentId: Int, lastFillPrice: Double, clientId: Int,
        whyHeld: String?, mktCapPrice: Double
    ) {
        zeromqServer?.send("OrderStatus", EWrapperMsgGenerator.orderStatus(
            orderId, status, filled, remaining, avgFillPrice,
            permId, parentId, lastFillPrice, clientId, whyHeld, mktCapPrice
        )) ?: run {
            println(
                "OrderStatus: " +
                        EWrapperMsgGenerator.orderStatus(
                            orderId, status, filled, remaining, avgFillPrice,
                            permId, parentId, lastFillPrice, clientId, whyHeld, mktCapPrice
                        )
            )
        }
    }

    /**
     *
     * @param orderId
     * @param contract
     * @param order
     * @param orderState
     */
    override fun openOrder(orderId: Int, contract: Contract?, order: Order?, orderState: OrderState?) {
        zeromqServer?.send("OpenOrder", EWrapperMsgGenerator.openOrder(orderId, contract, order, orderState)) ?: run {
            println(
                "OpenOrder: " +
                        EWrapperMsgGenerator.openOrder(orderId, contract, order, orderState)
            )
        }
    }

    /**
     *
     */
    override fun openOrderEnd() {
        zeromqServer?.send("OpenOrderEnd", "") ?: run {
            println("OpenOrderEnd")
        }
    }

    /**
     *
     * @param key
     * @param value
     * @param currency
     * @param accountName
     */
    override fun updateAccountValue(key: String?, value: String?, currency: String?, accountName: String?) {
        zeromqServer?.send("UpdateAccountValue",
            EWrapperMsgGenerator.updateAccountValue(key, value, currency, accountName)) ?: run {
            println(
                "UpdateAccountValue: " +
                        EWrapperMsgGenerator.updateAccountValue(key, value, currency, accountName)
            )
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
    override fun updatePortfolio(
        contract: Contract?,
        position: Decimal?,
        marketPrice: Double,
        marketValue: Double,
        averageCost: Double,
        unrealizedPNL: Double,
        realizedPNL: Double,
        accountName: String?
    ) {
        zeromqServer?.send("UpdatePortfolio", EWrapperMsgGenerator.updatePortfolio(
            contract, position, marketPrice, marketValue,
            averageCost, unrealizedPNL, realizedPNL, accountName
        )) ?: run {
            println(
                "UpdatePortfolio: " +
                        EWrapperMsgGenerator.updatePortfolio(
                            contract, position, marketPrice, marketValue,
                            averageCost, unrealizedPNL, realizedPNL, accountName
                        )
            )
        }
    }


    /**
     * @param timestamp
     */
    override fun updateAccountTime(timestamp: String?) {
        zeromqServer?.send("UpdateAccountTime", timestamp!!) ?: run {
            println("UpdateAccountTime: " + EWrapperMsgGenerator.updateAccountTime(timestamp))
        }
    }

    /**
     * @param account
     */
    override fun accountDownloadEnd(account: String?) {
        zeromqServer?.send("AccountDownloadEnd", account!!) ?: run {
            println("AccountDownloadEnd: " + EWrapperMsgGenerator.accountDownloadEnd(account))
        }
    }

    /**
     * @param numIds
     */
    override fun nextValidId(numIds: Int) {
        currentOrderId = numIds
        zeromqServer?.send("NextValidId", numIds.toString()) ?: run {
            println("NextValidId: " + EWrapperMsgGenerator.nextValidId(numIds))
        }
    }

    /**
     * @param reqId
     * @param contractDetails
     */
    override fun contractDetails(reqId: Int, contractDetails: ContractDetails?) {
        zeromqServer?.send("ContractDetails",
            EWrapperMsgGenerator.contractDetails(reqId, contractDetails)) ?: run {
            println("ContractDetails: " + EWrapperMsgGenerator.contractDetails(reqId, contractDetails))
        }
    }

    /**
     * @param reqId
     * @param contractDetails
     */
    override fun bondContractDetails(reqId: Int, contractDetails: ContractDetails?) {
        zeromqServer?.send("BondContractDetails",
            EWrapperMsgGenerator.bondContractDetails(reqId, contractDetails)) ?: run {
            println("BondContractDetails: " +
                    EWrapperMsgGenerator.bondContractDetails(reqId, contractDetails))
        }
    }

    /**
     * @param reqId
     */
    override fun contractDetailsEnd(reqId: Int) {
        zeromqServer?.send("ContractDetailsEnd", reqId.toString()) ?: run {
            println("ContractDetailsEnd: " + EWrapperMsgGenerator.contractDetailsEnd(reqId))
        }
    }

    /**
     * @param reqId
     * @param contract
     * @param execution
     */
    override fun execDetails(reqId: Int, contract: Contract?, execution: Execution?) {
        zeromqServer?.send("ExecDetails",
            EWrapperMsgGenerator.execDetails(reqId, contract, execution)) ?: run {
            println("ExecDetails: " + EWrapperMsgGenerator.execDetails(reqId, contract, execution))
        }
    }

    /**
     * @param reqId
     */
    override fun execDetailsEnd(reqId: Int) {
        zeromqServer?.send("ExecDetailsEnd", reqId.toString()) ?: run {
            println("ExecDetailsEnd: " + EWrapperMsgGenerator.execDetailsEnd(reqId))
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
    override fun updateMktDepth(
        tickerId: Int,
        position: Int,
        operation: Int,
        side: Int,
        price: Double,
        size: Decimal?
    ) {
        zeromqServer?.send("UpdateMktDepth",
            EWrapperMsgGenerator.updateMktDepth(tickerId, position, operation, side, price, size)) ?: run {
            println(
                "UpdateMktDepth: " +
                        EWrapperMsgGenerator.updateMktDepth(tickerId, position, operation, side, price, size)
            )
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
    override fun updateMktDepthL2(
        tickerId: Int, position: Int, marketMaker: String?,
        operation: Int, side: Int, price: Double, size: Decimal?,
        isSmartDepth: Boolean
    ) {
        zeromqServer?.send("UpdateMktDepthL2", EWrapperMsgGenerator.updateMktDepthL2(
            tickerId, position, marketMaker, operation, side, price, size, isSmartDepth
        )) ?: run {
            println(
                "UpdateMktDepthL2: " +
                        EWrapperMsgGenerator.updateMktDepthL2(
                            tickerId, position, marketMaker, operation, side, price, size, isSmartDepth)
            )
        }
    }


    /**
     *
     * @param msgId
     * @param msgType
     * @param message
     * @param origExchange
     */
    override fun updateNewsBulletin(msgId: Int, msgType: Int, message: String?, origExchange: String?) {
        zeromqServer?.send("UpdateNewsBulletin",
            EWrapperMsgGenerator.updateNewsBulletin(msgId, msgType, message, origExchange)) ?: run {
            println(
                "UpdateNewsBulletin: " +
                        EWrapperMsgGenerator.updateNewsBulletin(msgId, msgType, message, origExchange)
            )
        }
    }

    /**
     *
     * @param accountsList
     */
    override fun managedAccounts(accountsList: String?) {
        accountsList?.let {
            zeromqServer?.send("ManagedAccounts", it) ?: run {
                println("ManagedAccounts: " + EWrapperMsgGenerator.managedAccounts(accountsList))
            }
        } ?: run {
            println("ManagedAccounts: accountsList is null")
        }
    }

    /**
     *
     * @param faDataType
     * @param xml
     */
    override fun receiveFA(faDataType: Int, xml: String?) {
        zeromqServer?.send("ReceiveFA", EWrapperMsgGenerator.receiveFA(faDataType, xml)) ?: run {
            println("ReceiveFA: " + EWrapperMsgGenerator.receiveFA(faDataType, xml))
        }
    }

    /**
     * @param reqId
     * @param bar
     */
    override fun historicalData(reqId: Int, bar: Bar) {
        zeromqServer?.send("HistoricalData", EWrapperMsgGenerator.historicalData(reqId, bar.time(),
            bar.open(), bar.high(), bar.low(),
            bar.close(), bar.volume(), bar.count(), bar.wap())) ?: run {
            println("HistoricalData: " +
                    EWrapperMsgGenerator.historicalData(reqId, bar.time(), bar.open(), bar.high(), bar.low(),
                        bar.close(), bar.volume(), bar.count(), bar.wap()))
        }
    }

    /**
     * @param xml
     */
    override fun scannerParameters(xml: String?) {
        zeromqServer?.send("ScannerParameters", xml!!) ?: run {
            println("ScannerParameters: " + EWrapperMsgGenerator.scannerParameters(xml))
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
    override fun scannerData(
        reqId: Int, rank: Int, contractDetails: ContractDetails?, distance: String?,
        benchmark: String?, projection: String?, legsStr: String?
    ) {
        zeromqServer?.send("ScannerData", EWrapperMsgGenerator.scannerData(
            reqId, rank, contractDetails, distance, benchmark, projection, legsStr)) ?: run {
            println(
                "ScannerData: " +
                        EWrapperMsgGenerator.scannerData(
                            reqId, rank, contractDetails, distance, benchmark, projection, legsStr)
            )
        }
    }

    /**
     * @param reqId
     */
    override fun scannerDataEnd(reqId: Int) {
        zeromqServer?.send("ScannerDataEnd", reqId.toString()) ?: run {
            println("ScannerDataEnd: " + EWrapperMsgGenerator.scannerDataEnd(reqId))
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
    override fun realtimeBar(
        reqId: Int, time: Long, open: Double, high: Double, low: Double,
        close: Double, volume: Decimal?, wap: Decimal?, count: Int
    ) {
        zeromqServer?.send("RealTimeBar", EWrapperMsgGenerator.realtimeBar(
            reqId, time, open, high, low, close, volume, wap, count
        )) ?: run {
            println(
                "RealTimeBar: " +
                        EWrapperMsgGenerator.realtimeBar(
                            reqId, time, open, high, low, close, volume, wap, count
                        )
            )
        }
    }


    /**
     * @param time
     */
    override fun currentTime(time: Long) {
        zeromqServer?.send("CurrentTime", time.toString()) ?: run {
            println("CurrentTime: " + EWrapperMsgGenerator.currentTime(time))
        }
    }

    /**
     *
     * @param reqId
     * @param data
     */
    override fun fundamentalData(reqId: Int, data: String?) {
        zeromqServer?.send("FundamentalData",
            EWrapperMsgGenerator.fundamentalData(reqId, data)) ?: run {
            println("FundamentalData: " + EWrapperMsgGenerator.fundamentalData(reqId, data))
        }
    }

    /**
     *
     * @param reqId
     * @param underComp
     */
    override fun deltaNeutralValidation(reqId: Int, underComp: DeltaNeutralContract?) {
        zeromqServer?.send("DeltaNeutralValidation",
            EWrapperMsgGenerator.deltaNeutralValidation(reqId, underComp)) ?: run {
            println("DeltaNeutralValidation: " +
                    EWrapperMsgGenerator.deltaNeutralValidation(reqId, underComp)
            )
        }
    }

    /**
     *
     * @param reqId
     */
    override fun tickSnapshotEnd(reqId: Int) {
        zeromqServer?.send("TickSnapshotEnd", reqId.toString()) ?: run {
            println("TickSnapshotEnd: " + EWrapperMsgGenerator.tickSnapshotEnd(reqId))
        }
    }

    /**
     *
     * @param reqId
     * @param marketDataType
     */
    override fun marketDataType(reqId: Int, marketDataType: Int) {
        zeromqServer?.send("MarketDataType",
            EWrapperMsgGenerator.marketDataType(reqId, marketDataType)) ?: run {
            println("MarketDataType: " + EWrapperMsgGenerator.marketDataType(reqId, marketDataType))
        }
    }


    /**
     * @param commissionReport
     */
    override fun commissionReport(commissionReport: CommissionReport?) {
        zeromqServer?.send("CommissionReport",
            EWrapperMsgGenerator.commissionReport(commissionReport)) ?: run {
            println("CommissionReport: " + EWrapperMsgGenerator.commissionReport(commissionReport))
        }
    }

    /**
     *
     * @param account
     * @param contract
     * @param pos
     * @param avgCost
     */
    override fun position(account: String?, contract: Contract?, pos: Decimal?, avgCost: Double) {
        zeromqServer?.send("Position",
            EWrapperMsgGenerator.position(account, contract, pos, avgCost)) ?: run {
            println("Position: " + EWrapperMsgGenerator.position(account, contract, pos, avgCost))
        }
    }

    /**
     *
     */
    override fun positionEnd() {
        zeromqServer?.send("PositionEnd", "") ?: run {
            println("PositionEnd: " + EWrapperMsgGenerator.positionEnd())
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
    override fun accountSummary(reqId: Int, account: String?, tag: String?, value: String?, currency: String?) {
        zeromqServer?.send("AccountSummary",
            EWrapperMsgGenerator.accountSummary(reqId, account, tag, value, currency)) ?: run {
            println(
                "AccountSummary: " +
                        EWrapperMsgGenerator.accountSummary(reqId, account, tag, value, currency)
            )
        }
    }

    /**
     *
     * @param reqId
     */
    override fun accountSummaryEnd(reqId: Int) {
        zeromqServer?.send("AccountSummaryEnd", reqId.toString()) ?: run {
            println("AccountSummaryEnd: " + EWrapperMsgGenerator.accountSummaryEnd(reqId))
        }
    }


    /**
     *
     * @param s
     */
    override fun verifyMessageAPI(s: String) {
        zeromqServer?.send("VerifyMessageAPI", s) ?: run {
            println("VerifyMessageAPI: $s")
        }
    }


    /**
     *
     * @param b
     * @param s
     */
    override fun verifyCompleted(b: Boolean, s: String) {
        zeromqServer?.send("VerifyCompleted", "$b,$s") ?: run {
            println("VerifyCompleted: $b, $s")
        }
    }

    /**
     *
     * @param s
     * @param s1
     */
    override fun verifyAndAuthMessageAPI(s: String, s1: String) {
        zeromqServer?.send("VerifyAndAuthMessageAPI", "$s,$s1") ?: run {
            println("VerifyAndAuthMessageAPI: $s, $s1")
        }
    }

    /**
     *
     * @param b
     * @param s
     */
    override fun verifyAndAuthCompleted(b: Boolean, s: String) {
        zeromqServer?.send("VerifyAndAuthCompleted", "$b,$s") ?: run {
            println("VerifyAndAuthCompleted: $b, $s")
        }
    }

    /**
     * @param requestId
     * @param groups
     */
    override fun displayGroupList(requestId: Int, groups: String) {
        zeromqServer?.send("DisplayGroupList", "$requestId,$groups") ?: run {
            println("DisplayGroupList: $requestId, $groups")
        }
    }

    /**
     * @param requestId
     * @param contractInfo
     */
    override fun displayGroupUpdated(requestId: Int, contractInfo: String) {
        zeromqServer?.send("DisplayGroupUpdated", "$requestId,$contractInfo") ?: run {
            println("DisplayGroupUpdated: $requestId, $contractInfo")
        }
    }

    /**
     * @param e
     */
    override fun error(e: Exception) {
        zeromqServer?.send("Error", e.toString()) ?: run {
            println("Error: $e")
        }
    }

    /**
     * @param s
     */
    override fun error(s: String) {
        zeromqServer?.send("Error", s) ?: run {
            println("Error: $s")
        }
    }

    /**
     *
     * @param reqId
     * @param errorCode
     * @param errorMsg
     * @param advancedOrderRejectJson
     */
    override fun error(reqId: Int, errorCode: Int, errorMsg: String, advancedOrderRejectJson: String?) {
        var str = "Error. Id: $reqId, Code: $errorCode, Msg: $errorMsg"
        if (advancedOrderRejectJson != null) {
            str += (", AdvancedOrderRejectJson: $advancedOrderRejectJson")
        }

        zeromqServer?.send("Error", str) ?: run {
            println("Error: $str")
        }
    }

    /**
     *
     */
    override fun connectionClosed() {
        zeromqServer?.send("ConnectionClosed", "") ?: run {
            println("ConnectionClosed")
        }
    }

    /**
     *
     */
    override fun connectAck() {
        zeromqServer?.send("ConnectAck", "") ?: run {
            println("ConnectAck")
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
    override fun positionMulti(
        reqId: Int, account: String?, modelCode: String?,
        contract: Contract?, pos: Decimal?, avgCost: Double
    ) {
        zeromqServer?.send("PositionMulti", EWrapperMsgGenerator.positionMulti(
            reqId, account, modelCode, contract, pos, avgCost
        )) ?: run {
            println(
                "PositionMulti: " +
                        EWrapperMsgGenerator.positionMulti(
                            reqId, account, modelCode, contract, pos, avgCost
                        )
            )
        }
    }

    /**
     * @param requestId
     */
    override fun positionMultiEnd(requestId: Int) {
        zeromqServer?.send("PositionMultiEnd", requestId.toString()) ?: run {
            println("PositionMultiEnd: " + EWrapperMsgGenerator.positionMultiEnd(requestId))
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
    override fun accountUpdateMulti(
        reqId: Int, account: String?, modelCode: String?,
        key: String?, value: String?, currency: String?
    ) {
        zeromqServer?.send("AccountUpdateMulti", EWrapperMsgGenerator.accountUpdateMulti(
            reqId, account, modelCode, key, value, currency
        )) ?: run {
            println(
                "AccountUpdateMulti: " +
                        EWrapperMsgGenerator.accountUpdateMulti(
                            reqId, account, modelCode, key, value, currency
                        )
            )
        }
    }

    /**
     * @param requestId
     */
    override fun accountUpdateMultiEnd(requestId: Int) {
        zeromqServer?.send("AccountUpdateMultiEnd", requestId.toString()) ?: run {
            println("AccountUpdateMultiEnd: " + EWrapperMsgGenerator.accountUpdateMultiEnd(requestId))
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
    override fun securityDefinitionOptionalParameter(
        reqId: Int,
        exchange: String?,
        underlyingConId: Int,
        tradingClass: String?,
        multiplier: String?,
        expirations: MutableSet<String>?,
        strikes: MutableSet<Double>?
    ) {
        zeromqServer?.send("SecurityDefinitionOptionalParameter",
            EWrapperMsgGenerator.securityDefinitionOptionalParameter(
            reqId, exchange, underlyingConId, tradingClass, multiplier, expirations, strikes
        )) ?: run {
            println(
                "SecurityDefinitionOptionalParameter: " +
                        EWrapperMsgGenerator.securityDefinitionOptionalParameter(
                            reqId, exchange, underlyingConId, tradingClass, multiplier, expirations, strikes
                        )
            )
        }
    }

    /**
     * @param reqId
     */
    override fun securityDefinitionOptionalParameterEnd(reqId: Int) {
        zeromqServer?.send("SecurityDefinitionOptionalParameterEnd", reqId.toString()) ?: run {
            println("SecurityDefinitionOptionalParameterEnd: " +
                    EWrapperMsgGenerator.securityDefinitionOptionalParameterEnd(reqId))
        }
    }

    /**
     * @param reqId
     * @param softDollarTiers
     */
    override fun softDollarTiers(reqId: Int, softDollarTiers: Array<SoftDollarTier?>?) {
        // Send to the ZeroMQ server
        zeromqServer?.send("SoftDollarTiers",
            EWrapperMsgGenerator.softDollarTiers(reqId, softDollarTiers)) ?: run {
            println(
                "SoftDollarTiers: " +
                        EWrapperMsgGenerator.softDollarTiers(reqId, softDollarTiers)
            )
        }
    }

    /**
     * @param familyCodes
     */
    override fun familyCodes(familyCodes: Array<FamilyCode?>?) {
        zeromqServer?.send("FamilyCodes",
            EWrapperMsgGenerator.familyCodes(familyCodes)) ?: run {
            println(
                "FamilyCodes: " +
                        EWrapperMsgGenerator.familyCodes(familyCodes)
            )
        }
    }

    /**
     * @param reqId
     * @param contractDescriptions
     */
    override fun symbolSamples(reqId: Int, contractDescriptions: Array<ContractDescription?>?) {
        zeromqServer?.send("SymbolSamples",
            EWrapperMsgGenerator.symbolSamples(reqId, contractDescriptions)) ?: run {
            println(
                "SymbolSamples: " +
                        EWrapperMsgGenerator.symbolSamples(reqId, contractDescriptions)
            )
        }
    }

    /**
     *
     * @param reqId
     * @param startDateStr
     * @param endDateStr
     */
    override fun historicalDataEnd(reqId: Int, startDateStr: String?, endDateStr: String?) {
        zeromqServer?.send("HistoricalDataEnd",
            EWrapperMsgGenerator.historicalDataEnd(reqId, startDateStr, endDateStr)) ?: run {
            println(
                "HistoricalDataEnd: " +
                        EWrapperMsgGenerator.historicalDataEnd(reqId, startDateStr, endDateStr)
            )
        }
    }

    /**
     * @param depthMktDataDescriptions
     */
    override fun mktDepthExchanges(depthMktDataDescriptions: Array<DepthMktDataDescription?>?) {
        zeromqServer?.send("MktDepthExchanges",
            EWrapperMsgGenerator.mktDepthExchanges(depthMktDataDescriptions)) ?: run {
            println(
                "MktDepthExchanges: " +
                        EWrapperMsgGenerator.mktDepthExchanges(depthMktDataDescriptions)
            )
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
    override fun tickNews(
        tickerId: Int, timeStamp: Long, providerCode: String?,
        articleId: String?, headline: String?, extraData: String?
    ) {
        zeromqServer?.send("TickNews", EWrapperMsgGenerator.tickNews(
            tickerId, timeStamp, providerCode, articleId, headline, extraData
        )) ?: run {
            println(
                "TickNews: " +
                        EWrapperMsgGenerator.tickNews(
                            tickerId, timeStamp, providerCode, articleId, headline, extraData
                        )
            )
        }
    }


    /**
     *
     * @param reqId
     * @param theMap
     */
    override fun smartComponents(reqId: Int, theMap: Map<Int?, Map.Entry<String?, Char?>?>?) {
        zeromqServer?.send("SmartComponents",
            EWrapperMsgGenerator.smartComponents(reqId, theMap)) ?: run {
            println(
                "SmartComponents: " +
                        EWrapperMsgGenerator.smartComponents(reqId, theMap)
            )
        }
    }

    /**
     *
     * @param tickerId
     * @param minTick
     * @param bboExchange
     * @param snapshotPermissions
     */
    override fun tickReqParams(tickerId: Int, minTick: Double, bboExchange: String?, snapshotPermissions: Int) {
        zeromqServer?.send("TickReqParams",
            EWrapperMsgGenerator.tickReqParams(tickerId, minTick, bboExchange, snapshotPermissions)) ?: run {
            println(
                "TickReqParams: " +
                        EWrapperMsgGenerator.tickReqParams(tickerId, minTick, bboExchange, snapshotPermissions)
            )
        }
    }

    /**
     * @param newsProviders
     */
    override fun newsProviders(newsProviders: Array<NewsProvider?>?) {
        zeromqServer?.send("NewsProviders",
            EWrapperMsgGenerator.newsProviders(newsProviders)) ?: run {
            println(
                "NewsProviders: " +
                        EWrapperMsgGenerator.newsProviders(newsProviders)
            )
        }
    }


    /**
     *
     * @param requestId
     * @param articleType
     * @param articleText
     */
    override fun newsArticle(requestId: Int, articleType: Int, articleText: String?) {
        zeromqServer?.send("NewsArticle",
            EWrapperMsgGenerator.newsArticle(requestId, articleType, articleText)) ?: run {
            println(
                "NewsArticle: " +
                        EWrapperMsgGenerator.newsArticle(requestId, articleType, articleText)
            )
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
    override fun historicalNews(
        requestId: Int, time: String?, providerCode: String?,
        articleId: String?, headline: String?
    ) {
        zeromqServer?.send("HistoricalNews", EWrapperMsgGenerator.historicalNews(
            requestId, time, providerCode, articleId, headline)) ?: run {
            println(
                "HistoricalNews: " +
                        EWrapperMsgGenerator.historicalNews(
                            requestId, time, providerCode, articleId, headline
                        )
            )
        }
    }

    /**
     * @param requestId
     * @param hasMore
     */
    override fun historicalNewsEnd(requestId: Int, hasMore: Boolean) {
        zeromqServer?.send("HistoricalNewsEnd",
            EWrapperMsgGenerator.historicalNewsEnd(requestId, hasMore)) ?: run {
            println(
                "HistoricalNewsEnd: " +
                        EWrapperMsgGenerator.historicalNewsEnd(requestId, hasMore)
            )
        }
    }

    /**
     *
     * @param requestId
     * @param headTimestamp
     */
    override fun headTimestamp(requestId: Int, headTimestamp: String?) {
        zeromqServer?.send("HeadTimestamp",
            EWrapperMsgGenerator.headTimestamp(requestId, headTimestamp)) ?: run {
            println(
                "HeadTimestamp: " +
                        EWrapperMsgGenerator.headTimestamp(requestId, headTimestamp)
            )
        }
    }

    /**
     * @param requestId
     * @param list
     */
    override fun histogramData(requestId: Int, list: List<HistogramEntry?>?) {
        zeromqServer?.send("HistogramData",
            EWrapperMsgGenerator.histogramData(requestId, list)) ?: run {
            println(
                "HistogramData: " +
                        EWrapperMsgGenerator.histogramData(requestId, list)
            )
        }
    }

    /**
     * @param reqId
     * @param bar
     */
    override fun historicalDataUpdate(reqId: Int, bar: Bar) {
        zeromqServer?.send("HistoricalDataUpdate",
            EWrapperMsgGenerator.historicalData(reqId, bar.time(), bar.open(), bar.high(),
            bar.low(), bar.close(), bar.volume(), bar.count(), bar.wap())) ?: run {
            println(
                "HistoricalDataUpdate: " +
                        EWrapperMsgGenerator.historicalData(reqId, bar.time(), bar.open(), bar.high(),
                            bar.low(), bar.close(), bar.volume(), bar.count(), bar.wap()))
        }
    }

    /**
     *
     * @param reqId
     * @param conId
     * @param exchange
     */
    override fun rerouteMktDataReq(reqId: Int, conId: Int, exchange: String?) {
        zeromqServer?.send("RerouteMktDataReq",
            EWrapperMsgGenerator.rerouteMktDataReq(reqId, conId, exchange)) ?: run {
            println(
                "RerouteMktDataReq: " +
                        EWrapperMsgGenerator.rerouteMktDataReq(reqId, conId, exchange)
            )
        }
    }

    /**
     *
     * @param reqId
     * @param conId
     * @param exchange
     */
    override fun rerouteMktDepthReq(reqId: Int, conId: Int, exchange: String?) {
        zeromqServer?.send("RerouteMktDepthReq",
            EWrapperMsgGenerator.rerouteMktDepthReq(reqId, conId, exchange)) ?: run {
            println(
                "RerouteMktDepthReq: " +
                        EWrapperMsgGenerator.rerouteMktDepthReq(reqId, conId, exchange)
            )
        }
    }


    /**
     *
     * @param marketRuleId
     * @param priceIncrements
     */
    override fun marketRule(marketRuleId: Int, priceIncrements: Array<PriceIncrement?>?) {
        zeromqServer?.send("MarketRule",
            EWrapperMsgGenerator.marketRule(marketRuleId, priceIncrements)) ?: run {
            println(
                "MarketRule: " +
                        EWrapperMsgGenerator.marketRule(marketRuleId, priceIncrements)
            )
        }
    }


    /**
     *
     * @param reqId
     * @param dailyPnL
     * @param unrealizedPnL
     * @param realizedPnL
     */
    override fun pnl(reqId: Int, dailyPnL: Double, unrealizedPnL: Double, realizedPnL: Double) {
        zeromqServer?.send("PnL",
            EWrapperMsgGenerator.pnl(reqId, dailyPnL, unrealizedPnL, realizedPnL)) ?: run {
            println(
                "PnL: " +
                        EWrapperMsgGenerator.pnl(reqId, dailyPnL, unrealizedPnL, realizedPnL)
            )
        }
    }


    /**
     *
     * @param reqId
     * @param pos
     * @param dailyPnL
     * @param unrealizedPnL
     * @param realizedPnL
     * @param value
     */
    override fun pnlSingle(
        reqId: Int, pos: Decimal?, dailyPnL: Double,
        unrealizedPnL: Double, realizedPnL: Double, value: Double
    ) {
        zeromqServer?.send("PnLSingle",
            EWrapperMsgGenerator.pnlSingle(reqId, pos, dailyPnL, unrealizedPnL, realizedPnL, value)) ?: run {
            println(
                "PnLSingle: " +
                        EWrapperMsgGenerator.pnlSingle(reqId, pos, dailyPnL, unrealizedPnL, realizedPnL, value)
            )
        }
    }

    /**
     *
     * @param reqId
     * @param ticks
     * @param done
     */
    override fun historicalTicks(reqId: Int, ticks: List<HistoricalTick>, done: Boolean) {
        if (zeromqServer != null) {
            for (tick in ticks) {
                zeromqServer?.send(
                    "HistoricalTicks",
                            EWrapperMsgGenerator.historicalTick(reqId, tick.time(), tick.price(), tick.size())
                )
            }
        } else {
            for (tick in ticks) {
                println(
                    "HistoricalTicks: " +
                            EWrapperMsgGenerator.historicalTick(reqId, tick.time(), tick.price(), tick.size())
                )
            }
        }
    }

    /**
     *
     * @param reqId
     * @param ticks
     * @param done
     */
    override fun historicalTicksBidAsk(reqId: Int, ticks: List<HistoricalTickBidAsk>, done: Boolean) {
        if (zeromqServer != null) {
            for (tick in ticks) {
                zeromqServer?.send(
                    "HistoricalTicksBidAsk: ",
                            EWrapperMsgGenerator.historicalTickBidAsk(reqId, tick.time(), tick.tickAttribBidAsk(),
                                tick.priceBid(), tick.priceAsk(), tick.sizeBid(), tick.sizeAsk())
                )
            }
        } else {
            for (tick in ticks) {
                println(
                    "HistoricalTicksBidAsk: " +
                            EWrapperMsgGenerator.historicalTickBidAsk(reqId, tick.time(), tick.tickAttribBidAsk(),
                                tick.priceBid(), tick.priceAsk(), tick.sizeBid(), tick.sizeAsk())
                )
            }
        }
    }

    /**
     *
     * @param reqId
     * @param ticks
     * @param done
     */
    override fun historicalTicksLast(reqId: Int, ticks: List<HistoricalTickLast>, done: Boolean) {

        if (zeromqServer != null) {

            for (tick in ticks) {
                zeromqServer?.send(
                    "HistoricalTicksLast: ",
                            EWrapperMsgGenerator.historicalTickLast(reqId, tick.time(), tick.tickAttribLast(),
                                tick.price(), tick.size(), tick.exchange(), tick.specialConditions())
                )
            }
        } else {
            for (tick in ticks) {
                println(
                    "HistoricalTicksLast: " +
                            EWrapperMsgGenerator.historicalTickLast(reqId, tick.time(), tick.tickAttribLast(),
                                tick.price(), tick.size(), tick.exchange(), tick.specialConditions())
                )
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
    override fun tickByTickAllLast(
        reqId: Int, tickType: Int, time: Long,
        price: Double, size: Decimal?, tickAttribLast: TickAttribLast?,
        exchange: String?, specialConditions: String?
    ) {
        zeromqServer?.send("TickByTickAllLast", EWrapperMsgGenerator.tickByTickAllLast(
            reqId, tickType, time, price, size, tickAttribLast, exchange, specialConditions
        )) ?: run {
            println(
                "TickByTickAllLast: " +
                        EWrapperMsgGenerator.tickByTickAllLast(
                            reqId, tickType, time, price, size, tickAttribLast, exchange, specialConditions
                        )
            )
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
    override fun tickByTickBidAsk(
        reqId: Int,
        time: Long,
        bidPrice: Double,
        askPrice: Double,
        bidSize: Decimal?,
        askSize: Decimal?,
        tickAttribBidAsk: TickAttribBidAsk?
    ) {
        zeromqServer?.send("TickByTickBidAsk", EWrapperMsgGenerator.tickByTickBidAsk(
            reqId, time, bidPrice, askPrice, bidSize, askSize, tickAttribBidAsk
        )) ?: run {
            println(
                "TickByTickBidAsk: " +
                        EWrapperMsgGenerator.tickByTickBidAsk(
                            reqId, time, bidPrice, askPrice, bidSize, askSize, tickAttribBidAsk
                        )
            )
        }
    }


    /**
     *
     * @param reqId
     * @param time
     * @param midPoint
     */
    override fun tickByTickMidPoint(reqId: Int, time: Long, midPoint: Double) {
        zeromqServer?.send("TickByTickMidPoint",
            EWrapperMsgGenerator.tickByTickMidPoint(reqId, time, midPoint)) ?: run {
            println(
                "TickByTickMidPoint: " +
                        EWrapperMsgGenerator.tickByTickMidPoint(reqId, time, midPoint)
            )
        }
    }


    /**
     *
     * @param orderId
     * @param apiClientId
     * @param apiOrderId
     */
    override fun orderBound(orderId: Long, apiClientId: Int, apiOrderId: Int) {
        zeromqServer?.send(
            "OrderBound",
            "$orderId,$apiClientId,$apiOrderId"
        ) ?: run {
            println(
                "OrderBound: " +
                        EWrapperMsgGenerator.orderBound(orderId, apiClientId, apiOrderId)
            )
        }
    }

    /**
     * @param contract
     * @param order
     * @param orderState
     */
    override fun completedOrder(contract: Contract?, order: Order?, orderState: OrderState?) {
        zeromqServer?.send("CompletedOrder",
            EWrapperMsgGenerator.completedOrder(contract, order, orderState)) ?: run {
            println(
                "CompletedOrder: " +
                        EWrapperMsgGenerator.completedOrder(contract, order, orderState)
            )
        }
    }

    /**
     *
     */
    override fun completedOrdersEnd() {
        zeromqServer?.send("CompletedOrdersEnd", "") ?: run {
            println("CompletedOrdersEnd")
        }
    }

    /**
     * @param reqId
     * @param s
     */
    override fun replaceFAEnd(reqId: Int, s: String) {
        zeromqServer?.send("ReplaceFAEnd", "$reqId,$s") ?: run {
            println("ReplaceFAEnd: $reqId,$s")
        }
    }

    /**
     * @param i
     * @param s
     */
    override fun wshMetaData(i: Int, s: String) {
        zeromqServer?.send("WshMetaData", "$i,$s") ?: run {
            println("WshMetaData: $i,$s")
        }
    }

    /**
     * @param requestId
     * @param requestId
     */
    override fun wshEventData(requestId: Int, dataJson: String) {
        zeromqServer?.send("WshEventData", "$requestId,$dataJson") ?: run {
            println("WshEventData: $requestId,$dataJson")
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
    override fun historicalSchedule(
        reqId: Int,
        startDateTime: String?,
        endDateTime: String?,
        timeZone: String?,
        sessions: List<HistoricalSession>?
    ) {
        zeromqServer?.send("HistoricalSchedule", EWrapperMsgGenerator.historicalSchedule(
            reqId, startDateTime, endDateTime, timeZone, sessions
        )) ?: run {
            println(
                "HistoricalSchedule: " +
                        EWrapperMsgGenerator.historicalSchedule(
                            reqId, startDateTime, endDateTime, timeZone, sessions
                        )
            )
        }
    }

    /**
     * @param reqId
     * @param whiteBrandingId
     */
    override fun userInfo(reqId: Int, whiteBrandingId: String) {
        zeromqServer?.send("UserInfo", "$reqId,$whiteBrandingId") ?: run {
            println("UserInfo: $reqId,$whiteBrandingId")
        }
    }
}