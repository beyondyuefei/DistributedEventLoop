package com.ch.distributed.event.loop.filter.impl;

import com.ch.distributed.event.loop.client.ResourceHandler;
import com.ch.distributed.event.loop.client.ResourceHandlerRequest;
import com.ch.distributed.event.loop.client.ResourceHandlerResponse;
import com.ch.distributed.event.loop.filter.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class LogFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogFilter.class);

    @Override
    public <T, R> CompletableFuture<ResourceHandlerResponse<R>> filter(ResourceHandler resourceHandler, ResourceHandlerRequest<T> resourceHandlerRequest, Class<R> rClass) {
        LOGGER.info("LogFilter, request handlerName:{}", resourceHandlerRequest.resourceHandlerName());
        final CompletableFuture<ResourceHandlerResponse<R>> result = resourceHandler.handle(resourceHandlerRequest, rClass);
        LOGGER.info("LogFilter, response handlerName:{}, result:{}", resourceHandlerRequest.resourceHandlerName(), result.join().data());
        return result;
    }
}
