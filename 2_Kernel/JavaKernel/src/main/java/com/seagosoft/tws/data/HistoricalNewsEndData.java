package com.seagosoft.tws.data;

public class HistoricalNewsEndData {

    public int requestId;
    public boolean hasMore;

    public HistoricalNewsEndData(int requestId, boolean hasMore) {
        this.requestId = requestId;
        this.hasMore = hasMore;
    }
}
