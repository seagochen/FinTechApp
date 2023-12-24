package com.seagosoft.tws.data;

import com.ib.client.Bar;

public class HistoricalDataData {

    public int reqId;
    public Bar bar;

    public HistoricalDataData(int reqId, Bar bar) {
        this.reqId = reqId;
        this.bar = bar;
    }
}
