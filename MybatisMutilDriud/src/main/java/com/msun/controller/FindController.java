package com.msun.controller;

import com.msun.service.FindAllServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class FindController {

    @Autowired
    private FindAllServer findAllServer;

    @RequestMapping("/user")
    @ResponseBody
    public List user() {
        return findAllServer.FindAllUser();
    }

    @RequestMapping("/city")
    @ResponseBody
    public List city() {
        return findAllServer.FindAllCity();
    }
}
