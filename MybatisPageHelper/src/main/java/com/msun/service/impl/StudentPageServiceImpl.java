package com.msun.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.msun.dao.StudentDao;
import com.msun.domain.Student;
import com.msun.service.StudentPageService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentPageServiceImpl implements StudentPageService {

    @Autowired
    private StudentDao studentDao;

    @Override
    public PageInfo<Student> getByPageHelper(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(studentDao.getByPageHelper());
    }

    @Override
    public PageInfo<Student> getByRowBounds(Integer pageNum, Integer pageSize) {
        return new PageInfo<>(studentDao.getByRowBounds(new RowBounds(pageNum, pageSize)));
    }

    @Override
    public PageInfo<Student> getByInterfaceArgs(Integer pageNum, Integer pageSize) {
        return new PageInfo<>(studentDao.getByInterfaceArgs(pageNum, pageSize));
    }

    @Override
    public PageInfo<Student> getByModalArgs(Student student) {
        return new PageInfo<>(studentDao.getByModalArgs(student));
    }
}
