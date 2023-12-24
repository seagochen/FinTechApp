package com.seagosoft.tws.data;

import com.ib.client.Bar;

public class HistoricalDataUpdateData {

    public int reqId;
    public Bar bar;
    public HistoricalDataUpdateData(int reqId, Bar bar) {
        this.reqId = reqId;
        this.bar = bar;
    }
}
