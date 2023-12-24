package com.seagosoft.tws.data;

import com.ib.client.Decimal;

public class TickSizeData {
    
    public int tickerId;
    public int field;
    public Decimal size;

    public TickSizeData(int tickerId, int field, Decimal size) {
        this.tickerId = tickerId;
        this.field = field;
        this.size = size;
    }
}
