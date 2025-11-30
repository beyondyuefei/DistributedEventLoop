package com.ch.distributed.event.loop.client;

import com.ch.distributed.event.loop.common.ResourceKey;

public interface ResourceHandlerRequest extends ResourceKey {
    <T> T payload();

    String resourceHandlerName();
}
