package com.seagosoft.tws.data;

public class tickGenericData {

    public int tickerId;
    public int tickType;
    public double value;

    public tickGenericData(int tickerId, int tickType, double value) {
        this.tickerId = tickerId;
        this.tickType = tickType;
        this.value = value;
    }
}
