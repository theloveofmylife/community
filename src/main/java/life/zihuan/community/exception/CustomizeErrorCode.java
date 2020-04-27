package life.zihuan.community.exception;

public class CustomizeErrorCode implements ICustomizeErrorCode {
   static public String questionNotFound(){
        return "你找的问题已不在，请换一个！！";
    }
    private String message;
    public CustomizeErrorCode(String message){
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
