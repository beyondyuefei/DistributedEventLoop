package com.ch.resource.sync.core.client.cluster;

import com.ch.resource.sync.core.client.ResourceHandler;
import com.ch.resource.sync.core.common.Node;
import com.ch.resource.sync.core.component.Component;

import java.util.List;

public interface Cluster extends ResourceHandler, Component {
    List<Node> getNodes();
}
