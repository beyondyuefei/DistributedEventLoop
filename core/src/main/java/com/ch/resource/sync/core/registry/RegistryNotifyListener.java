package com.ch.resource.sync.core.registry;

import com.ch.resource.sync.core.common.Node;

import java.util.List;

public interface RegistryNotifyListener {
    void registryNotify(final List<Node> nodes);
}
