# windy
轻量级分布式RPC框架实现（基础功能实现）

模块说明：
spi：服务接口
provider：服务实现
consumer：服务消费者
rpc：远程调用

启动：


1. 启动netty服务，入口在rpc模块下的ServerBoot

2. 消费者调用，入口在consumer模块下的application

注意：先在rpc-common包中的Constants配置zookeeper连接
