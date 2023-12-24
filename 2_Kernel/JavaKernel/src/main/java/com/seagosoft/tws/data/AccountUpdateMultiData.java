package com.seagosoft.tws.data;

public class AccountUpdateMultiData {

    public int reqId;
    public String account;
    public String modelCode;
    public String key;
    public String value;
    public String currency;

    public AccountUpdateMultiData(int reqId, String account, String modelCode,
                                  String key, String value, String currency) {
        this.reqId = reqId;
        this.account = account;
        this.modelCode = modelCode;
        this.key = key;
        this.value = value;
        this.currency = currency;
    }
}
