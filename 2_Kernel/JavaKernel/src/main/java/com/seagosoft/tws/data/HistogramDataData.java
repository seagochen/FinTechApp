package com.seagosoft.tws.data;

import com.ib.client.HistogramEntry;

import java.util.List;

public class HistogramDataData {

    public int requestId;
    public List<HistogramEntry> list;

    public HistogramDataData(int requestId, List<HistogramEntry> list) {
        this.requestId = requestId;
        this.list = list;
    }
}
