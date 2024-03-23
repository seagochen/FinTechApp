package com.seagosoft.tws.wrapper.structs

import com.ib.client.CommissionReport


class CommissionReportData(var commissionReport: CommissionReport) {
    override fun toString(): String {
        return "CommissionReportData(commissionReport=$commissionReport)"
    }
}