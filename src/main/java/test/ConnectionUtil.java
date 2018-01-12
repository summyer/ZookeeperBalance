package test;

import com.github.zkclient.IZkClient;
import com.github.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/11.
 */
public class ConnectionUtil {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionUtil.class);

    private static List<String> applicationAddress=new ArrayList<String>();
    private static List<String> accessAddress=new ArrayList<String>();
    static {
        for(int i=0;i<5;i++){
            applicationAddress.add("127.0.0.1:"+(3000+i));
        }
    }
    public static void main(String[] args){
        //127.0.0.1:3000 ·þÎñÆ÷µØÖ·
        final String server="192.168.1.19:2181";
        IZkClient zkClient = new ZkClient(server,5000);
        final String namespace="/hshb";
        if(zkClient.exists(namespace)){
            //print(accessAddress);
            while (1==1){
                try {
                    accessAddress=zkClient.getChildren(namespace);
                    Thread.sleep(2000);
                    print(accessAddress);
                    logger.info("-------------"+new Date());
                }catch (Exception e){

                }
            }
        }
    }
    public  static <T> void print(List<T> data){
        for(T item:data){
            logger.info(item.toString());
        }
    }
}
