package com.seagosoft.tws.data;

import com.ib.client.Contract;
import com.ib.client.Execution;

public class ExecDetailsData {

    public int reqId;
    public Contract contract;
    public Execution execution;

    public ExecDetailsData(int reqId, Contract contract, Execution execution) {
        this.reqId = reqId;
        this.contract = contract;
        this.execution = execution;
    }
}
