package com.seagosoft.tws.data;

public class RerouteMktDepthReqData {

    public int reqId;
    public int conId;
    public String exchange;

    public RerouteMktDepthReqData(int reqId, int conId, String exchange) {
        this.reqId = reqId;
        this.conId = conId;
        this.exchange = exchange;
    }
}
