package life.zihuan.community.dto;

import life.zihuan.community.exception.CustomizeErrorCode;
import life.zihuan.community.exception.CustomizeException;

public class ResultDTO {
    private int code;
    private String message;

    public ResultDTO(CustomizeErrorCode customizeErrorCode) {
        this.code = customizeErrorCode.getCode();
        this.message = customizeErrorCode.getMessage();
    }
    public static ResultDTO okof(){
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
