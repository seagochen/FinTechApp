package com.seagosoft.tws.data;

import com.ib.client.HistoricalTickBidAsk;

import java.util.List;

public class HistoricalTicksBidAskData {

    public int reqId;
    public List<HistoricalTickBidAsk> ticks;
    public boolean done;
    public HistoricalTicksBidAskData(int reqId, List<HistoricalTickBidAsk> ticks, boolean done) {
        this.reqId = reqId;
        this.ticks = ticks;
        this.done = done;
    }
}
