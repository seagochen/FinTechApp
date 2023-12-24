package com.seagosoft.tws.data;

public class HistoricalNewsData {

    public int requestId;
    public String time;
    public String providerCode;
    public String articleId;
    public String headline;

    public HistoricalNewsData(int requestId, String time, String providerCode,
                              String articleId, String headline) {
        this.requestId = requestId;
        this.time = time;
        this.providerCode = providerCode;
        this.articleId = articleId;
        this.headline = headline;
    }
}
