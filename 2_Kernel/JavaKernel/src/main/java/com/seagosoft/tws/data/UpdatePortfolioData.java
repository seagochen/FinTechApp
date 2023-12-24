package com.seagosoft.tws.data;

import com.ib.client.Contract;
import com.ib.client.Decimal;

public class UpdatePortfolioData {

    public Contract contract;
    public Decimal position;
    public double marketPrice;
    public double marketValue;
    public double averageCost;
    public double unrealizedPNL;
    public double realizedPNL;
    public String accountName;

    public UpdatePortfolioData(Contract contract,
                               Decimal position,
                               double marketPrice,
                               double marketValue,
                               double averageCost,
                               double unrealizedPNL,
                               double realizedPNL,
                               String accountName) {
        this.contract = contract;
        this.position = position;
        this.marketPrice = marketPrice;
        this.marketValue = marketValue;
        this.averageCost = averageCost;
        this.unrealizedPNL = unrealizedPNL;
        this.realizedPNL = realizedPNL;
        this.accountName = accountName;
    }
}
