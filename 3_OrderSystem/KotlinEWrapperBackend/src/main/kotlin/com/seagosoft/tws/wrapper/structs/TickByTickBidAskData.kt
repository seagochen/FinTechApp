package com.seagosoft.tws.wrapper.structs

import com.ib.client.Decimal
import com.ib.client.TickAttribBidAsk


class TickByTickBidAskData(
    var reqId: Int,
    var time: Long,
    var bidPrice: Double,
    var askPrice: Double,
    var bidSize: Decimal,
    var askSize: Decimal,
    var tickAttribBidAsk: TickAttribBidAsk
)