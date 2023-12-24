package com.seagosoft.tws.data;

import com.ib.client.Decimal;
import com.ib.client.TickAttribBidAsk;

public class TickByTickBidAskData {

    public int reqId;
    public long time;
    public double bidPrice;
    public double askPrice;
    public Decimal bidSize;
    public Decimal askSize;
    public TickAttribBidAsk tickAttribBidAsk;


    public TickByTickBidAskData(int reqId,
                                long time,
                                double bidPrice,
                                double askPrice,
                                Decimal bidSize,
                                Decimal askSize,
                                TickAttribBidAsk tickAttribBidAsk) {
        this.reqId = reqId;
        this.time = time;
        this.bidPrice = bidPrice;
        this.askPrice = askPrice;
        this.bidSize = bidSize;
        this.askSize = askSize;
        this.tickAttribBidAsk = tickAttribBidAsk;
    }
}
