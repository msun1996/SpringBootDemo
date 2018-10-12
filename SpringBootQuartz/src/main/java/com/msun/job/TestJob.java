package com.msun.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 执行的任务（定时任务，写入方法）
 */
public class TestJob implements Job {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        JobDataMap dataMap=jobExecutionContext.getJobDetail().getJobDataMap();
        logger.info("Test job is executing..." );
        // Do what you want here
        try{
            Thread.sleep(1000);
        }catch(Exception e){
            System.exit(0);//退出程序
        }
    }
}