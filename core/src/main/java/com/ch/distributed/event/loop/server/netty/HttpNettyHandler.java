package com.ch.distributed.event.loop.server.netty;

import com.ch.distributed.event.loop.server.ResourceExecutorComponent;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpNettyHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpNettyHandler.class);
    private ResourceExecutorComponent resourceExecutorComponent;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) {
        final String requestBody = request.content().toString(CharsetUtil.UTF_8);
        LOGGER.info("Request body: {}, headers:{}, version:{}, path:{}", requestBody, request.headers(), request.protocolVersion(), request.uri());
        // todo:
        //  1、pasrse request
        //  2、 resourceExecutorComponent.execute(runnbale, request)
        //  3、 response
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.error("Exception caught: ", cause);
        final ChannelFuture closeFuture = ctx.close();
        logFutureResult(closeFuture, "ChannelHandlerContext close() successfully", "ChannelHandlerContext close error");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        LOGGER.debug("channel closed...");
    }

    private void logFutureResult(final ChannelFuture channelFuture, final String successMsg, final String errorMsg) {
        channelFuture.addListener(future -> {
            if (future.isSuccess()) {
                LOGGER.debug(successMsg);
            } else {
                LOGGER.error(errorMsg, future.cause());
            }
        });
    }

    private DefaultFullHttpResponse createResponseWithEmptyContent(final HttpResponseStatus status) {
        final DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status);
        response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, 0);
        return response;
    }
}
