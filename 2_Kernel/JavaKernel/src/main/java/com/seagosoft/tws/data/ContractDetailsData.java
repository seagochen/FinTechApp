package com.seagosoft.tws.data;

import com.ib.client.ContractDetails;

public class ContractDetailsData {

    public int reqId;
    public ContractDetails contractDetails;


    public ContractDetailsData(int reqId, ContractDetails contractDetails) {
        this.reqId = reqId;
        this.contractDetails = contractDetails;
    }
}
