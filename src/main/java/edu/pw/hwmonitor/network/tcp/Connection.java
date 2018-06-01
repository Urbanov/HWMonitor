package edu.pw.hwmonitor.network.tcp;

import edu.pw.hwmonitor.network.messages.Message;
import edu.pw.hwmonitor.network.Manager;
import edu.pw.hwmonitor.network.messages.received.ReceivedMessageFactory;
import edu.pw.hwmonitor.network.messages.sent.SerializableMessage;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.atomic.AtomicInteger;

public class Connection extends Thread {
    private final static int MAX_BUFFER_SIZE = 36;
    private static final AtomicInteger counter = new AtomicInteger(0);

    private int sessionId;
    private int databseId;
    private Socket socket;
    private byte[] buffer;
    private Manager manager;
    private boolean authenticated;
    private String assignedChallenge;

    public Connection(Socket socket, Manager manager) {
        this.sessionId = counter.getAndIncrement();
        this.socket = socket;
        this.buffer = new byte[MAX_BUFFER_SIZE];
        this.manager = manager;
        this.authenticated = false;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setDatabseId(int databseId) {
        this.databseId = databseId;
    }

    public int getDatabseId() {
        return databseId;
    }

    public void authenticate() {
        authenticated = true;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAssignedChallenge(String assignedChallenge) {
        this.assignedChallenge = assignedChallenge;
    }

    public String getAssignedChallenge() {
        return assignedChallenge;
    }

    @Override
    public void run() {
        while (socket.isConnected() && !socket.isClosed()) {
            try {
                readMessage();
            } catch (IOException e) {
                break;
            }
        }
        manager.onDisconnected(this);
    }

    public void sendMessage(Message message) {
        try {
            socket.getOutputStream().write(((SerializableMessage) message).serialize());
        } catch (IOException e) {
            manager.onDisconnected(this);
        }
    }

    private void readMessage() throws IOException {
        int length = socket.getInputStream().read(buffer);
        if (length < 0) {
            throw new SocketException();
        }
        Message message = ReceivedMessageFactory.build(buffer);
        manager.onMessageReceived(this, message);
    }

    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            // socket seems to be broken so the thread is going to stop anyway
        }
    }
}
