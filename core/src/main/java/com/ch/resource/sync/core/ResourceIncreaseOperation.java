package com.ch.resource.sync.core;

import java.util.concurrent.CompletableFuture;

public interface ResourceIncreaseOperation extends ResourceOperation{
    void increaseSync(ResourceKey key);

    CompletableFuture<Void> increaseAsync(ResourceKey key);
}
