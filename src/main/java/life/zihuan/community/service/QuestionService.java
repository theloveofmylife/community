package life.zihuan.community.service;

import life.zihuan.community.dao.QuestionExtMapper;
import life.zihuan.community.dto.PaginationDTO;
import life.zihuan.community.dto.QuestionDTO;
import life.zihuan.community.dao.UserMapper;
import life.zihuan.community.dao.QuestionMapper;
import life.zihuan.community.exception.CustomizeErrorCode;
import life.zihuan.community.exception.CustomizeException;
import life.zihuan.community.model.Question;
import life.zihuan.community.model.QuestionExample;
import life.zihuan.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    QuestionExtMapper questionExtMapper;

    public PaginationDTO list(int page, int size) {
        int offset = size * (page - 1);
        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc");
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample,new RowBounds(offset,size));
        //List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>(questions.size());
        for (Question question:questions){
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            //User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        paginationDTO.setData(questionDTOList);
        int totalCount = (int) questionMapper.countByExample(new QuestionExample());
        // totalCount = questionMapper.count();
        paginationDTO.setPage(page);
        paginationDTO.setPagination(totalCount,size);
        return paginationDTO;
    }

    public PaginationDTO list(long id, int page, int size) {
        int offset = size * (page - 1);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(id);
        questionExample.setOrderByClause("gmt_create desc");
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample,new RowBounds(offset,size));
        //List<Question> questions = questionMapper.listByUserId(id,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>(questions.size());
        User user = userMapper.selectByPrimaryKey(id);
        //User user = userMapper.findById(id);
        for (Question question:questions){
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        paginationDTO.setData(questionDTOList);
        questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(id);
        int totalCount = (int) questionMapper.countByExample(questionExample);
        //int totalCount = questionMapper.countByUserId(id);
        paginationDTO.setPage(page);
        paginationDTO.setPagination(totalCount,size);
        return paginationDTO;
    }

    public QuestionDTO getById(long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        //Question question = questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId()==0){
            question.setId(null);
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setCommentCount(0);
            question.setLikeCount(0);
            questionMapper.insert(question);
            //questionMapper.create(question);
        }else{
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion,questionExample);
            if (updated != 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            //questionMapper.update(question);
        }
    }

    public void incViewCount(long id) {
        Question record = new Question();
        record.setId(id);
        record.setViewCount(1);
        questionExtMapper.incViewCount(record);

    }

    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        if (StringUtils.isBlank(queryDTO.getTag())){
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryDTO.getTag(),",");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexpTag);
        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q->{
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q,questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;
    }
}
