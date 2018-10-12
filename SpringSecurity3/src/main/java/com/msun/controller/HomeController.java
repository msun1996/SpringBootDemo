package com.msun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String index(){
        return "home";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/admin")
    @ResponseBody
    public String hello(){
        return "hello admin";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public String getList(){
        return "hello getList";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public String save(){
        return "hello save";
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    @ResponseBody
    public String update(){
        return "hello update";
    }
}
