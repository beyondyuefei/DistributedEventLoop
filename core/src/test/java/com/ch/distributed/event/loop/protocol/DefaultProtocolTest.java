package com.ch.distributed.event.loop.protocol;

import com.ch.distributed.event.loop.client.ResourceHandler;
import com.ch.distributed.event.loop.client.ResourceHandlerRequest;
import com.ch.distributed.event.loop.client.ResourceHandlerResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

public class DefaultProtocolTest {
    @Test
    public void test() {
        final DefaultProtocol defaultProtocol = new DefaultProtocol();
        final ResourceHandler resourceHandler = defaultProtocol.refer("hello-test");
        final ResourceHandlerRequest<String> resourceHandlerRequest = new ResourceHandlerRequest<>() {
            @Override
            public String getKey() {
                return "abc";
            }

            @Override
            public String payload() {
                return "fdsfds";
            }

            @Override
            public String resourceHandlerName() {
                return "resource-a";
            }
        };
        final CompletableFuture<ResourceHandlerResponse<User>> completableFuture = resourceHandler.handle(resourceHandlerRequest, User.class);
        Assertions.assertTrue(completableFuture.isDone());
    }

    record User(String name, String email) {
    }
}
