package com.msun.controller;

import com.msun.domain.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping()
    @ApiOperation(value = "添加用户服务")
    public String create(@ApiParam(value = "用户信息") @RequestBody User user) {
        return null;
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable("id") int id) {
    }

    @PutMapping("/{id:\\d+}")
    public String update(@PathVariable("id") int id) {
        return null;
    }

    @GetMapping("/{id:\\d+}")
    public User getInfo(@PathVariable("id") int id) {
        return null;
    }

    @GetMapping()
    public List<User> list() {
        List<User> users = new ArrayList<>();
        return users;
    }
}