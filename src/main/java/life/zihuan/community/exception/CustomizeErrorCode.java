package life.zihuan.community.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

/**
 * @author 60561
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001,"你找的问题已不在，请换一个！！"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或回复！！"),
    NO_LOGIN(2003,"未登录不能进行评论，请先登录"),
    SYS_ERROR(2004,"错误网页！！"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在！！"),
    COMMENT_NOT_FOUND(2006,"回复的评论不存在了！！"),
    CONTENT_IS_EMPTY(2007,"输入内容不能为空");
    private int code;
    private String message;
    CustomizeErrorCode(int code, String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getCode() {
        return code;
    }
}
