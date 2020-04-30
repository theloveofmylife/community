package life.zihuan.community.controller;

import life.zihuan.community.dto.CommentDTO;
import life.zihuan.community.dto.QuestionDTO;
import life.zihuan.community.service.CommentService;
import life.zihuan.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;
    @GetMapping("/question/{id}")
    public  String question(@PathVariable("id") Long id,
                            Model model){
        QuestionDTO questionDTO = questionService.getById(id);

        List<CommentDTO> commentDTOList = commentService.listByQuestionId(id);
        //累加阅读数
        questionService.incViewCount(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",commentDTOList);
        return "question";
    }
}
