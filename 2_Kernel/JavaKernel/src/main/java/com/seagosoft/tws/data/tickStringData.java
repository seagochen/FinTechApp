package com.seagosoft.tws.data;

public class tickStringData {

    private int tickerId;
    private int tickType;
    private String value;

    public tickStringData(int tickerId, int tickType, String value) {
        this.tickerId = tickerId;
        this.tickType = tickType;
        this.value = value;
    }
}
