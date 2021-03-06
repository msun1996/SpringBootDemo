package com.msun.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaoxinguo on 2017/9/13.
 */
@RestController
@RequestMapping("/users")
@Api(value = "用户管理", description = "用户管理")
public class UserController {

    /**
     * 获取用户列表
     * @return
     */
    @ApiOperation(value = "查询用户列表")
    @GetMapping("/userList")
    public Map<String, Object> userList(){
//        List<User> users = userService.getById();
//        logger.info("users: {}", users);
        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("users",users);
        return map;
    }

}
