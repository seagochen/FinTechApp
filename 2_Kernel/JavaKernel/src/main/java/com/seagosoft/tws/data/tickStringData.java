package com.seagosoft.tws.data;

public class tickStringData {

    public int tickerId;
    public int tickType;
    public String value;

    public tickStringData(int tickerId, int tickType, String value) {
        this.tickerId = tickerId;
        this.tickType = tickType;
        this.value = value;
    }
}
