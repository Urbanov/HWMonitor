package edu.pw.hwmonitor.network.udp;

import edu.pw.hwmonitor.network.Manager;
import edu.pw.hwmonitor.network.NetworkProperties;
import edu.pw.hwmonitor.network.messages.Message;
import edu.pw.hwmonitor.network.messages.received.DataMessage;
import edu.pw.hwmonitor.network.messages.received.ReceivedMessageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

@Component
public class UdpServer extends Thread {
    private final static int PACKET_SIZE = 20;

    private DatagramSocket socket;
    private Manager manager;
    private DatagramPacket packet;

    @Autowired
    public UdpServer(Manager manager, NetworkProperties properties) throws SocketException {
        this.socket = new DatagramSocket(properties.getDataPort());
        this.manager = manager;
        this.packet = new DatagramPacket(new byte[PACKET_SIZE], PACKET_SIZE);
    }

    @Override
    public void run() {
        while(!socket.isClosed()) {
            try {
                socket.receive(packet);
                readData();
            } catch (IOException e) {
                // error while receiving message, do nothing
            }
        }
    }

    private void readData() throws IOException {
        byte[] buffer = packet.getData();
        Message message = ReceivedMessageFactory.build(buffer);
        manager.onDataReceived((DataMessage) message);
    }
}
