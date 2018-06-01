package edu.pw.hwmonitor.network;

import edu.pw.hwmonitor.network.tcp.TcpServer;
import edu.pw.hwmonitor.network.udp.UdpServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Network implements CommandLineRunner {

    private TcpServer tcpServer;
    private UdpServer udpServer;

    @Autowired
    public Network(TcpServer tcpServer, UdpServer udpServer) {
        this.tcpServer = tcpServer;
        this.udpServer = udpServer;
    }

    @Override
    public void run(String... args) {
        tcpServer.start();
        udpServer.start();
    }
}
