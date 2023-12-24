package com.seagosoft.tws.data;

public class RerouteMktDataReqData {

    public int reqId;
    public int conId;
    public String exchange;

    public RerouteMktDataReqData(int reqId, int conId, String exchange) {
        this.reqId = reqId;
        this.conId = conId;
        this.exchange = exchange;
    }
}
