package com.seagosoft.tws.zeromq

import org.zeromq.SocketType
import org.zeromq.ZContext
import org.zeromq.ZMQ.Socket

class ZeroMQClient (server: String, port: Int) {
    private var socket: Socket? = null
    private var context: ZContext? = null
    private var port: Int? = null
    private var ip: String? = null


    fun connect(topic: String) {

        // Set up the socket
        context = ZContext()
        socket = context!!.createSocket(SocketType.REQ)
        socket!!.connect("tcp://$ip:$port")

        // Subscribe to the topic
        socket!!.subscribe(topic)
    }

    // Receive a message
    fun receive(): String {
        // Receive the message
        val message = socket!!.recvStr()

        // Return the message
        return message
    }
}