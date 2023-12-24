package com.seagosoft.tws.data;

public class TickEFPData {
    public int tickerId;
    public int tickType;
    public double basisPoints;
    public String formattedBasisPoints;
    public double impliedFuture;
    public int holdDays;
    public String futureExpiry;
    public double dividendImpact;
    public double dividendsToExpiry;

    public TickEFPData(int tickerId, int tickType, double basisPoints, String formattedBasisPoints, double impliedFuture, int holdDays,
                       String futureExpiry, double dividendImpact, double dividendsToExpiry) {
        this.tickerId = tickerId;
        this.tickType = tickType;
        this.basisPoints = basisPoints;
        this.formattedBasisPoints = formattedBasisPoints;
        this.impliedFuture = impliedFuture;
        this.holdDays = holdDays;
        this.futureExpiry = futureExpiry;
        this.dividendImpact = dividendImpact;
        this.dividendsToExpiry = dividendsToExpiry;
    }
}
