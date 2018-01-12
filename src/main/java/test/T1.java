package test;

import balance.Balancer;
import balance.RoundRobinBalancer;
import com.github.zkclient.IZkClient;
import com.github.zkclient.ZkClient;
import core.Starter;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2018/1/11.
 */
public class T1 {
    private static final Logger logger = LoggerFactory.getLogger(T1.class);

    public static void main(String[] args) throws Exception{

        for(int i=0;i<4;i++){
            new Thread("thread"+i){
                public void run() {
                    try {
                        channelActive();
                    }catch (KeeperException.SessionExpiredException e){

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }.start();
            Thread.sleep(1000);
        }
    }
    public static void channelActive() throws Exception {
        String server="192.168.1.19:2181";
        IZkClient zkClient = new ZkClient(server);
        while(true){
            Starter.print(zkClient.getChildren("/hshb"));
            //Balancer balancer = new RandomBalancer(zkClient,"/hshb");
            Balancer balancer = new RoundRobinBalancer(zkClient,"/hshb");
            Thread.sleep(1000);
            String ip = balancer.select();
            //zkClient.delete("/hshb/round");
            logger.info("当前选中的ip："+ip+"  thread:"+Thread.currentThread().getName());
        }
    }
}
