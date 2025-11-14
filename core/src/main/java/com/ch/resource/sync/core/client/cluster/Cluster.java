package com.ch.resource.sync.core.client.cluster;

import com.ch.resource.sync.core.client.ResourceHandler;
import com.ch.resource.sync.core.common.Node;
import com.ch.resource.sync.core.component.Component;
import com.ch.resource.sync.core.registry.RegistryNotifyListener;

import java.util.List;

public interface Cluster extends RegistryNotifyListener, Component {
    ResourceHandler join(final List<Node> nodes);
}
