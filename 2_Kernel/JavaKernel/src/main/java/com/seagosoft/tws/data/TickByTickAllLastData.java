package com.seagosoft.tws.data;

import com.ib.client.Decimal;
import com.ib.client.TickAttribLast;

public class TickByTickAllLastData {

    public int reqId;
    public int tickType;
    public long time;
    public double price;
    public Decimal size;
    public TickAttribLast tickAttribLast;
    public String exchange;
    public String specialConditions;

    public TickByTickAllLastData(int reqId, int tickType, long time,
                                 double price, Decimal size, TickAttribLast tickAttribLast,
                                 String exchange, String specialConditions) {
        this.reqId = reqId;
        this.tickType = tickType;
        this.time = time;
        this.price = price;
        this.size = size;
        this.tickAttribLast = tickAttribLast;
        this.exchange = exchange;
        this.specialConditions = specialConditions;
    }
}
