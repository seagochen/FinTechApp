package com.seagosoft.tws.data;

import com.ib.client.PriceIncrement;

public class MarketRuleData {

    public int marketRuleId;
    public PriceIncrement[] priceIncrements;

    public MarketRuleData(int marketRuleId, PriceIncrement[] priceIncrements) {
        this.marketRuleId = marketRuleId;
        this.priceIncrements = priceIncrements;
    }
}
