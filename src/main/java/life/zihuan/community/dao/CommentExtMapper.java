package life.zihuan.community.dao;

import life.zihuan.community.model.Comment;

import java.util.List;

public interface CommentExtMapper {

    void incCommentCount(Comment comment);
}