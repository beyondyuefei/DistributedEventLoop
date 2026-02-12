package com.ch.distributed.event.loop.protocol;

import com.ch.distributed.event.loop.client.ResourceHandler;
import com.ch.distributed.event.loop.client.ResourceHandlerRequest;
import com.ch.distributed.event.loop.client.ResourceHandlerResponse;
import com.ch.distributed.event.loop.client.cluster.Cluster;
import com.ch.distributed.event.loop.client.cluster.FastfailCluster;
import com.ch.distributed.event.loop.filter.Filter;
import com.ch.distributed.event.loop.filter.impl.LogFilter;
import com.ch.distributed.event.loop.filter.impl.MockFilter;

import java.util.List;
import java.util.concurrent.CompletableFuture;

// todo: 还要处理resourceHandlerName的唯一性问题，因为这里存在多个resourceHandlerName (当作RPC的接口全限名来使用)，如何处理？
//  另外，服务端上报的resourceHandlerName，也是要附带服务端IP地址，建连等需要使用到
public class DefaultProtocol implements Protocol {
    // todo: 自动识别Filter并加载
    private final List<Filter> filters = List.of(new LogFilter(), new MockFilter());

    @Override
    public ResourceHandler refer(String resourceHandlerName) {
        final Cluster cluster = new FastfailCluster();
        // 将clusterResourceHandler放在Invoker/Filter链式调用的最后来执行 (因为涉及到真正的网络调用等，自定义的Filter优先执行)
        ResourceHandler lastResourceHandler = cluster.join();
        for (int i = filters.size() - 1; i >= 0; i--) {
            final Filter filter = filters.get(i);
            final ResourceHandler nextResourceHandler = lastResourceHandler;
            lastResourceHandler = wrapperFilter2ResourceHandler(nextResourceHandler, filter);
        }
        return lastResourceHandler;
    }

    private ResourceHandler wrapperFilter2ResourceHandler(final ResourceHandler resourceHandler, final Filter filter) {
        return new ResourceHandler() {
            @Override
            public <T, R> CompletableFuture<ResourceHandlerResponse<R>> handle(ResourceHandlerRequest<T> resourceHandlerRequest, Class<R> rClass) {
                return filter.filter(resourceHandler, resourceHandlerRequest, rClass);
            }
        };
    }
}
