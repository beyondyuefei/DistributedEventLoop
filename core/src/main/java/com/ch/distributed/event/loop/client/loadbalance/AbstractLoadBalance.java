package com.ch.distributed.event.loop.client.loadbalance;

import com.ch.distributed.event.loop.client.ResourceHandler;
import com.ch.distributed.event.loop.client.ResourceHandlerRequest;
import com.ch.distributed.event.loop.client.remote.RemoteServiceFactory;
import com.ch.distributed.event.loop.common.Node;

import java.util.List;
import java.util.ServiceLoader;

public abstract class AbstractLoadBalance implements LoadBalance {

    private final RemoteServiceFactory remoteServiceFactory;

    protected AbstractLoadBalance() {
        this.remoteServiceFactory = ServiceLoader.load(RemoteServiceFactory.class)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No RemoteServiceFactory found via SPI"));
    }

    protected AbstractLoadBalance(RemoteServiceFactory remoteServiceFactory) {
        this.remoteServiceFactory = remoteServiceFactory;
    }

    @Override
    public <T> ResourceHandler select(final ResourceHandlerRequest<T> resourceHandlerRequest, final List<Node> nodes) {
        if (nodes == null || nodes.isEmpty()) {
            return null;
        }

        if (nodes.size() == 1) {
            return remoteServiceFactory.create(nodes.getFirst());
        }

        return remoteServiceFactory.create(find(resourceHandlerRequest, nodes));
    }

    protected abstract <T> Node find(final ResourceHandlerRequest<T> resourceHandlerRequest, final List<Node> nodes);
}
