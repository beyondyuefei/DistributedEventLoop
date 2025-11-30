package com.ch.distributed.event.loop.client;

import java.util.concurrent.CompletableFuture;

public interface ResourceHandler {
    CompletableFuture<ResourceHandlerResponse> handle(final ResourceHandlerRequest resourceHandlerRequest);
}
