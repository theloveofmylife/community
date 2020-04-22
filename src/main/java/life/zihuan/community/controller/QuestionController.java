package life.zihuan.community.controller;

import life.zihuan.community.dto.QuestionDTO;
import life.zihuan.community.mapper.QuestionMapper;
import life.zihuan.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/question/{id}")
    public  String question(@PathVariable("id") int id,
                            Model model){
        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
