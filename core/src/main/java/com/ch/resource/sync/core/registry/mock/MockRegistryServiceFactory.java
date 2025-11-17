package com.ch.resource.sync.core.registry.mock;

import com.ch.resource.sync.core.registry.RegistryService;
import com.ch.resource.sync.core.registry.RegistryServiceFactory;

import java.util.Map;

public class MockRegistryServiceFactory implements RegistryServiceFactory {
    @Override
    public RegistryService createRegistryService(Map<String, String> configMap) {
        return new MockRegistryService();
    }
}
