package com.seagosoft.tws.data;

import com.ib.client.ContractDescription;

public class SymbolSamplesData {

    public int reqId;
    public ContractDescription[] contractDescriptions;

    public SymbolSamplesData(int reqId, ContractDescription[] contractDescriptions) {
        this.reqId = reqId;
        this.contractDescriptions = contractDescriptions;
    }
}
