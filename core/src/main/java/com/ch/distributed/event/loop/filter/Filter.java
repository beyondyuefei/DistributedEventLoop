package com.ch.distributed.event.loop.filter;

import com.ch.distributed.event.loop.client.ResourceHandler;
import com.ch.distributed.event.loop.client.ResourceHandlerRequest;
import com.ch.distributed.event.loop.client.ResourceHandlerResponse;

import java.util.concurrent.CompletableFuture;

public interface Filter {
    <T,R> CompletableFuture<ResourceHandlerResponse<R>> filter(final ResourceHandler resourceHandler, final ResourceHandlerRequest<T> resourceHandlerRequest, final Class<R> rClass);
}
