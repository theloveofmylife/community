package life.zihuan.community.service;

import life.zihuan.community.dto.PaginationDTO;
import life.zihuan.community.dto.QuestionDTO;
import life.zihuan.community.mapper.QuestionMapper;
import life.zihuan.community.mapper.UserMapper;
import life.zihuan.community.model.Question;
import life.zihuan.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    public PaginationDTO list(int page, int size) {
        int offset = size * (page - 1);
        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>(questions.size());
        for (Question question:questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setQuestionDTOList(questionDTOList);
        int totalCount = questionMapper.count();
        paginationDTO.setPage(page);
        paginationDTO.setPagination(totalCount,size);
        return paginationDTO;
    }

    public PaginationDTO list(int id, int page, int size) {
        int offset = size * (page - 1);
        List<Question> questions = questionMapper.listByUserId(id,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>(questions.size());
        User user = userMapper.findById(id);
        for (Question question:questions){
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setQuestionDTOList(questionDTOList);
        int totalCount = questionMapper.countByUserId(id);
        paginationDTO.setPage(page);
        paginationDTO.setPagination(totalCount,size);
        return paginationDTO;
    }

    public QuestionDTO getById(int id) {
        Question question = questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId()==0){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }else{
            question.setGmtModified(question.getGmtCreate());
            questionMapper.update(question);
        }
    }
}
