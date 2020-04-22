package life.zihuan.community.service;

import life.zihuan.community.dto.GithubUser;
import life.zihuan.community.mapper.UserMapper;
import life.zihuan.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public User insertOrUpdate(User user, GithubUser githubUser) {
        User dbUser = userMapper.findByAccountId(user.getAccountId());
        if(dbUser == null){
            dbUser = new User();
            dbUser.setAccountId(user.getAccountId());
            dbUser.setName(githubUser.getName());
            dbUser.setToken(UUID.randomUUID().toString());
            dbUser.setGmtCreate(System.currentTimeMillis());
            dbUser.setGmtModified(user.getGmtCreate());
            dbUser.setAvatarUrl(githubUser.getAvatar_url());
            userMapper.insert(dbUser);
        }else {
            dbUser.setName(githubUser.getName());
            dbUser.setToken(UUID.randomUUID().toString());
            dbUser.setGmtModified(user.getGmtCreate());
            dbUser.setAvatarUrl(githubUser.getAvatar_url());
            userMapper.update(dbUser);
        }
        return dbUser;
    }
}
