package com.seagosoft.tws.wrapper.structs

import com.ib.client.Contract
import com.ib.client.Decimal


class UpdatePortfolioData(
    contract: Contract,
    var position: Decimal,
    var marketPrice: Double,
    var marketValue: Double,
    var averageCost: Double,
    var unrealizedPNL: Double,
    var realizedPNL: Double,
    var accountName: String
) {
    var contract: Contract = contract
}