package com.msun.service.api;

import com.msun.dao.JobDao;
import com.msun.domain.ScheduleJob;
import com.msun.config.quartz.QuartzManager;
import com.msun.common.ServiceException;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JobAPIService {

    @Autowired
    private JobDao jobDao;

    @Autowired
    private Scheduler scheduler;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 获取所有定时任务
     * @return
     */
    public List<ScheduleJob> getAllJob() {
        return jobDao.getAll();
    }

    /**
     * 根据id获取定时任务
     * @param jobId
     * @return
     * @throws ServiceException
     */
    public ScheduleJob getById(int jobId) throws ServiceException {
        ScheduleJob scheduleJob = jobDao.getById(jobId);
        if (scheduleJob == null) {
            throw new ServiceException("ScheduleJob:" + jobId + " not found");
        }
        return scheduleJob;
    }

    /**
     * 修改定时任务
     * @param jobId
     * @param scheduleJob
     * @return
     * @throws ServiceException
     */
    @Transactional(rollbackFor = DataAccessException.class)
    public ScheduleJob modify(int jobId, ScheduleJob scheduleJob) throws ServiceException {

        if (jobDao.modify(scheduleJob) <= 0) {
            throw new ServiceException("Update product:" + jobId + "failed");
        }

        QuartzManager.updateScheduleJob(scheduler, scheduleJob);

        return scheduleJob;
    }

    /**
     * 添加定时任务
     * @param scheduleJob
     * @return
     * @throws ServiceException
     */
    @Transactional(rollbackFor = DataAccessException.class)
    public boolean add(ScheduleJob scheduleJob) throws ServiceException {
        Integer num = jobDao.add(scheduleJob);
        if (num <= 0) {
            throw new ServiceException("Add product failed");
        }
        QuartzManager.createScheduleJob(scheduler, scheduleJob);
        return true;
    }

    /**
     * 删除定时任务
     * @param jobId
     * @return
     * @throws ServiceException
     */
    @Transactional(rollbackFor = DataAccessException.class)
    public boolean deleteByID(int jobId) throws ServiceException {
        ScheduleJob scheduleJob = getById(jobId);

        Integer num = jobDao.deleteByID(jobId);
        if (num <= 0) {
            throw new ServiceException("Delete product:" + jobId + "failed");
        }

        QuartzManager.deleteJob(scheduler, scheduleJob);

        return true;
    }

    /**
     * 恢复定时任务
     * @param jobId
     * @return
     * @throws ServiceException
     */
    public boolean resume(int jobId) throws ServiceException {
        ScheduleJob scheduleJob = updateScheduleJobStatus(jobId, false);
        QuartzManager.resumeJob(scheduler, scheduleJob);
        return true;
    }

    /**
     * 暂停定时任务
     * @param jobId
     * @return
     * @throws ServiceException
     */
    public boolean pause(int jobId) throws ServiceException {
        ScheduleJob scheduleJob = updateScheduleJobStatus(jobId, true);
        QuartzManager.pauseJob(scheduler, scheduleJob);
        return true;
    }

    /**
     * 运行任务
     * @param jobId
     * @return
     * @throws ServiceException
     */
    public boolean run(int jobId) throws ServiceException {
        ScheduleJob scheduleJob = updateScheduleJobStatus(jobId, false);
        QuartzManager.run(scheduler, scheduleJob);
        return true;
    }


    /**
     * 更新任务，用于暂停恢复任务等
     * @param jobId
     * @param isPause
     * @return
     * @throws ServiceException
     */
    private ScheduleJob updateScheduleJobStatus(int jobId, Boolean isPause) throws ServiceException {
        ScheduleJob scheduleJob = getById(jobId);
        scheduleJob.setPause(isPause);
        modify(scheduleJob.getId(), scheduleJob);
        return scheduleJob;
    }

}
