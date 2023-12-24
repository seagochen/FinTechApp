package com.seagosoft.tws.data;

import com.ib.client.Contract;
import com.ib.client.Order;
import com.ib.client.OrderState;

public class OpenOrderData {
    public int orderId;
    public Contract contract;
    public Order order;
    public OrderState orderState;

    public OpenOrderData(int orderId, com.ib.client.Contract contract,
                         Order order, com.ib.client.OrderState orderState) {
        this.orderId = orderId;
        this.contract = contract;
        this.order = order;
        this.orderState = orderState;
    }
}
