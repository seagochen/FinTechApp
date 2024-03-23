package com.seagosoft.tws.wrapper.structs

class TickEFPData(
    var tickerId: Int,
    var tickType: Int,
    var basisPoints: Double,
    var formattedBasisPoints: String,
    var impliedFuture: Double,
    var holdDays: Int,
    var futureExpiry: String,
    var dividendImpact: Double,
    var dividendsToExpiry: Double
)