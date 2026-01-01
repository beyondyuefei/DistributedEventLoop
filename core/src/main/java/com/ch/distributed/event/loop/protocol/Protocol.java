package com.ch.distributed.event.loop.protocol;

import com.ch.distributed.event.loop.client.ResourceHandler;

public interface Protocol {
    <T, R> ResourceHandler<T, R> refer(final String resourceHandlerName);
}
