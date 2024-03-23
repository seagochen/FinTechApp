package com.seagosoft.tws.zeromq

import org.zeromq.SocketType
import org.zeromq.ZContext
import org.zeromq.ZMQ.Socket

class ZeroMQServer(port: Int) : AutoCloseable {
    private var socket: Socket? = null
    private var context: ZContext? = null
    private var port: Int? = null

    fun start() {
        ZContext().use { context ->

            // Set up the context
            this.context = context

            // Set up the socket
            socket = context.createSocket(SocketType.REP)

            // Bind the socket to the port
            socket!!.bind("tcp://*:$port")
        }
    }

    // Send a message
    fun send(topic: String, message: String) {
        // Send the message
        socket!!.sendMore(topic)
        socket!!.send(message)
    }

    override fun close() {
        // Close the socket
        socket!!.close()

        // Close the context
        context!!.close()
    }
}