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
			 * ��׼��������Ǵ�����ģ�����׼����û�л��棨Ĭ�����ã����Ըģ�������������ñ�׼�����ӡ�����Ķ�������������ʾ����Ļ��
			 * ����׼�����ӡ�����Ķ�������Ҫ�ٻ��ۼ����ַ�����һ���ӡ������
			 */
            System.out.println("out:�ҿ���catch it!");
            System.err.println("err:catch it!");
            logger.error("log4j:catch it!");
        }
        logger.info("log4j:done!");
    }

    public static void main(String[] args) {
        testPrint();
    }
}
