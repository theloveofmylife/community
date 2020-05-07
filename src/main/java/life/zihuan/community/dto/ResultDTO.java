package life.zihuan.community.dto;

import life.zihuan.community.exception.CustomizeErrorCode;
import life.zihuan.community.exception.CustomizeException;

import java.util.List;

public class ResultDTO<T> {
    private int code;
    private String message;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResultDTO(CustomizeErrorCode customizeErrorCode) {
        this.code = customizeErrorCode.getCode();
        this.message = customizeErrorCode.getMessage();
    }
    public static <T> ResultDTO okOf(T t){
        ResultDTO resultDTO = new ResultDTO<T>();
        resultDTO.setCode(200);
        resultDTO.setMessage("成功");
        resultDTO.setData(t);
        return resultDTO;
    }

    public static ResultDTO okOf(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("成功");
        return resultDTO;
    }

    public static ResultDTO error(CustomizeException ex) {
        return new ResultDTO(ex.getCode(),ex.getMessage());
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultDTO(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultDTO() {
    }
}
