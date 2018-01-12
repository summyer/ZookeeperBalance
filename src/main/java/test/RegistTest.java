package test;

import core.ZookeeperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2018/1/12.
 */
public class RegistTest {
    private static final Logger logger = LoggerFactory.getLogger(RegistTest.class);

    public static void main(String[] args){
        ZookeeperUtil.registerServer("192.168.1.19:2181","/hshb","/localhost:8080");
    }
}
