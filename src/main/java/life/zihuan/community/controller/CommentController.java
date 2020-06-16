package life.zihuan.community.controller;

import life.zihuan.community.dao.CommentMapper;
import life.zihuan.community.dto.CommentCreateDTO;
import life.zihuan.community.dto.CommentDTO;
import life.zihuan.community.dto.ResultDTO;
import life.zihuan.community.enums.CommentTypeEnum;
import life.zihuan.community.exception.CustomizeErrorCode;
import life.zihuan.community.model.Comment;
import life.zihuan.community.model.User;
import life.zihuan.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    CommentService commentService;
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    @ResponseBody
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return new ResultDTO(CustomizeErrorCode.NO_LOGIN);
        }
        if (commentCreateDTO == null|| StringUtils.isBlank(commentCreateDTO.getContent())){
            return new ResultDTO(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(1L);
        comment.setLikeCount(0L);
        commentService.insert(comment,user);
        return ResultDTO.okOf();
    }

    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO comments(@PathVariable(name = "id") Long id){
        List<CommentDTO> commentDTOList = commentService.listByParentIdAndType(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOList);
    }
}