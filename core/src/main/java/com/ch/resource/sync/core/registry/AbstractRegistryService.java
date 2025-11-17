package com.ch.resource.sync.core.registry;

import com.ch.resource.sync.core.common.Node;

public abstract class AbstractRegistryService implements RegistryService{
    protected volatile RegistryNotifyListener registryNotifyListeners;

    @Override
    public void subscribe(RegistryNotifyListener registryNotifyListener) {
        this.registryNotifyListeners = registryNotifyListener;
    }

    protected final Node localNode() {
        // fixme 如果是netty启动http handler，则需要获取端口才行
        return new Node("127.0.0.1", 1211);
    }
}
