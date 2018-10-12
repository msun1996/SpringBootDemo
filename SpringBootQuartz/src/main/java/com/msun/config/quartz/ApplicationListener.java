package com.msun.config.quartz;

import com.msun.common.ServiceException;
import com.msun.domain.ScheduleJob;
import com.msun.service.api.JobAPIService;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationListener implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JobAPIService jobAPIService;

    @Autowired
    private Scheduler scheduler;

    @Override
    public void run(String... args) throws Exception {

        // Run schedule job when Application startup
        List<ScheduleJob> scheduleJobList = jobAPIService.getAllJob();
        for (ScheduleJob scheduleJob : scheduleJobList) {
            try {
                CronTrigger cronTrigger = QuartzManager.getCronTrigger(scheduler, scheduleJob);
                if (cronTrigger == null) {
                    QuartzManager.createScheduleJob(scheduler, scheduleJob);
                } else {
                    QuartzManager.updateScheduleJob(scheduler, scheduleJob);
                }
                logger.info("Startup {}-{} success", scheduleJob.getJobGroup(), scheduleJob.getJobName());
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
    }
}
