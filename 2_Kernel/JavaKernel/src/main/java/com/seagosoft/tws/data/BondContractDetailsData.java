package com.seagosoft.tws.data;

import com.ib.client.ContractDetails;

public class BondContractDetailsData {

    public int reqId;
    public ContractDetails contractDetails;

    public BondContractDetailsData(int reqId, ContractDetails contractDetails) {
        this.reqId = reqId;
        this.contractDetails = contractDetails;
    }
}
