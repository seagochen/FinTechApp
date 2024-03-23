package com.seagosoft.tws.wrapper.structs

import com.ib.client.Decimal


class PnLSingleData(
    var reqId: Int,
    var pos: Decimal,
    var dailyPnL: Double,
    var unrealizedPnL: Double,
    var realizedPnL: Double,
    var value: Double
)