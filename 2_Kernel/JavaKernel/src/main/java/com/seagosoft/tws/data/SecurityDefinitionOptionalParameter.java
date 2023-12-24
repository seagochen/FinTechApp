package com.seagosoft.tws.data;

import java.util.Set;

public class SecurityDefinitionOptionalParameter {

    public int reqId;
    public String exchange;
    public int underlyingConId;
    public String tradingClass;
    public String multiplier;
    public Set expirations;
    public Set strikes;


    public SecurityDefinitionOptionalParameter(int reqId,
                                               String exchange,
                                               int underlyingConId,
                                               String tradingClass,
                                               String multiplier,
                                               Set expirations,
                                               Set strikes) {
        this.reqId = reqId;
        this.exchange = exchange;
        this.underlyingConId = underlyingConId;
        this.tradingClass = tradingClass;
        this.multiplier = multiplier;
        this.expirations = expirations;
        this.strikes = strikes;
    }
}
