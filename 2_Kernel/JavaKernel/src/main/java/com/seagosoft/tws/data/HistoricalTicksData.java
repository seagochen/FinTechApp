package com.seagosoft.tws.data;

import com.ib.client.HistoricalTick;

import java.util.List;

public class HistoricalTicksData {

    public int reqId;
    public List<HistoricalTick> ticks;
    public boolean done;

    public HistoricalTicksData(int reqId, List<HistoricalTick> ticks, boolean done) {
        this.reqId = reqId;
        this.ticks = ticks;
        this.done = done;
    }
}
