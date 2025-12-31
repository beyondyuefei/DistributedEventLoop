package com.ch.distributed.event.loop.client.remote;

import com.ch.distributed.event.loop.common.Node;

public abstract class AbstractRemoteService<T,R> implements RemoteService<T, R> {
    protected final Node node;

    public AbstractRemoteService(Node node) {
        this.node = node;
    }
}
