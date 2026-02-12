package com.ch.distributed.event.loop.filter.impl;

import com.ch.distributed.event.loop.client.ResourceHandler;
import com.ch.distributed.event.loop.client.ResourceHandlerRequest;
import com.ch.distributed.event.loop.client.ResourceHandlerResponse;
import com.ch.distributed.event.loop.filter.Filter;

import java.util.concurrent.CompletableFuture;

public class MockFilter implements Filter {
    @Override
    public <T, R> CompletableFuture<ResourceHandlerResponse<R>> filter(ResourceHandler resourceHandler, ResourceHandlerRequest<T> resourceHandlerRequest, Class<R> rClass) {
        System.out.println("MockFilter...before");
        final CompletableFuture<ResourceHandlerResponse<R>> result = resourceHandler.handle(resourceHandlerRequest, rClass);
        System.out.println("MockFilter...after");
        return result;
    }
}
