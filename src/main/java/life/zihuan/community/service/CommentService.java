package life.zihuan.community.service;

import life.zihuan.community.dao.CommentMapper;
import life.zihuan.community.dao.QuestionExtMapper;
import life.zihuan.community.dao.QuestionMapper;
import life.zihuan.community.dao.UserMapper;
import life.zihuan.community.dto.CommentDTO;
import life.zihuan.community.enums.CommentTypeEnum;
import life.zihuan.community.exception.CustomizeErrorCode;
import life.zihuan.community.exception.CustomizeException;
import life.zihuan.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;
    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }else {
            //回复问题
            Question dbQuestion = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (dbQuestion == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            dbQuestion.setCommentCount(1);
            questionExtMapper.incCommentCount(dbQuestion);
        }
    }

    public List<CommentDTO> listByQuestionId(Long id) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(CommentTypeEnum.QUESTION.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> commentList = commentMapper.selectByExample(commentExample);
        if (commentList.size() == 0){
            return null;
        }
        //获取去重的评论人ID
        Set<Long> commentatorList =  commentList.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIdList = new ArrayList<>(commentatorList);

        //获取评论人并转换为Map
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIdList);
        List<User> userList = userMapper.selectByExample(userExample);
        Map<Long,User> IdUserMap = userList.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //转换 comment 为 commentDTO
        List<CommentDTO> commentDTOList = commentList.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            User user = IdUserMap.get(comment.getCommentator());
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(user);
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOList;
    }
}
