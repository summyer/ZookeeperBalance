# ZookeeperBalance
zookeeper实现的负载均衡，包括随机算法和轮询算法
服务器注册：
        在容器启动前调用
        //注册zookeeper服务
        ZookeeperUtil.registerServer("192.168.1.19:2181", "/hshb", "/localhost:8080");
负载均衡器：
        logger.info("select ip:"+ZookeeperUtil.channelActive("192.168.1.19:2181","/hshb", BalanceType.RANDOMTYPE));
