package life.zihuan.community.controller;

import life.zihuan.community.mapper.QuestionMapper;
import life.zihuan.community.mapper.UserMapper;
import life.zihuan.community.model.Question;
import life.zihuan.community.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model
    ){
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

        Cookie[] cookies = request.getCookies();
        User user = null;
        if (cookies != null && cookies.length!=0){
        for (Cookie cookie:cookies){
            if(cookie.getName().equals("Token")){
                String Token= cookie.getValue();
                user = userMapper.findByToken(Token);
                if(user != null)
                    request.getSession().setAttribute("user",user);
                break;
            }
        }}
        if (user == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setCreator(user.getId());
        questionMapper.create(question);
        return "redirect:index";
    }
}
