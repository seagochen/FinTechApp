package com.seagosoft.tws.wrapper.structs

class TickOptionComputationData(
    var tickerId: Int,
    var field: Int,
    var tickAttrib: Int,
    var impliedVol: Double,
    var delta: Double,
    var optPrice: Double,
    var pvDividend: Double,
    var gamma: Double,
    var vega: Double,
    var theta: Double,
    var undPrice: Double
)