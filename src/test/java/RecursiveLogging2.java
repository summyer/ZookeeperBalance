/**
 * Created by Administrator on 2018/1/12.
 */
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class RecursiveLogging2 {
    static {
        try {
            Log4jPrintStream.redirectSystemOut();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        //TestMyException.testPrint();
        System.out.println("jell");
    }

}

class Log4jPrintStream extends PrintStream {
    private Logger logger = Logger.getLogger("SystemOut");
    private static PrintStream instance = null;

    private Log4jPrintStream(OutputStream out) {
        super(out);
    }

    public static void redirectSystemOut() throws Exception {
        instance=new Log4jPrintStream(new PrintStream(new FileOutputStream(new File( "F:/testerr.txt "))));
        System.setOut(instance);
    }


    public void println(boolean x) {
        logger.debug(Boolean.valueOf(x));
    }

    public void println(char x) {
        logger.debug(Character.valueOf(x));
    }

    public void println(char[] x) {
        logger.debug(x == null ? null : new String(x));
    }

    public void println(double x) {
        logger.debug(Double.valueOf(x));
    }

    public void println(float x) {
        logger.debug(Float.valueOf(x));
    }

    public void println(int x) {
        logger.debug(Integer.valueOf(x));
    }

    public void println(long x) {
        logger.debug(x);
    }

    public void println(Object x) {
        logger.debug(x);
    }

    public void println(String x) {
        logger.debug(x);
        super.println(x);
    }

}