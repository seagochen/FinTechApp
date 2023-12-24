package com.seagosoft.tws.data;

public class UpdateNewsBulletinData {

    public int msgId;
    public int msgType;
    public String message;
    public String origExchange;

    public UpdateNewsBulletinData(int msgId, int msgType, String message, String origExchange) {
        this.msgId = msgId;
        this.msgType = msgType;
        this.message = message;
        this.origExchange = origExchange;
    }
}
