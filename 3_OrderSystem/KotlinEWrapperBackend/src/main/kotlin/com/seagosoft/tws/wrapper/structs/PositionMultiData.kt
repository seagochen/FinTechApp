package com.seagosoft.tws.wrapper.structs

import com.ib.client.Contract
import com.ib.client.Decimal


class PositionMultiData(
    var reqId: Int, var account: String, var modelCode: String,
    contract: Contract, var pos: Decimal, var avgCost: Double
) {
    var contract: Contract = contract
}