package com.seagosoft.tws.data;

public class TickReqParamsData {

    public int tickerId;
    public double minTick;
    public String bboExchange;
    public int snapshotPermissions;

    public TickReqParamsData(int tickerId, double minTick, String bboExchange, int snapshotPermissions) {
        this.tickerId = tickerId;
        this.minTick = minTick;
        this.bboExchange = bboExchange;
        this.snapshotPermissions = snapshotPermissions;
    }
}
