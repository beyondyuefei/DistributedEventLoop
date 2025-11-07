package com.ch.resource.sync.core;

import java.util.concurrent.CompletableFuture;

public interface ResourceDecreaseOperation extends ResourceOperation{
    void decreaseSync(ResourceKey key);

    CompletableFuture<Void> decreaseAsync(ResourceKey key);
}
