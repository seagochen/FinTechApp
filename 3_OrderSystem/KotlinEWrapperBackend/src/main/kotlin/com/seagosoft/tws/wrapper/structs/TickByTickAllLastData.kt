package com.seagosoft.tws.wrapper.structs

import com.ib.client.Decimal
import com.ib.client.TickAttribLast


class TickByTickAllLastData(
    var reqId: Int, var tickType: Int, var time: Long,
    var price: Double, var size: Decimal, var tickAttribLast: TickAttribLast,
    var exchange: String, var specialConditions: String
)