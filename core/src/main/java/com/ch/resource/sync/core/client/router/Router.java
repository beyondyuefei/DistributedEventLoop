package com.ch.resource.sync.core.client.router;

import com.ch.resource.sync.core.client.ResourceHandler;
import com.ch.resource.sync.core.common.Node;
import com.ch.resource.sync.core.component.Component;

import java.util.List;

public interface Router extends ResourceHandler, Component {
    Node select(final List<Node> nodes);
}
