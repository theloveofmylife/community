package life.zihuan.community.advice;

import life.zihuan.community.dto.ResultDTO;
import life.zihuan.community.exception.CustomizeErrorCode;
import life.zihuan.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    Object handleControllerException(HttpServletRequest request, Throwable ex, Model model) {
        String contentType = request.getContentType();
        String contentTypePRE = "application/json";
        if (contentTypePRE.equals(contentType)){
            //返回JSON
            return ResultDTO.error((CustomizeException) ex);
        }else {
            //错误页面跳转
            if (ex instanceof CustomizeException){
                model.addAttribute("message", ex.getMessage());
            } else {
                model.addAttribute("message", CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            HttpStatus status = getStatus(request);
            model.addAttribute("status",status.value());
            return new ModelAndView("error");
        }
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
