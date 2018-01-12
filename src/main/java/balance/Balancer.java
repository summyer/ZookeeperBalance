package balance;

/**
 * 负载均衡.
 */
public interface Balancer {
    //返回Server的IP地址
    String select() throws Exception;
}