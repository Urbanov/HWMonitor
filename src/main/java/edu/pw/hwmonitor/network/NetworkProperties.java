package edu.pw.hwmonitor.network;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("network.sockets")
@Component
public class NetworkProperties {
    private int authPort;
    private int dataPort;

    public int getAuthPort() {
        return authPort;
    }

    public int getDataPort() {
        return dataPort;
    }

    public void setAuthPort(int authPort) {
        this.authPort = authPort;
    }

    public void setDataPort(int dataPort) {
        this.dataPort = dataPort;
    }
}
