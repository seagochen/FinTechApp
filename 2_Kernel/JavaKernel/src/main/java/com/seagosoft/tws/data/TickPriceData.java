package com.seagosoft.tws.data;

import com.ib.client.TickAttrib;

// You would also need a class to represent the data structure
public class TickPriceData {
    private int reqId;
    private int field;
    private double price;
    private TickAttrib attrib;

    public TickPriceData(int reqId, int field, double price, TickAttrib attrib) {
        this.reqId = reqId;
        this.field = field;
        this.price = price;
        this.attrib = attrib;
    }
}