package com.ch.distributed.event.loop.protocol;

import com.ch.distributed.event.loop.client.ResourceHandler;

import java.util.concurrent.ConcurrentHashMap;

// todo: 还要处理resourceHandlerName的唯一性问题，因为这里存在多个resourceHandlerName (当作RPC的接口全限名来使用)，如何处理？
 // 另外，服务端上报的resourceHandlerName，也是要附带服务端IP地址，建连等需要使用到
public class DefaultProtocol implements  Protocol{
    private ConcurrentHashMap<String, ResourceHandler<?,?>> resourceHandlerMap = new ConcurrentHashMap<>();
    @Override
    public <T, R> ResourceHandler<T, R> refer(String resourceHandlerName) {
        // todo: not find in resourceHandlerMap , do create resourceHandler (包括: RemoteService等，负责组装初始化DefaultResourceHandler实现)
        return null;
    }
}
