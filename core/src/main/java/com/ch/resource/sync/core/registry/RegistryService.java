package com.ch.resource.sync.core.registry;

import com.ch.resource.sync.core.common.Node;

import java.util.List;

public interface RegistryService {
    void register();

    // note: 职责上应该是 Cluster需要订阅，并关注集群变化
    void subscribe(final RegistryNotifyListener registryNotifyListener);

    List<Node> getNodes();

    void unsubscribe();

    void unregister();
}
