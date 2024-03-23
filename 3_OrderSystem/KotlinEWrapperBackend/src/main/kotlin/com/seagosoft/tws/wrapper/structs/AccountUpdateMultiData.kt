package com.seagosoft.tws.wrapper.structs

class AccountUpdateMultiData(var reqId: Int, var account: String, var modelCode: String,
                             var key: String, var value: String, var currency: String) {
    override fun toString(): String {
        return "AccountUpdateMultiData(reqId=$reqId, account='$account', modelCode='$modelCode', key='$key', value='$value', currency='$currency')"
    }
}