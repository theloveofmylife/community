package life.zihuan.community.service;

import life.zihuan.community.dao.UserMapper;
import life.zihuan.community.dto.GithubUser;
import life.zihuan.community.model.User;
import life.zihuan.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public User insertOrUpdate(User user, GithubUser githubUser) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        //User dbUser = userMapper.findByAccountId(user.getAccountId());
        User dbUser;
        if(users.size() == 0){
            dbUser = new User();
            dbUser.setAccountId(user.getAccountId());
            dbUser.setName(githubUser.getName());
            dbUser.setToken(UUID.randomUUID().toString());
            dbUser.setGmtCreate(System.currentTimeMillis());
            dbUser.setGmtModified(user.getGmtCreate());
            dbUser.setAvatarUrl(githubUser.getAvatar_url());
            userMapper.insert(dbUser);
        }else {
            dbUser = users.get(0);
            User updateUser = new User();
            updateUser.setName(githubUser.getName());
            updateUser.setToken(UUID.randomUUID().toString());
            updateUser.setGmtModified(user.getGmtCreate());
            updateUser.setAvatarUrl(githubUser.getAvatar_url());
            UserExample updateUserExample = new UserExample();
            updateUserExample.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser,updateUserExample);
            dbUser.setToken(updateUser.getToken());
            //userMapper.update(dbUser);
        }
        return dbUser;
    }
}
