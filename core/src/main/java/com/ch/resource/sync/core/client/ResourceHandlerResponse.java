package com.ch.resource.sync.core.client;

public interface ResourceHandlerResponse<R> {
    boolean isSuccess();

    R data();

    Exception exception();
}
