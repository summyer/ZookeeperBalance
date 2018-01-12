package test;

import balance.BalanceType;
import core.ZookeeperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2018/1/12.
 */
public class SearchIPTest {
    private static final Logger logger = LoggerFactory.getLogger(SearchIPTest.class);

    public static void main(String[] args){
        try {
            ZookeeperUtil.channelActive("192.168.1.19:2181","/hshb", BalanceType.RANDOMTYPE);
        }catch (Exception e){
            logger.error(e.getMessage());
        }

    }
}
