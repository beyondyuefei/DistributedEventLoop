package com.ch.distributed.event.loop.client.remote;

import com.ch.distributed.event.loop.common.Node;

public class HttpRemoteServiceFactory implements RemoteServiceFactory {

    @Override
    public RemoteService create(Node node) {
        return new HttpRemoteService(node);
    }
}
