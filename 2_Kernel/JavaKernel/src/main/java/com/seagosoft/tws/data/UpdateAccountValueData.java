package com.seagosoft.tws.data;

public class UpdateAccountValueData {

    public String key;
    public String value;
    public String currency;
    public String accountName;

    public UpdateAccountValueData(String key, String value, String currency, String accountName) {
        this.key = key;
        this.value = value;
        this.currency = currency;
        this.accountName = accountName;
    }
}
