package com.msun.dao;

import com.msun.domain.ScheduleJob;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface JobDao {
    ScheduleJob getById(int id);
    List<ScheduleJob> getAll();
    Integer modify(ScheduleJob scheduleJob);
    Integer add(ScheduleJob scheduleJob);
    Integer deleteByID(int id);
}
