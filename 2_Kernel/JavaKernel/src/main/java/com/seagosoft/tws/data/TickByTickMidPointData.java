package com.seagosoft.tws.data;

public class TickByTickMidPointData {

    public long time;
    public double midPoint;
    public int reqId;

    public TickByTickMidPointData(int reqId, long time, double midPoint) {
        this.time = time;
        this.midPoint = midPoint;
        this.reqId = reqId;
    }
}
