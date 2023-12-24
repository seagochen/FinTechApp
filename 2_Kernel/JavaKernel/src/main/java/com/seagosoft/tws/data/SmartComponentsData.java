package com.seagosoft.tws.data;

import java.util.Map;

public class SmartComponentsData {

    public int reqId;
    public Map<Integer, Map.Entry<String, Character>> theMap;

    public SmartComponentsData(int reqId, Map<Integer, Map.Entry<String, Character>> theMap) {
        this.reqId = reqId;
        this.theMap = theMap;
    }
}
