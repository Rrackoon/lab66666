package org.example.client.core;


import java.nio.*;
import java.io.*;
import java.net.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UDPConnector {

    private static final Logger logger = LogManager.getLogger(UDPConnector.class);
    private DatagramSocket datagramSocket;
    private SocketAddress serverAddress;
    private int serverPort;
    private String host;

    public UDPConnector( String host, int port ) {
        this.datagramSocket = null;
        this.serverAddress = null;
        this.serverPort = port;
        this.host = host;
    }

    public  boolean Connect(String host,int serverPort) {
        try {
            this.serverAddress = new InetSocketAddress(host, serverPort);
            this.serverPort= serverPort;
            this.datagramSocket = new DatagramSocket();
            this.datagramSocket.connect(serverAddress);
            logger.info("Connected to " + host + " on port " + serverPort);

        }
        catch (IOException e) {
            logger.error("Could not connect to " + host + " on port " + serverPort);
            return false;
        }
        return true;
    }

    public int getServerPort() {
        return serverPort;
    }

    public DatagramSocket getDatagramSocket() {
        return datagramSocket;
    }

    public SocketAddress getServerAddress() {
        return serverAddress;
    }

}
