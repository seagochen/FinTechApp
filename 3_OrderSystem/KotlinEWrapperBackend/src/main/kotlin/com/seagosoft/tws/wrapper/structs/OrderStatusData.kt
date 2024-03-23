package com.seagosoft.tws.wrapper.structs

import com.ib.client.Decimal


class OrderStatusData(
    var orderId: Int, var status: String,
    var filled: Decimal, var remaining: Decimal, var avgFillPrice: Double,
    var permId: Int, var parentId: Int, var lastFillPrice: Double, var clientId: Int,
    var whyHeld: String, var mktCapPrice: Double
)