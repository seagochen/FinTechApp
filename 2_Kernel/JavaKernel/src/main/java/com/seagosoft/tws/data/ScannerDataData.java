package com.seagosoft.tws.data;

import com.ib.client.ContractDetails;

public class ScannerDataData {
    public int reqId;
    public int rank;
    public ContractDetails contractDetails;
    public String distance;
    public String benchmark;
    public String projection;
    public String legsStr;


    public ScannerDataData(int reqId, int rank, ContractDetails contractDetails, String distance,
                           String benchmark, String projection, String legsStr) {
        this.reqId = reqId;
        this.rank = rank;
        this.contractDetails = contractDetails;
        this.distance = distance;
        this.benchmark = benchmark;
        this.projection = projection;
        this.legsStr = legsStr;
    }
}
