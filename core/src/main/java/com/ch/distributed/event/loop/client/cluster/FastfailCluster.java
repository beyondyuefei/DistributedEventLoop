package com.ch.distributed.event.loop.client.cluster;

import com.ch.distributed.event.loop.client.ResourceHandler;
import com.ch.distributed.event.loop.client.cluster.support.FastfailClusterResourceHandler;

public class FastfailCluster implements Cluster {
    @Override
    public <T,R> ResourceHandler<T,R> join() {
        return new FastfailClusterResourceHandler<>();
    }
}
