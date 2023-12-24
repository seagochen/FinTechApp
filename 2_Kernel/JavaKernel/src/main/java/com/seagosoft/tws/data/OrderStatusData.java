package com.seagosoft.tws.data;

import com.ib.client.Decimal;

public class OrderStatusData {

    public int orderId;
    public String status;
    public Decimal filled;
    public Decimal remaining;
    public double avgFillPrice;
    public int permId;
    public int parentId;
    public double lastFillPrice;
    public int clientId;
    public String whyHeld;
    public double mktCapPrice;

    public OrderStatusData(int orderId, String status,
                           Decimal filled, Decimal remaining, double avgFillPrice,
                           int permId, int parentId, double lastFillPrice, int clientId,
                           String whyHeld, double mktCapPrice) {
        this.orderId = orderId;
        this.status = status;
        this.filled = filled;
        this.remaining = remaining;
        this.avgFillPrice = avgFillPrice;
        this.permId = permId;
        this.parentId = parentId;
        this.lastFillPrice = lastFillPrice;
        this.clientId = clientId;
        this.whyHeld = whyHeld;
        this.mktCapPrice = mktCapPrice;
    }
}
