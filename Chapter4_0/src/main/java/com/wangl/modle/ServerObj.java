package com.wangl.modle;

/**
 * Created by seentech on 2017/2/22.
 */
public class ServerObj {

    private String protocol;

    private String host;

    private int port;

    public ServerObj(String protocol, String host, int port) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
    }

    @Override
    public String toString() {
        return "ServerObj{" +
                "protocol='" + protocol + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
