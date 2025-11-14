package com.ch.resource.sync.core.client.cluster;

import com.ch.resource.sync.core.common.Node;

import java.util.List;

public abstract class AbstractCluster implements Cluster{
    private volatile List<Node> nodes;

    @Override
    public void registryNotify(List<Node> nodes) {

    }

    abstract protected Node select(List<Node> nodes);
}
