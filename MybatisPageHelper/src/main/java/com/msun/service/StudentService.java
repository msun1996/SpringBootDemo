package com.msun.service;

import com.msun.domain.Student;

import java.util.List;

public interface StudentService {
    /**
     * 查询所有学生
     */
    List<Student> findAllStudent();


}
