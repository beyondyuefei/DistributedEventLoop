package com.ch.distributed.event.loop.client.loadbalance;

import com.ch.distributed.event.loop.client.ResourceHandler;
import com.ch.distributed.event.loop.client.ResourceHandlerRequest;
import com.ch.distributed.event.loop.client.remote.HttpRemoteService;
import com.ch.distributed.event.loop.common.Node;

import java.util.List;

public abstract class AbstractLoadBalance implements LoadBalance {

    @Override
    public <T> ResourceHandler select(final ResourceHandlerRequest<T> resourceHandlerRequest, final List<Node> nodes) {
        if (nodes == null || nodes.isEmpty()) {
            return null;
        }

        if (nodes.size() == 1) {
            return new HttpRemoteService(nodes.getFirst());
        }

        return new HttpRemoteService(find(resourceHandlerRequest, nodes));
    }

    protected abstract <T> Node find(final ResourceHandlerRequest<T> resourceHandlerRequest, final List<Node> nodes);
}
