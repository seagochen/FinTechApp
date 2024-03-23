package com.seagosoft.tws.wrapper.structs

import com.ib.client.ContractDetails


class ScannerData(
    var reqId: Int, var rank: Int, var contractDetails: ContractDetails, var distance: String,
    var benchmark: String, var projection: String, var legsStr: String
)