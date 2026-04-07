package com.ch.distributed.event.loop.client.remote;

import com.ch.distributed.event.loop.common.Node;

public interface RemoteServiceFactory {

    RemoteService create(Node node);
}
