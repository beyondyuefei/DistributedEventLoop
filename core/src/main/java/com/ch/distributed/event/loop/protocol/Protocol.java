package com.ch.distributed.event.loop.protocol;

import com.ch.distributed.event.loop.client.ResourceHandler;

public interface Protocol {
     ResourceHandler refer(final String resourceHandlerName);
}
