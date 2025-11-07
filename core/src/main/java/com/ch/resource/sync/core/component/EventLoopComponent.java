package com.ch.resource.sync.core.component;

import com.ch.resource.sync.core.lifecycle.Lifecycle;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;

public class EventLoopComponent implements Lifecycle {
    private final EventLoopGroup eventLoopGroup;

    public EventLoopComponent() {
        this(Math.max(1, Runtime.getRuntime().availableProcessors() * 2));
    }

    public EventLoopComponent(final int threadNum) {
        this.eventLoopGroup = new DefaultEventLoopGroup(threadNum);
    }

    public EventLoop next() {
        return eventLoopGroup.next();
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
        eventLoopGroup.close();
    }
}
