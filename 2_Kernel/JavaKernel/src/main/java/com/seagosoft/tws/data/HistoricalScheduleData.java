package com.seagosoft.tws.data;

import java.util.List;

public class HistoricalScheduleData {

    public int reqId;
    public String startDateTime;
    public String endDateTime;
    public String timeZone;
    public List sessions;

    public HistoricalScheduleData(int reqId, String startDateTime, String endDateTime, String timeZone, List sessions) {
        this.reqId = reqId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.timeZone = timeZone;
        this.sessions = sessions;
    }
}
