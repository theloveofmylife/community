package life.zihuan.community.exception;

/**
 * @author 60561
 */
public interface ICustomizeErrorCode {
    String message = null;
    int code = 0;

    /**
     * 获得error的message
     * @return message
     */
    String getMessage();

    /**
     * 获得error的code
     * @return code
     */
    int getCode();
}
