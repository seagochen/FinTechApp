package com.seagosoft.tws.wrapper.structs

import com.ib.client.ContractDetails

class BondContractDetailsData(var reqId: Int, var contractDetails: ContractDetails) {
    override fun toString(): String {
        return "BondContractDetailsData(reqId=$reqId, contractDetails=$contractDetails)"
    }
}