package com.ch.distributed.event.loop.client;

import com.ch.distributed.event.loop.common.ResourceKey;

public interface ResourceHandlerRequest<T> extends ResourceKey {
     T payload();

    String resourceHandlerName();
}
