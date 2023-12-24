package com.seagosoft.tws.data;

import com.ib.client.Contract;
import com.ib.client.Decimal;

public class PositionMultiData {

    public int reqId;
    public String account;
    public String modelCode;
    public Contract contract;
    public Decimal pos;
    public double avgCost;


    public PositionMultiData(int reqId, String account, String modelCode,
                             Contract contract, Decimal pos, double avgCost) {
        this.reqId = reqId;
        this.account = account;
        this.modelCode = modelCode;
        this.contract = contract;
        this.pos = pos;
        this.avgCost = avgCost;
    }
}
