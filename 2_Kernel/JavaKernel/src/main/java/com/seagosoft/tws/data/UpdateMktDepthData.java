package com.seagosoft.tws.data;

import com.ib.client.Decimal;

public class UpdateMktDepthData {

    public int tickerId;
    public int position;
    public int operation;
    public int side;
    public double price;
    public Decimal size;

    public UpdateMktDepthData(int tickerId, int position, int operation, int side, double price, Decimal size) {
        this.tickerId = tickerId;
        this.position = position;
        this.operation = operation;
        this.side = side;
        this.price = price;
        this.size = size;
    }
}
