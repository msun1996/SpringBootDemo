package com.msun.service;

import com.github.pagehelper.PageInfo;
import com.msun.domain.Student;

public interface StudentPageService {
    PageInfo<Student> getByPageHelper(Integer pageNum, Integer pageSize);
    PageInfo<Student> getByRowBounds(Integer pageNum, Integer pageSize);
    PageInfo<Student> getByInterfaceArgs(Integer pageNum, Integer pageSize);
    PageInfo<Student> getByModalArgs(Student student);
}
