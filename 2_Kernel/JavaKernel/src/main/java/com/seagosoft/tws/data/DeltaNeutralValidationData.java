package com.seagosoft.tws.data;

import com.ib.client.DeltaNeutralContract;

public class DeltaNeutralValidationData {

    public int reqId;
    public DeltaNeutralContract underComp;


    public DeltaNeutralValidationData(int reqId, DeltaNeutralContract underComp) {
        this.reqId = reqId;
        this.underComp = underComp;
    }
}
