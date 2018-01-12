package test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * Created by Administrator on 2018/1/11.
 */
public class T2 {
    private static final Logger logger = LoggerFactory.getLogger(T2.class);
    public static void main(String[] args) throws Exception{
        System.setOut(new PrintStream(new FileOutputStream(new File( "F:/testerr.txt "))));
        logger.error("dd");
        logger.info(T2.class.getName());
        //System.setOut(new PrintStream(new FileOutputStream(new File( "F:/test.txt "))));
        //System.out.println( "haha ");
        //System.setErr(new PrintStream(new FileOutputStream(new File( "F:/testerr.txt "))));
        //System.err.println("helllo");
    }
}
