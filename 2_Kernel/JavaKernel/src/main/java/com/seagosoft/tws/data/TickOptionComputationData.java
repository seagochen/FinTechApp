package com.seagosoft.tws.data;

public class TickOptionComputationData {

    public int tickerId;
    public int field;
    public int tickAttrib;
    public double impliedVol;
    public double delta;
    public double optPrice;
    public double pvDividend;
    public double gamma;
    public double vega;
    public double theta;
    public double undPrice;

    public TickOptionComputationData(int tickerId, int field, int tickAttrib, double impliedVol, double delta, double optPrice,
                                     double pvDividend, double gamma, double vega, double theta, double undPrice) {
        this.tickerId = tickerId;
        this.field = field;
        this.tickAttrib = tickAttrib;
        this.impliedVol = impliedVol;
        this.delta = delta;
        this.optPrice = optPrice;
        this.pvDividend = pvDividend;
        this.gamma = gamma;
        this.vega = vega;
        this.theta = theta;
        this.undPrice = undPrice;
    }

}
