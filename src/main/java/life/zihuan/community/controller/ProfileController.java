package life.zihuan.community.controller;

import life.zihuan.community.dto.PaginationDTO;
import life.zihuan.community.model.User;
import life.zihuan.community.service.NotificationService;
import life.zihuan.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action, Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") int page,
                          @RequestParam(name = "pageSize",defaultValue = "5") int size){
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return "redirect:/";
        }
        if ("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            PaginationDTO paginationDTO = questionService.list(user.getId(),page,size);
            model.addAttribute("paginationDTO", paginationDTO);
        }else if ("replies".equals(action)){
            PaginationDTO paginationDTO = notificationService.list(user.getId(),page,size);
            model.addAttribute("paginationDTO", paginationDTO);
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }else if("homePage".equals(action)){
            model.addAttribute("section","homePage");
            model.addAttribute("sectionName","个人主页");
        }
        return "profile";
    }
}
