package com.seagosoft.tws.data;

import com.ib.client.DepthMktDataDescription;

public class MktDepthExchangesData {

    public DepthMktDataDescription[] depthMktDataDescriptions;

    public MktDepthExchangesData(DepthMktDataDescription[] depthMktDataDescriptions) {
        this.depthMktDataDescriptions = depthMktDataDescriptions;
    }
}
