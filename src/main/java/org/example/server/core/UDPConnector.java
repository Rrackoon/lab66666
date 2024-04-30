package org.example.server.core;
import java.io.*;
import java.net.*;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class UDPConnector {
    private static final Logger logger = LogManager.getLogger(UDPConnector.class);
    DatagramChannel channel;
    DatagramSocket socket;
    Selector selector;
    //SocketAddress client;
    public UDPConnector() {

        try {
            channel = DatagramChannel.open();
            selector = Selector.open();
            socket = channel.socket();
            SocketAddress address = new InetSocketAddress(9999);
            socket.bind(address);
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
            logger.info("UDP Connector initialized");
        } catch (Exception e) {
            logger.error("Error initializing UDP Connector: {}", e.getMessage(), e);
        }
    }

    public DatagramChannel getChannel()
    {
        return this.channel;
    }
    public DatagramSocket getSocket()
    {
        return this.socket;
    }
    public Selector getSelector()
    {
        return this.selector;
    }
}