package com.seagosoft.tws.data;

import com.ib.client.Decimal;

public class RealTimeBarData {

    public int reqId;
    public long time;
    public double open;
    public double high;
    public double low;
    public double close;
    public Decimal volume;
    public Decimal wap;
    public int count;

    public RealTimeBarData(int reqId, long time, double open, double high, double low,
                           double close, Decimal volume, Decimal wap, int count) {
        this.reqId = reqId;
        this.time = time;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.wap = wap;
        this.count = count;
    }
}
