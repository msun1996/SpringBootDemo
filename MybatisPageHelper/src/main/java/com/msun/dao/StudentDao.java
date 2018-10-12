package com.msun.dao;

import com.msun.domain.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface StudentDao {

    List<Student> findAllStudent();

    List<Student> getByPageHelper();

    List<Student> getByRowBounds(RowBounds rowBounds);

    List<Student> getByInterfaceArgs(Integer pageNum, Integer pageSize);

    List<Student> getByModalArgs(Student product);
}
