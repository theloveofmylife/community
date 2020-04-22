package life.zihuan.community.controller;

import life.zihuan.community.dto.AccessTokenDTO;
import life.zihuan.community.dto.GithubUser;
import life.zihuan.community.mapper.UserMapper;
import life.zihuan.community.model.User;
import life.zihuan.community.provider.GithubProvider;
import life.zihuan.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.client.uri}")
    private String clientUri;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state,
                           HttpServletRequest request, HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setRedirect_uri(clientUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        System.out.println(githubUser.getId());
        System.out.println(githubUser.getName());
        if (githubUser !=null){
            User user = new User();
            user.setAccountId(String.valueOf((githubUser.getId())));
            user = userService.insertOrUpdate(user,githubUser);
            System.out.println(user.getToken());
            response.addCookie(new Cookie("Token", user.getToken()));
            request.getSession().setAttribute("user",user);
            return "redirect:index";
        }else {
            //登陆失败
            return "redirect:index";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("Token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:index";
    }
}
