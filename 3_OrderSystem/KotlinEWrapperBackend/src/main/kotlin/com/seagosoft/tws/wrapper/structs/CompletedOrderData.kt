package com.seagosoft.tws.wrapper.structs

import com.ib.client.Contract
import com.ib.client.Order
import com.ib.client.OrderState


class CompletedOrderData(contract: Contract, order: Order, var orderState: OrderState) {
    var contract: Contract = contract
    var order: Order = order
}