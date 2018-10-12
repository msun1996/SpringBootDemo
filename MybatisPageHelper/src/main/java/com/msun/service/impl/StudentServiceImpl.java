package com.msun.service.impl;

import com.msun.dao.StudentDao;
import com.msun.domain.Student;
import com.msun.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;

    @Override
    public List<Student> findAllStudent() {
        return studentDao.findAllStudent();
    }
}
