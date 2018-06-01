package edu.pw.hwmonitor.network.tcp;

import edu.pw.hwmonitor.network.Manager;
import edu.pw.hwmonitor.network.NetworkProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class TcpServer extends Thread {
    private ServerSocket serverSocket;
    private Manager manager;

    @Autowired
    public TcpServer(Manager manager, NetworkProperties properties) throws IOException {
        this.serverSocket = new ServerSocket(properties.getAuthPort());
        this.manager = manager;
    }

    // TODO close

    @Override
    public void run() {
        while (!serverSocket.isClosed()) {
            try {
                acceptConnection(serverSocket.accept());
            } catch (IOException e) {
                // connection failed, do nothing
            }
        }
    }

    private void acceptConnection(Socket socket) {
        Connection connection = new Connection(socket, manager);
        connection.start();
        manager.onConnected(connection);
    }
}
