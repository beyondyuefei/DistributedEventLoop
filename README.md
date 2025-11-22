# DistributedEventLoop
- 一级路由：支持分布式环境下根据业务key路由到指定机器节点
- 二级路由：根据业务key分配一个EventLoop，基于EventLoop的线程安全实现相关的业务逻辑
