package com.viepovsky;

enum Port {
    PORT8090(8090);

    private final int port;

    Port(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }
}
