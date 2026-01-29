package com.ch.distributed.event.loop.client;

import java.util.concurrent.CompletableFuture;

public interface ResourceHandler {
    <T,R> CompletableFuture<ResourceHandlerResponse<R>> handle(final ResourceHandlerRequest<T> resourceHandlerRequest, final Class<R> rClass);
}
