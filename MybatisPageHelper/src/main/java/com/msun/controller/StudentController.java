package com.msun.controller;

import com.msun.domain.Student;
import com.msun.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 返回所有学生信息
     */
    @RequestMapping("/")
    @ResponseBody
    public List<Student> findAllUser() {
        return studentService.findAllStudent();
    }
}
