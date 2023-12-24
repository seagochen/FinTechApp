package com.seagosoft.tws.data;

public class HistoricalDataEndData {

    public int reqId;
    public String startDateStr;
    public String endDateStr;

    public HistoricalDataEndData(int reqId, String startDateStr, String endDateStr) {
        this.reqId = reqId;
        this.startDateStr = startDateStr;
        this.endDateStr = endDateStr;
    }
}
