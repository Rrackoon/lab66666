package org.example.server.core;
import org.example.command.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UDPSender {

    private static final Logger logger = LogManager.getLogger(UDPSender.class);
    private final DatagramChannel datagramChannel;
    private final Selector selector;

    public UDPSender(DatagramChannel datagramChannel,Selector selector) throws IOException {
        this.datagramChannel = datagramChannel;
        this.datagramChannel.configureBlocking(false);

        // Открываем селектор и регистрируем канал на запись
        this.selector = selector;
        this.datagramChannel.register(selector, SelectionKey.OP_WRITE);
    }

    public void send(Response[] response, SocketAddress address, DatagramChannel channel, Logger logger) {

        for (int i = 0; i < response.length; i++) {
            try {


                for (int j = 0; j < response[i].getMessage().length; j++) {
                    logger.debug("response: {}", response[i].getMessage()[i]);
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();//для записи объекта в массив байтов
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(response);
                byte[] arr = baos.toByteArray();
                ByteBuffer buffer = ByteBuffer.wrap(arr);//обертка массива байтов

                // Ожидаем готовности канала на запись
                selector.select();//блокирует текущий поток до тех пор, пока не будет готов как минимум один канал из зарегистрированных в селекторе. В данном случае мы ждем готовности канала на запись.
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    if (key.isWritable()) {
                        //DatagramChannel
                        channel = (DatagramChannel) key.channel();
                        channel.send(buffer, address);
                        logger.debug("Sent " + arr.length + " bytes");
                    }
                    keyIterator.remove();
                }
            } catch (IOException e) {
                logger.error("Error sending UDP data: " + e.getMessage(), e);
            }
        }
    }
}
