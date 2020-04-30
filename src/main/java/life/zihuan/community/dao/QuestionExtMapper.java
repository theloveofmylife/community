package life.zihuan.community.dao;

import life.zihuan.community.model.*;

import java.util.*;

public interface QuestionExtMapper {

    void incViewCount(Question question);

    void incCommentCount(Question question);

    List<Question> selectRelated(Question question);
}