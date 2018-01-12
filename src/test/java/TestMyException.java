import org.apache.log4j.Logger;

/**
 * Created by Administrator on 2018/1/12.
 */
public class TestMyException {
    private static Logger logger = Logger.getLogger(TestMyException.class);

    public static void f() throws MyException {
        System.out.println("in f method");
        throw new MyException();
    }

    public static void testPrint() {
        try {
            f();
        } catch (MyException e) {
            // e.printStackTrace();
			/*
			 * 标准输出往往是带缓存的，而标准出错没有缓存（默认设置，可以改）。所以如果你用标准出错打印出来的东西可以马上显示在屏幕，
			 * 而标准输出打印出来的东西可能要再积累几个字符才能一起打印出来。
			 */
            System.out.println("out:我可以catch it!");
            System.err.println("err:catch it!");
            logger.error("log4j:catch it!");
        }
        logger.info("log4j:done!");
    }

    public static void main(String[] args) {
        testPrint();
    }
}
