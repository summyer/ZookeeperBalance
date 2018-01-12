package core;

import com.github.zkclient.IZkClient;
import com.github.zkclient.IZkStateListener;
import com.github.zkclient.ZkClient;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 2018/1/11.
 */
public class Starter {
    private static final Logger logger = LoggerFactory.getLogger(Starter.class);

    private static List<String> applicationAddress=new ArrayList<String>();
    private static List<Thread> createdThread=new ArrayList<Thread>();
    private static List<Future<?>> tasks=new ArrayList<Future<?>>();
    static {
        for(int i=0;i<5;i++){
            applicationAddress.add("127.0.0.1:"+(3000+i));
        }
    }
    public static void main(String[] args){
        logger.info(Starter.class.getName());
        logger.info("当前可供注册的client");
        print(applicationAddress);
        //127.0.0.1:3000 服务器地址
        final String server="192.168.1.19:2181";
        IZkClient zkClient = new ZkClient(server,5000);
        final String namespace="/hshb";
        if(!zkClient.exists(namespace)){
            zkClient.createPersistent(namespace);
        }else {
            zkClient.deleteRecursive(namespace);
            zkClient.createPersistent(namespace);
        }
        for(final String url:applicationAddress){
            createdThread.add(new Thread("thread:"+url){
                @Override
                public void run() {
                    final String threadName = this.getName();
                    IZkClient zkClient = new ZkClient(server);
                    zkClient.createEphemeral(namespace + "/" + url);
                    zkClient.subscribeStateChanges(new IZkStateListener() {
                        public void handleStateChanged(Watcher.Event.KeeperState keeperState) throws Exception {
                            logger.info("session is close: " + threadName);
                        }

                        public void handleNewSession() throws Exception {
                            logger.info("session is created: " + threadName);
                        }
                    });
                    System.out.println("thread is open:" + threadName);
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            logger.info(threadName+"正在执行 state:"+zkClient.getZooKeeper().getState());
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            logger.info(threadName+" is interrupt");
                            Thread.currentThread().interrupt();
                        }
                    }

                    try {
                        zkClient.getZooKeeper().close();
                        Thread.sleep(10000);
                    } catch (Exception e) {
                    }
                    logger.info("thread is close:" + threadName+" state:"+zkClient.getZooKeeper().getState());
                }
            });
        }
        logger.info("开始创建session会话---");
        starts(createdThread);
        try {
            Thread.sleep(2000);
        }catch (Exception e){
            e.printStackTrace();
        }
        List<String> nodes=zkClient.getChildren(namespace);
        print(nodes);
        tasks.get(1).cancel(true);
        try {
            logger.info("Press any key to exit.");
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  static <T> void print(List<T> data){
        for(T item:data){
            logger.info("node ----"+item.toString());
        }
    }
    public  static void starts(List<Thread> thread){
        ExecutorService es = Executors.newCachedThreadPool();
        for(Thread ithread:thread){
            Future<?> task = es.submit(ithread);
            tasks.add(task);
        }
    }
}
