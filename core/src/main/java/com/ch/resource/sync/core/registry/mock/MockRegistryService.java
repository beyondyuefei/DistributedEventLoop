package com.ch.resource.sync.core.registry.mock;

import com.ch.resource.sync.core.common.Node;
import com.ch.resource.sync.core.registry.AbstractRegistryService;
import com.ch.resource.sync.core.registry.RegistryNotifyListener;

import java.util.List;

public class MockRegistryService extends AbstractRegistryService {
    @Override
    public void register() {
       // do nothing
    }

    @Override
    public void subscribe(RegistryNotifyListener registryNotifyListener) {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void unregister() {

    }

    @Override
    public List<Node> getNodes() {
        return List.of(new Node("127.0.0.1", 1211));
    }
}
