package com.seagosoft.tws.data;

import com.ib.client.SoftDollarTier;

public class SoftDollarTiersData {

    public int reqId;
    public SoftDollarTier[] softDollarTiers;

    public SoftDollarTiersData(int reqId, SoftDollarTier[] softDollarTiers) {
        this.reqId = reqId;
        this.softDollarTiers = softDollarTiers;
    }
}
