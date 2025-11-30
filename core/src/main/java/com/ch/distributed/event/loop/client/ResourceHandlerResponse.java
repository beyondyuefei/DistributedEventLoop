package com.ch.distributed.event.loop.client;

public interface ResourceHandlerResponse {
    boolean isSuccess();

    String data();

    Exception exception();
}
