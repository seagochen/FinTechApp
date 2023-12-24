package com.seagosoft.tws.data;

import com.ib.client.Contract;
import com.ib.client.Order;
import com.ib.client.OrderState;

public class CompletedOrderData {

    public Contract contract;
    public Order order;
    public OrderState orderState;

    public CompletedOrderData(Contract contract, Order order, OrderState orderState) {
        this.contract = contract;
        this.order = order;
        this.orderState = orderState;
    }
}
