package com.seagosoft.tws.data;

public class tickGenericData {

    private int tickerId;
    private int tickType;
    private double value;

    public tickGenericData(int tickerId, int tickType, double value) {
        this.tickerId = tickerId;
        this.tickType = tickType;
        this.value = value;
    }
}
