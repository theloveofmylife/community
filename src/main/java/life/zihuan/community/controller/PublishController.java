package life.zihuan.community.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import life.zihuan.community.dto.QuestionDTO;
import life.zihuan.community.mapper.QuestionMapper;
import life.zihuan.community.model.Question;
import life.zihuan.community.model.User;
import life.zihuan.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    QuestionService questionService;
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "id",required = false,defaultValue = "0") int id,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model
    ){
        model.addAttribute("id",id);
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if (title == null || title == ""){
            model.addAttribute("error", "问题标题不能为空");
            return "publish";
        }
        if (title == null || description == ""){
            model.addAttribute("error", "问题内容不能为空");
            return "publish";
        }
        if (title == null || tag == ""){
            model.addAttribute("error", "问题标签不能为空");
            return "publish";
        }

        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:index";
    }
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") int id,
                       Model model){
        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute("title",questionDTO.getTitle());
        model.addAttribute("description",questionDTO.getDescription());
        model.addAttribute("tag",questionDTO.getTag());
        model.addAttribute("id",questionDTO.getId());
        return "publish";
    }
}
