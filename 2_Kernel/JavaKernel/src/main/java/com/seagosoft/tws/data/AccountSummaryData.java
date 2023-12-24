package com.seagosoft.tws.data;

public class AccountSummaryData {

    public int reqId;
    public String account;
    public String tag;
    public String value;
    public String currency;

    public AccountSummaryData(int reqId, String account, String tag, String value, String currency) {
        this.reqId = reqId;
        this.account = account;
        this.tag = tag;
        this.value = value;
        this.currency = currency;
    }
}
