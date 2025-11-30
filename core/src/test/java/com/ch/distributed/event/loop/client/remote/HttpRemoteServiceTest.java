package com.ch.distributed.event.loop.client.remote;

import com.ch.distributed.event.loop.client.ResourceHandlerRequest;
import com.ch.distributed.event.loop.client.ResourceHandlerResponse;
import com.ch.distributed.event.loop.common.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@ExtendWith(MockitoExtension.class)
public class HttpRemoteServiceTest {
    @Mock
    private HttpClient mockHttpClient;
    @Mock
    private HttpResponse<String> mockHttpResponse;

    @Test
    public void test() throws ExecutionException, InterruptedException {
        HttpRemoteService httpRemoteService = new HttpRemoteService(new Node("127.0.0.1", 80), mockHttpClient);
        httpRemoteService.start();

        final ResourceHandlerRequest resourceHandlerRequest = new ResourceHandlerRequest() {
            @Override
            public String getKey() {
                return "abc";
            }

            @Override
            public <T> T payload() {
                return (T) "fdsfds";
            }

            @Override
            public String resourceHandlerName() {
                return "resource-a";
            }
        };
        // 1. 准备Mock数据
        String successResponseBody = "{\"name\":\"jack\",\"email\":12312@das.com}";
        when(mockHttpResponse.body()).thenReturn(successResponseBody);
        when(mockHttpResponse.statusCode()).thenReturn(200);

        // Mock CompletableFuture返回成功结果
        CompletableFuture<HttpResponse<String>> successfulFuture = CompletableFuture.completedFuture(mockHttpResponse);
        when(mockHttpClient.sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(successfulFuture);

        final CompletableFuture<ResourceHandlerResponse> completableFuture = httpRemoteService.handle(resourceHandlerRequest);
        Assertions.assertTrue(completableFuture.isDone());
        Assertions.assertTrue(completableFuture.get().isSuccess());
        System.out.println(completableFuture.get().data());
    }

}
