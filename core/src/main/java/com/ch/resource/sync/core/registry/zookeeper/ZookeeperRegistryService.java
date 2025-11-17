package com.ch.resource.sync.core.registry.zookeeper;

import com.ch.resource.sync.core.common.Node;
import com.ch.resource.sync.core.registry.AbstractRegistryService;
import com.ch.resource.sync.core.registry.RegistryNotifyListener;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.List;

public class ZookeeperRegistryService extends AbstractRegistryService {
    private CuratorFramework zkClient;
    private RegistryNotifyListener listener;
    private static final String CLUSTER_PATH = "/resource-sync/cluster";
    private CuratorCache curatorCache;

    public ZookeeperRegistryService(final String zkServers, final Integer connectionTimeout) {
        if (zkServers == null) {
            throw new IllegalArgumentException("zkServers can not be null");
        }

        this.zkClient = CuratorFrameworkFactory.builder()
                .connectString(zkServers)
                .sessionTimeoutMs(60000)
                .connectionTimeoutMs(connectionTimeout)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();

        zkClient.getConnectionStateListenable().addListener(this::handleConnectionStateChange);
        zkClient.start();
    }

    @Override
    public void register() {
        final Node localNode = localNode();
       // zkClient.
    }

    @Override
    public void subscribe(RegistryNotifyListener registryNotifyListener) {
        //todo

        doNotify();
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void unregister() {

    }

    @Override
    public List<Node> getNodes() {
        return List.of();
    }
}
