package balance;

import com.github.zkclient.IZkClient;

import java.util.List;

/**
 * ÂÖ×ªËã·¨
 */
public class RoundRobinBalancer implements Balancer {

    private IZkClient zkClient;
    private String node;

    public RoundRobinBalancer(IZkClient zkClient, String node){
        this.zkClient = zkClient;
        this.node=node;
    }

    public String select() throws Exception {
        if(!zkClient.exists(node)){
            zkClient.createPersistent(node);
        }
        List<String> workers = zkClient.getChildren(node);
        if(workers == null){
            return null;
        }
        int size = workers.size();
        if(size == 0){
            return null;
        }

        int round = -1;
        SharedCount sc = new SharedCount("/round", -1);
        boolean success = false;
        sc.start();
        while(!success){
            round = sc.getCount();
            round++;
            if(round >= Integer.MAX_VALUE){
                round = -1;
            }
            success = sc.trySetCount(round);
        }
        int index = round % size;
        return workers.get(index);
    }
    private class SharedCount{
        String childNode;
        Integer round;
        public SharedCount(String childNode,Integer round){
            this.childNode=childNode;
            this.round=round;
        }
        public Integer getCount(){
           return Integer.parseInt(new String(zkClient.readData(childNode)));
        }
        public void start(){
            if(!zkClient.exists(childNode)){
                zkClient.createPersistent(childNode,"-1".getBytes());
            }
        }
        public boolean trySetCount(Integer round){
            synchronized (zkClient){
                zkClient.writeData(childNode,round.toString().getBytes());
            }
            return true;
        }
    }

}
