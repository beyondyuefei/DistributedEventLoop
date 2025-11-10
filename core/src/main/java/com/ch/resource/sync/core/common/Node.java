package com.ch.resource.sync.core.common;

public class Node {
    private final String ip;
    private final int port;
    private volatile boolean isHealth;

    public Node(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.isHealth = true;
    }

    public String getNodeUniqueKey() {
        return ip + ":" + port;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public boolean isHealth() {
        return isHealth;
    }

    public void setHealth(boolean health) {
        isHealth = health;
    }
}
