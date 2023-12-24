package com.seagosoft.tws.data;

import com.ib.client.HistoricalTickLast;

import java.util.List;

public class HistoricalTicksLastData {

    public int reqId;
    public List<HistoricalTickLast> ticks;
    public boolean done;

    public HistoricalTicksLastData(int reqId, List<HistoricalTickLast> ticks, boolean done) {
        this.reqId = reqId;
        this.ticks = ticks;
        this.done = done;
    }
}
