package com.ch.distributed.event.loop.server;

import com.ch.distributed.event.loop.component.Component;

public interface WebServer extends Component {
    /**
     * 启动Web Server组件、绑定bind端口 (如:80)、配置处理http请求的处理器
     */
    void start();
}
