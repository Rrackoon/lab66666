package org.example.common;

public interface ServerInt extends AutoCloseable {
    void start(int port,int threads);
    @Override
    void close();
}
