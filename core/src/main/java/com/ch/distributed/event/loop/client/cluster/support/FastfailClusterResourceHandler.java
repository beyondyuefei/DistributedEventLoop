package com.ch.distributed.event.loop.client.cluster.support;

import com.ch.distributed.event.loop.client.ResourceHandlerRequest;
import com.ch.distributed.event.loop.client.ResourceHandlerResponse;

import java.util.concurrent.CompletableFuture;

public class FastfailClusterResourceHandler extends AbstractClusterResourceHandler{
    @Override
    public <R> ResourceHandlerResponse<CompletableFuture<R>> handle(ResourceHandlerRequest resourceHandlerRequest) {
        final ResourceHandlerResponse<CompletableFuture<ResourceHandlerResponse>> response = doSelect(resourceHandlerRequest)
                .handle(resourceHandlerRequest);
        return null;
    }
}
