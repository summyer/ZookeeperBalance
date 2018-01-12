/**
 * Created by Administrator on 2018/1/12.
 */
import java.util.Date;

public class MyException extends Exception {
    private static final long serialVersionUID = 1L;
    private String errorMsg;

    public MyException() {
        super((new Date()) + "");
    }

    public MyException(String msg) {
        super((new Date()) + " " + msg);
    }

    @Override
    public String getMessage() {
        // TODO Auto-generated method stub
        return super.getMessage();
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
