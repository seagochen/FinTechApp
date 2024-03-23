package com.seagosoft.tws.wrapper.structs

import com.ib.client.HistoricalTickBidAsk


class HistoricalTicksBidAskData(var reqId: Int, var ticks: List<HistoricalTickBidAsk>, var done: Boolean)