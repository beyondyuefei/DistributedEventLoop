package com.ch.distributed.event.loop.server.netty;

import com.ch.distributed.event.loop.lifecycle.LifecycleException;
import com.ch.distributed.event.loop.server.WebServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class NettyWebServer implements WebServer {
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;
    private final HttpNettyHandler httpNettyHandler;
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyWebServer.class);

    public NettyWebServer() {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        httpNettyHandler = new HttpNettyHandler();
    }

    @Override
    public void start() {
        try {
            final ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new HttpServerCodec()); // HTTP 编解码器
                            ch.pipeline().addLast(new HttpObjectAggregator(65536)); // 聚合 HTTP 消息
                            ch.pipeline().addLast(httpNettyHandler); // 自定义 HTTP 处理器
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128) // 设置连接队列大小
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // 保持长连接
            final ChannelFuture channelFuture = bootstrap.bind(80).await();
            if (!channelFuture.isSuccess()) {
                final String errorMsg = "Failed to start Netty server on port 80, channelFuture.isSuccess() return false!";
                LOGGER.error(errorMsg, channelFuture.cause());
                throw new LifecycleException(errorMsg);
            }
            LOGGER.info("Netty server started on port 80");
        } catch (Exception e) {
            final String errorMsg = "Failed to start Netty server on port 80, occur exception";
            LOGGER.error(errorMsg, e);
            try {
                stop();
            } catch (LifecycleException e1) {
                // ignore
            }
            throw new LifecycleException(errorMsg, e);
        }
    }

    @Override
    public void stop() {
        try {
            final Future<?> bossShutDownFuture = bossGroup.shutdownGracefully(0, 3, TimeUnit.SECONDS);
            final Future<?> workerShutDownFuture = workerGroup.shutdownGracefully(0, 3, TimeUnit.SECONDS);
            if (bossShutDownFuture.sync().isSuccess() && workerShutDownFuture.sync().isSuccess()) {
                LOGGER.info("Netty server stopped");
            } else {
                final Throwable throwable = (bossShutDownFuture.cause() != null ? bossShutDownFuture.cause() : workerShutDownFuture.cause());
                final String errorMsg = "Failed to stop Netty server, sync() occur exception";
                LOGGER.warn(errorMsg, throwable);
                throw new LifecycleException(errorMsg, throwable);
            }
        } catch (Exception e) {
            final String errorMsg = "Failed to stop Netty server, occur exception";
            LOGGER.error(errorMsg, e);
            throw new LifecycleException(errorMsg, e);
        }
    }
}
