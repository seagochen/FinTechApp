package com.seagosoft.tws.data;

import com.ib.client.Decimal;

public class UpdateMktDepthL2Data {

    public int tickerId;
    public int position;
    public String marketMaker;
    public int operation;
    public int side;
    public double price;
    public Decimal size;
    public boolean isSmartDepth;

    public UpdateMktDepthL2Data(int tickerId, int position, String marketMaker,
                                int operation, int side, double price, Decimal size,
                                boolean isSmartDepth) {
        this.tickerId = tickerId;
        this.position = position;
        this.marketMaker = marketMaker;
        this.operation = operation;
        this.side = side;
        this.price = price;
        this.size = size;
        this.isSmartDepth = isSmartDepth;
    }
}
