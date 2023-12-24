package com.seagosoft.tws.data;

public class TickNewsData {

    public int tickerId;
    public long timeStamp;
    public String providerCode;
    public String articleId;
    public String headline;
    public String extraData;

    public TickNewsData(int tickerId, long timeStamp, String providerCode,
                        String articleId, String headline, String extraData) {
        this.tickerId = tickerId;
        this.timeStamp = timeStamp;
        this.providerCode = providerCode;
        this.articleId = articleId;
        this.headline = headline;
        this.extraData = extraData;
    }
}
