package com.msun.controller;

import com.msun.dao.UserDao;
import com.msun.domain.User;
import com.msun.exception.UsernameIsExitedException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaoxinguo on 2018/06/05.
 */
@RestController
@RequestMapping("/users")
@Api(value = "注册管理", description = "注册管理")
public class RegisterController {

    @Autowired
    protected UserDao userDao;

    /**
     * 注册用户 默认开启白名单
     * @param user
     */
    @ApiOperation(value = "注册用户")
    @PostMapping("/sighup")
    public User sighup(@RequestBody User user) {
        User bizUser = userDao.getByUserName(user.getUsername());
        if(null != bizUser){
            throw new UsernameIsExitedException("用户已经存在");
        }
        /*user.setPassword(DigestUtils.md5DigestAsHex((user.getPassword()).getBytes()));*/
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userDao.save(user);
        return user;
    }

}
