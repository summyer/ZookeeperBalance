package balance;

import com.github.zkclient.IZkClient;

import java.util.List;
import java.util.Random;

/**
 * Ëæ»úËã·¨
 */
public class RandomBalancer implements Balancer {

    private IZkClient zkClient;
    private String node;

    public RandomBalancer(IZkClient zkClient,String node){
        this.zkClient = zkClient;
        this.node=node;
    }

    public String select() throws Exception{
        List<String> workers = zkClient.getChildren(node);
        if(workers == null){
            return null;
        }
        int size = workers.size();
        if(size == 0){
            return null;
        }
        Random r = new Random();
        int index = r.nextInt(size);
        return workers.get(index);
    }

}