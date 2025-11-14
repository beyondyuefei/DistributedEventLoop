package com.ch.resource.sync.core.registry;

import com.ch.resource.sync.core.common.Node;

public interface RegistryService {
    void register(final Node node);

    void subscribe(final RegistryNotifyListener listener);
}
