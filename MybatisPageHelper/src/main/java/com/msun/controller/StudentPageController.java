package com.msun.controller;

import com.github.pagehelper.PageInfo;
import com.msun.domain.Student;
import com.msun.service.StudentPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/page")
public class StudentPageController {

    @Autowired
    private StudentPageService studentPageService;

    @RequestMapping("/pageHelper")
    @ResponseBody
    public PageInfo<Student> getByPageHelper(Integer pageNum, Integer pageSize) {
        return studentPageService.getByPageHelper(pageNum, pageSize);
}

    // 按照给定页码和每页个数查询（返回结果只包含需要查询的数据）
    @RequestMapping("/rowBounds")
    @ResponseBody
    public PageInfo<Student> getByBounds(Integer pageNum, Integer pageSize) {
        return studentPageService.getByRowBounds(pageNum,pageSize);
    }
    // 按照给定页码和每页个数查询（返回结果包含所有数据结果集）
    @RequestMapping("/interfaceArgs")
    @ResponseBody
    public PageInfo<Student> getByInterfaceArgs(Integer pageNum, Integer pageSize) {
        return studentPageService.getByInterfaceArgs(pageNum, pageSize);
    }

    @RequestMapping("/modalArgs")
    @ResponseBody
    public PageInfo<Student> getByModalArgs(Student student) {
        return studentPageService.getByModalArgs(student);
    }
}
