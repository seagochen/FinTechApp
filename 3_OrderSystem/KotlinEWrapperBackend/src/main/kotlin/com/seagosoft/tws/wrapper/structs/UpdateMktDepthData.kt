package com.seagosoft.tws.wrapper.structs

import com.ib.client.Decimal


class UpdateMktDepthData(
    var tickerId: Int,
    var position: Int,
    var operation: Int,
    var side: Int,
    var price: Double,
    var size: Decimal
)