package com.seagosoft.tws.wrapper.structs

class AccountSummaryData(var reqId: Int, var account: String, var tag: String, var value: String, var currency: String) {
    override fun toString(): String {
        return "AccountSummaryData(reqId=$reqId, account='$account', tag='$tag', value='$value', currency='$currency')"
    }
}