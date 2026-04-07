package com.ch.distributed.event.loop.client.loadbalance.sticky;

import com.ch.distributed.event.loop.client.ResourceHandlerRequest;
import com.ch.distributed.event.loop.client.loadbalance.AbstractLoadBalance;
import com.ch.distributed.event.loop.client.remote.RemoteServiceFactory;
import com.ch.distributed.event.loop.common.Node;

import java.util.List;

public class RedisStickLoadBalance extends AbstractLoadBalance {

    public RedisStickLoadBalance() {
        super();
    }

    public RedisStickLoadBalance(RemoteServiceFactory remoteServiceFactory) {
        super(remoteServiceFactory);
    }

    @Override
    protected <T> Node find(ResourceHandlerRequest<T> resourceHandlerRequest, final List<Node> nodes) {
        // todo redis
        return null;
    }
}
