package com.seagosoft.tws.data;

public class PnLData {

    public int reqId;
    public double dailyPnL;
    public double unrealizedPnL;
    public double realizedPnL;

    public PnLData(int reqId, double dailyPnL, double unrealizedPnL, double realizedPnL) {
        this.reqId = reqId;
        this.dailyPnL = dailyPnL;
        this.unrealizedPnL = unrealizedPnL;
        this.realizedPnL = realizedPnL;
    }
}
