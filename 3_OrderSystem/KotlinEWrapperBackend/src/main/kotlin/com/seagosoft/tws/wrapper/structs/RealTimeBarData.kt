package com.seagosoft.tws.wrapper.structs

import com.ib.client.Decimal


class RealTimeBarData(
    var reqId: Int, var time: Long, var open: Double, var high: Double, var low: Double,
    var close: Double, var volume: Decimal, var wap: Decimal, var count: Int
)