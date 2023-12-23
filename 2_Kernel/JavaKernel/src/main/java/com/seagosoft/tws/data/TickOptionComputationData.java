package com.seagosoft.tws.data;

public class TickOptionComputationData {

    private int tickerId;
    private int field;
    private int tickAttrib;
    private double impliedVol;
    private double delta;
    private double optPrice;
    private double pvDividend;
    private double gamma;
    private double vega;
    private double theta;
    private double undPrice;

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
