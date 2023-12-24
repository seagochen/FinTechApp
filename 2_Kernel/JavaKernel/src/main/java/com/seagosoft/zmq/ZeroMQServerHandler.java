package com.seagosoft.zmq;

import org.zeromq.SocketType;
import org.zeromq.ZMQ.Socket;
import org.zeromq.ZContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ZeroMQServerHandler implements AutoCloseable {

    private static final Logger logger = LogManager.getLogger(ZeroMQServerHandler.class);

    private int port;
    private String address;
    private ZContext context;
    private Socket socket;

    public ZeroMQServerHandler(String address, int port) {
        if (address == null || address.isEmpty()) {
            throw new IllegalArgumentException("Address cannot be null or empty");
        }
        if (port <= 0) {
            throw new IllegalArgumentException("Port must be positive");
        }

        this.port = port;
        this.address = address;

        try {
            // Set up the ZeroMQ
            this.context = new ZContext(1);

            // Socket to talk to clients
            this.socket = context.createSocket(SocketType.PUB);

            // Bind to the port
            this.socket.bind("tcp://" + this.address + ":" + this.port);
        } catch (Exception e) {
            close(); // Clean up resources in case of an exception
            throw e; // Re-throw the exception to handle it further up the call stack
        }
    }

    @Override
    public void close() {
        try {
            context.close();
        } catch (Exception e) {
            logger.error("Error closing ZeroMQ context", e);
        }
    }

    public void send(String topic, String message) {
        try {
            this.socket.sendMore(topic);
            this.socket.send(message);
        } catch (Exception e) {
            logger.error("Error sending message", e);
        }
    }

    // Additional methods and configurations
    public void setPort(int port) {
        this.port = port;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Reset the ZeroMQ socket
    public void reset() {
        // Close ZeroMQ
        close();

        // Rebind the socket
        bind();
    }

    public void bind() {
        try {
            // Set up the ZeroMQ
            this.context = new ZContext(1);

            // Socket to talk to clients
            this.socket = context.createSocket(SocketType.PUB);

            // Bind to the port
            this.socket.bind("tcp://" + this.address + ":" + this.port);
        } catch (Exception e) {
            // Log the error
            logger.error("Error binding ZeroMQ socket", e);
            
            // Clean up resources in case of an exception
            close();

            // Re-throw the exception to handle it further up the call stack
            throw e; 
        }
    }
}
