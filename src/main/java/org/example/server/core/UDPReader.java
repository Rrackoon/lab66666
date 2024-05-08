package org.example.server.core;

import org.example.command.Command;
import org.example.command.CommandShallow;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.server.Main;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class UDPReader {
    private static final Logger logger = LogManager.getLogger(UDPReader.class);

    public static String in_string;
    ByteBuffer in_buffer;
    DatagramChannel channel;
    SocketAddress client;
    Selector selector;
    CommandShallow shallow ;
    //Scanner scanner;

    public UDPReader(DatagramChannel channel, Selector selector) throws Exception {

        in_string= "";
        in_buffer = ByteBuffer.allocateDirect(1024);
        this.channel=channel;
        this.selector=selector;
        //this.scanner = new Scanner(System.in);

    }

    public CommandShallow getShallow() {
        return shallow;
    }

    public SocketAddress getClient()
    {return this.client;}
    public void execute() throws IOException {
        //selector = Selector.open();
        channel.register(selector, SelectionKey.OP_READ);
        if (true) {

            try {

                if (selector.select() > 0) {

                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {

                        SelectionKey key = iterator.next();

                        if (key.isReadable()) {
                            client = receive();
                            iterator.remove();
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("Error executing UDP Reader: {}", e.getMessage(), e);

            }
        }
    }

    public SocketAddress  receive() {
        //SocketAddress client;
        try {
            //channel = (DatagramChannel) key.channel();
            in_buffer = ByteBuffer.allocate(65507);
            client = channel.receive(in_buffer);
            //client = channel.getRemoteAddress();

            //int numRead = -1;
            //numRead = channel.read(in_buffer);
            byte[] data = new byte[in_buffer.position()];
            System.arraycopy(in_buffer.array(), 0, data, 0, in_buffer.position());
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            ObjectInput in = new ObjectInputStream(bis);
            shallow = (CommandShallow)in.readObject();
            System.out.println("Получена команда:"+ shallow.getCommand());
            logger.info("Command received: "+shallow.getCommand());
            //logger.info("Command received: {} {}", shallow.getCommand().getName());
            in_buffer.clear();


        } catch (Exception e){
            logger.error("Error receiving UDP data: {}"+ e.getMessage());
            //logger.error("Error receiving UDP data: {}", e.getMessage(), e);
        }
        return client;


    }

}