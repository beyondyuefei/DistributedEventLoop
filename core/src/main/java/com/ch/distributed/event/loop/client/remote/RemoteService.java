package com.ch.distributed.event.loop.client.remote;

import com.ch.distributed.event.loop.client.ResourceHandler;
import com.ch.distributed.event.loop.component.Component;

public interface RemoteService<T, R> extends ResourceHandler<T,R>, Component {
}
