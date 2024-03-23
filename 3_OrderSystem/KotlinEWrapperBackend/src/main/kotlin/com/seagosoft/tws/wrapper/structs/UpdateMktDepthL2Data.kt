package com.seagosoft.tws.wrapper.structs

import com.ib.client.Decimal


class UpdateMktDepthL2Data(
    var tickerId: Int, var position: Int, var marketMaker: String,
    var operation: Int, var side: Int, var price: Double, var size: Decimal,
    var isSmartDepth: Boolean
)