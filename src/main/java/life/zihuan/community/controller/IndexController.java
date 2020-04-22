package life.zihuan.community.controller;

import life.zihuan.community.dto.PaginationDTO;
import life.zihuan.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private QuestionService questionService;
    @GetMapping(value={"/","index"})
    public String index(HttpServletRequest request,Model model,
                        @RequestParam(name = "page",defaultValue = "1") int page,
                        @RequestParam(name = "pageSize",defaultValue = "5") int size){
        PaginationDTO paginationDTO = questionService.list(page,size);
        model.addAttribute("paginationDTO", paginationDTO);
        return "index";
    }
}
