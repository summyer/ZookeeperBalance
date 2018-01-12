package core;

import balance.BalanceType;
import balance.Balancer;
import balance.RandomBalancer;
import balance.RoundRobinBalancer;
import com.github.zkclient.IZkClient;
import com.github.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title: ZookeeperUtil
 * @Package core
 * 注册服务器工具类
 * @author 作者 E-mail <a href="sunxiakun@163.com">sxk</a>
 * @date 2018/1/12 10:43
 * @version V1.0
 */
public class ZookeeperUtil {
    private static final Logger logger = LoggerFactory.getLogger(ZookeeperUtil.class);
    public static void registerServer(final String server,final String namespace,final String node){
        new Thread(){
            @Override
            public void run() {
                IZkClient zkClient = new ZkClient(server);
                if(!zkClient.exists(namespace)){
                    zkClient.createPersistent(namespace);
                }
                if(zkClient.exists(namespace + node)){
                    zkClient.delete(namespace + node);
                }
                zkClient.createEphemeral(namespace + node);
                logger.info(node+" registe success! node path:"+namespace + node);
                //Thread.currentThread().interrupt();
                while (!Thread.currentThread().isInterrupted()){
                    try {
                        Thread.sleep(Long.MAX_VALUE);
                    } catch (InterruptedException e) {
                        zkClient.close();
                        logger.info(node+" close ! delete path:"+namespace + node);
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }.start();
    }
    public static String channelActive(String server, String namespace, BalanceType type) throws Exception {
        IZkClient zkClient = new ZkClient(server);
        Balancer balancer = null;
        switch (type){
            case RANDOMTYPE:
                balancer=new RandomBalancer(zkClient,namespace);
                break;
            case ROUNDROBIN:
                balancer=new RoundRobinBalancer(zkClient,namespace);
                break;
            default:
                balancer=new RandomBalancer(zkClient,namespace);
        }
        String ip = balancer.select();
        if(ip==null){
            throw new Exception("无可用服务器");
        }
        zkClient.close();
        logger.info("当前选中的ip："+ip+"  thread:"+Thread.currentThread().getName());
        return ip;
    }
    public static void main(String[] args){
         /*registerServer("192.168.1.19:2181","/hshb","/localhost:8080");
        try {
            channelActive("192.168.1.19:2181");
        }catch (Exception e){
            logger.error(e.getMessage());
        }*/
    }
}
