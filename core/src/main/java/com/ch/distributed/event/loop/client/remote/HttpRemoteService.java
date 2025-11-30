package com.ch.distributed.event.loop.client.remote;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.ch.distributed.event.loop.client.ResourceHandlerRequest;
import com.ch.distributed.event.loop.client.ResourceHandlerResponse;
import com.ch.distributed.event.loop.common.Node;
import com.ch.distributed.event.loop.common.ResourceHandlerException;
import org.slf4j.Logger;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class HttpRemoteService extends AbstractRemoteService {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(HttpRemoteService.class);
    private volatile HttpClient httpClient;
    private static final String URL = "http://%s:%s/resource/handler/";

    public HttpRemoteService(Node node) {
        super(node);
    }

    protected HttpRemoteService(Node node, HttpClient httpClient) {
        super(node);
        this.httpClient = httpClient;
    }

    @Override
    public void start() {
        if (httpClient != null) {
            return;
        }
        synchronized (this) {
            if (httpClient != null) {
                return;
            }
            this.httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .connectTimeout(Duration.ofSeconds(15))
                    .executor(Executors.newFixedThreadPool(30)) // 自定义线程池
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .build();

            // 设置连接池系统属性
            System.setProperty("jdk.httpclient.keepalive.timeout", "30"); // 保持连接时间（秒）
            System.setProperty("jdk.httpclient.connectionPoolSize", "100"); // 连接池大小
            System.setProperty("jdk.httpclient.maxConnections", "100"); // 最大连接数

            LOGGER.info("HttpRemoteService start success");
        }
    }

    @Override
    public CompletableFuture<ResourceHandlerResponse> handle(ResourceHandlerRequest resourceHandlerRequest) {
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL.formatted(node.getIp(), node.getPort())))
                .header("Content-Type", "application/json")
                .header("ResourceHandlerName", resourceHandlerRequest.resourceHandlerName())
                .POST(HttpRequest.BodyPublishers.ofString(resourceHandlerRequest.payload()))
                .timeout(Duration.ofSeconds(5))
                .build();
        final CompletableFuture<HttpResponse<String>> completableFuture = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        return completableFuture.thenApply(response -> new ResourceHandlerResponse() {
            @Override
            public boolean isSuccess() {
                return response.statusCode() == 200;
            }

            @Override
            public String data() {
                return response.body();
            }

            @Override
            public Exception exception() {
                // fixme: check这么写是否正确，以及 status != 200 的处理
                return new ResourceHandlerException(completableFuture.exceptionNow());
            }
        });
    }

    @Override
    public void stop() {
        httpClient.close();
        LOGGER.info("HttpRemoteService stop success");
    }
}
