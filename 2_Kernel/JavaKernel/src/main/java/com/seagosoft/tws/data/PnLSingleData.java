package com.seagosoft.tws.data;

import com.ib.client.Decimal;

public class PnLSingleData {

    public int reqId;
    public Decimal pos;
    public double dailyPnL;
    public double unrealizedPnL;
    public double realizedPnL;
    public double value;

    public PnLSingleData(int reqId, Decimal pos, double dailyPnL, double unrealizedPnL, double realizedPnL, double value) {
        this.reqId = reqId;
        this.pos = pos;
        this.dailyPnL = dailyPnL;
        this.unrealizedPnL = unrealizedPnL;
        this.realizedPnL = realizedPnL;
        this.value = value;
    }
}
