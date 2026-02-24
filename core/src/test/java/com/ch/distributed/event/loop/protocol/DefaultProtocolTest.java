package com.ch.distributed.event.loop.protocol;

import com.ch.distributed.event.loop.client.ResourceHandler;
import com.ch.distributed.event.loop.client.ResourceHandlerRequest;
import com.ch.distributed.event.loop.client.ResourceHandlerResponse;
import com.ch.distributed.event.loop.client.cluster.Cluster;
import com.ch.distributed.event.loop.filter.Filter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * DefaultProtocol.refer() 方法的单元测试
 */
public class DefaultProtocolTest {

    private Cluster mockCluster;
    private Filter mockFilter1;
    private Filter mockFilter2;

    @BeforeEach
    void setUp() {
        // 初始化 Mock 对象
        mockCluster = Mockito.mock(Cluster.class);
        mockFilter1 = Mockito.mock(Filter.class);
        mockFilter2 = Mockito.mock(Filter.class);
    }

    /**
     * 测试正常流程：过滤器按逆序包装，最终返回正确的 ResourceHandler
     */
    @Test
    void testRefer_NormalFlow() {
        // 模拟 cluster.join() 返回一个 ResourceHandler
        ResourceHandler mockResourceHandler = Mockito.mock(ResourceHandler.class);
        when(mockCluster.join()).thenReturn(mockResourceHandler);
        when(mockResourceHandler.handle(any(ResourceHandlerRequest.class), any(Class.class))).thenReturn(CompletableFuture.completedFuture(null));

        // 模拟 Filter 的行为
        when(mockFilter1.filter(any(ResourceHandler.class), any(ResourceHandlerRequest.class), any(Class.class)))
                .thenAnswer(invocation -> {
                    ResourceHandler nextHandler = invocation.getArgument(0);
                    ResourceHandlerRequest<?> request = invocation.getArgument(1);
                    Class<?> clazz = invocation.getArgument(2);
                    return nextHandler.handle(request, clazz);
                });
        when(mockFilter2.filter(any(ResourceHandler.class), any(ResourceHandlerRequest.class), any(Class.class)))
                .thenAnswer(invocation -> {
                    ResourceHandler nextHandler = invocation.getArgument(0);
                    ResourceHandlerRequest<?> request = invocation.getArgument(1);
                    Class<?> clazz = invocation.getArgument(2);
                    return nextHandler.handle(request, clazz);
                });

        // 构造 DefaultProtocol 实例
        DefaultProtocol protocol = new DefaultProtocol(mockCluster);
        // 使用反射修改 filters 字段，注入 Mock 的 Filter 列表
        try {
            var filtersField = DefaultProtocol.class.getDeclaredField("filters");
            filtersField.setAccessible(true);
            filtersField.set(protocol, List.of(mockFilter1, mockFilter2));
        } catch (Exception e) {
            fail("Failed to inject mock filters into DefaultProtocol", e);
        }

        // 调用 refer 方法
        ResourceHandler resultHandler = protocol.refer("test-handler");

        // 构造测试请求
        ResourceHandlerRequest<String> request = Mockito.mock(ResourceHandlerRequest.class);
        when(request.getKey()).thenReturn("key");
        when(request.payload()).thenReturn("payload");
        when(request.resourceHandlerName()).thenReturn("test-handler");

        // 执行 handle 方法
        CompletableFuture<ResourceHandlerResponse<String>> future = resultHandler.handle(request, String.class);

        // 验证 Filter 调用顺序
        InOrder inOrder = Mockito.inOrder(mockFilter1, mockFilter2);
        inOrder.verify(mockFilter1).filter(any(ResourceHandler.class), any(ResourceHandlerRequest.class), any(Class.class));
        inOrder.verify(mockFilter2).filter(any(ResourceHandler.class), any(ResourceHandlerRequest.class), any(Class.class));

        // 验证最终调用了 mockResourceHandler.handle()
        verify(mockResourceHandler).handle(request, String.class);
        assertTrue(future.isDone());
    }

    /**
     * 测试边界情况：过滤器列表为空时，直接返回 cluster.join() 的结果
     */
    @Test
    void testRefer_EmptyFilters() {
        // 模拟 cluster.join() 返回一个 ResourceHandler
        ResourceHandler mockResourceHandler = Mockito.mock(ResourceHandler.class);
        when(mockCluster.join()).thenReturn(mockResourceHandler);

        // 构造 DefaultProtocol 实例，过滤器列表为空
        DefaultProtocol protocol = new DefaultProtocol(mockCluster, List.of());

        // 调用 refer 方法
        ResourceHandler resultHandler = protocol.refer("test-handler");

        // 验证直接返回了 mockResourceHandler
        assertSame(mockResourceHandler, resultHandler);
    }

    /**
     * 测试边界情况：cluster.join() 抛出异常
     */
    @Test
    void testRefer_ClusterJoinThrowsException() {
        // 模拟 cluster.join() 抛出 RuntimeException
        when(mockCluster.join()).thenThrow(new RuntimeException("Cluster join failed"));

        // 构造 DefaultProtocol 实例
        DefaultProtocol protocol = new DefaultProtocol(mockCluster);

        // 调用 refer 方法，期望抛出 RuntimeException
        RuntimeException exception = assertThrows(RuntimeException.class, () -> protocol.refer("test-handler"));
        assertEquals("Cluster join failed", exception.getMessage());
    }
}
