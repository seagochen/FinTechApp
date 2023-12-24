package com.seagosoft.tws.data;

import com.ib.client.Contract;
import com.ib.client.Decimal;

public class PositionData {

    public String account;
    public Contract contract;
    public Decimal pos;
    public double avgCost;

    public PositionData(String account, Contract contract, Decimal pos, double avgCost) {
        this.account = account;
        this.contract = contract;
        this.pos = pos;
        this.avgCost = avgCost;
    }
}
