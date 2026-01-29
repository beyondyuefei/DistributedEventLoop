package com.ch.distributed.event.loop.client.loadbalance;

import com.ch.distributed.event.loop.client.ResourceHandler;
import com.ch.distributed.event.loop.client.ResourceHandlerRequest;
import com.ch.distributed.event.loop.common.Node;

import java.util.List;

public interface LoadBalance {
    <T> ResourceHandler select(final ResourceHandlerRequest<T> resourceHandlerRequest, final List<Node> nodes);
}
