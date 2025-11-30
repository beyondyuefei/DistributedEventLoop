package com.ch.distributed.event.loop.client;

public interface ResourceHandlerResponse {
    boolean isSuccess();

    // todo: 支持泛型
    String data();

    Exception exception();
}
