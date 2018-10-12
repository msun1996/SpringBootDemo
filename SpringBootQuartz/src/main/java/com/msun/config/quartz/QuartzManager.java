package com.msun.config.quartz;

import com.msun.domain.ScheduleJob;
import com.msun.common.ServiceException;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuartzManager {
    private final static Logger logger = LoggerFactory.getLogger(QuartzManager.class);

    /**
     * 获取 Trigger Key
     * @param scheduleJob
     * @return
     */
    public static TriggerKey getTriggerKey(ScheduleJob scheduleJob) {
        return TriggerKey.triggerKey(scheduleJob.getTriggerName(), scheduleJob.getTriggerGroup());
    }

    /**
     * 获取 Job Key
     * @param scheduleJob
     * @return
     */
    public static JobKey getJobKey(ScheduleJob scheduleJob) {
        return JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
    }

    /**
     * 获取 Cron Trigger
     * @param scheduler
     * @param scheduleJob
     * @return
     * @throws ServiceException
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, ScheduleJob scheduleJob) throws ServiceException {
        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(scheduleJob));
        } catch (SchedulerException e) {
            throw new ServiceException("Get Cron trigger failed", e);
        }
    }

    /**
     * 创建任务
     * @param scheduler
     * @param scheduleJob
     * @throws ServiceException
     */
    public static void createScheduleJob(Scheduler scheduler, ScheduleJob scheduleJob) throws ServiceException {

        validateCronExpression(scheduleJob);

        try {
            // Get Job class from ScheduleJob entity
            Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(scheduleJob.getClassName()).newInstance().getClass();

            // Create JobDetail entity
            JobDetail jobDetail = JobBuilder.newJob(jobClass)
                    .withIdentity(scheduleJob.getJobName(), scheduleJob.getJobGroup())
                    .withDescription(scheduleJob.getDescription())
                    .build();

            // Create CronScheduleBuilder entity
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())
                    .withMisfireHandlingInstructionDoNothing();

            // Create a new CronTrigger by Cron Expression
            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(scheduleJob.getTriggerName(), scheduleJob.getTriggerGroup())
                    .withDescription(scheduleJob.getDescription())
                    .withSchedule(scheduleBuilder)
                    .startNow()
                    .build();

            scheduler.scheduleJob(jobDetail, cronTrigger);

            logger.info("Create schedule job {}-{} success", scheduleJob.getJobGroup(), scheduleJob.getJobName());

            if (scheduleJob.getPause()) {
                pauseJob(scheduler, scheduleJob);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Execute schedule job failed");
            throw new ServiceException("Execute schedule job failed", e);
        }
    }

    /**
     * 更新任务
     * @param scheduler
     * @param scheduleJob
     * @throws ServiceException
     */
    public static void updateScheduleJob(Scheduler scheduler, ScheduleJob scheduleJob) throws ServiceException {

        validateCronExpression(scheduleJob);

        try {

            TriggerKey triggerKey = getTriggerKey(scheduleJob);

            // Create CronScheduleBuilder entity
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())
                    .withMisfireHandlingInstructionDoNothing();

            CronTrigger cronTrigger = getCronTrigger(scheduler, scheduleJob);

            // Create new cron trigger
            cronTrigger = cronTrigger.getTriggerBuilder()
                    .withIdentity(triggerKey)
                    .withDescription(scheduleJob.getDescription())
                    .withSchedule(cronScheduleBuilder).build();

            scheduler.rescheduleJob(triggerKey, cronTrigger);

            logger.info("Update schedule job {}-{} success", scheduleJob.getJobGroup(), scheduleJob.getJobName());

            if (scheduleJob.getPause()) {
                pauseJob(scheduler, scheduleJob);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
            logger.error("Update schedule job failed");
            throw new ServiceException("Update schedule job failed", e);
        }
    }

    /**
     * 执行任务
     * @param scheduler
     * @param scheduleJob
     * @throws ServiceException
     */
    public static void run(Scheduler scheduler, ScheduleJob scheduleJob) throws ServiceException {
        try {
            scheduler.triggerJob(getJobKey(scheduleJob));
            logger.info("Run schedule job {}-{} success", scheduleJob.getJobGroup(), scheduleJob.getJobName());
        } catch (SchedulerException e) {
            e.printStackTrace();
            logger.error("Run schedule job failed");
            throw new ServiceException("Run schedule job failed", e);
        }
    }

    /**
     * 暂停任务
     * @param scheduler
     * @param scheduleJob
     * @throws ServiceException
     */
    public static void pauseJob(Scheduler scheduler, ScheduleJob scheduleJob) throws ServiceException {
        try {
            scheduler.pauseJob(getJobKey(scheduleJob));
            logger.info("Pause schedule job {}-{} success", scheduleJob.getJobGroup(), scheduleJob.getJobName());
        } catch (SchedulerException e) {
            e.printStackTrace();
            logger.error("Pause schedule job failed");
            throw new ServiceException("Pause job failed", e);
        }
    }

    /**
     * 继续执行任务
     * @param scheduler
     * @param scheduleJob
     * @throws ServiceException
     */
    public static void resumeJob(Scheduler scheduler, ScheduleJob scheduleJob) throws ServiceException {
        try {
            scheduler.resumeJob(getJobKey(scheduleJob));
            logger.info("Resume schedule job {}-{} success", scheduleJob.getJobGroup(), scheduleJob.getJobName());
        } catch (SchedulerException e) {
            e.printStackTrace();
            logger.error("Resume schedule job failed");
            throw new ServiceException("Resume job failed", e);
        }
    }

    /**
     * 删除任务
     * @param scheduler
     * @param scheduleJob
     * @throws ServiceException
     */
    public static void deleteJob(Scheduler scheduler, ScheduleJob scheduleJob) throws ServiceException {
        try {
            scheduler.deleteJob(getJobKey(scheduleJob));
            logger.info("Delete schedule job {}-{} success", scheduleJob.getJobGroup(), scheduleJob.getJobName());
        } catch (SchedulerException e) {
            e.printStackTrace();
            logger.error("Delete schedule job failed");
            throw new ServiceException("Delete job failed", e);
        }
    }

    /**
     * 校验cron表达式
     * @param scheduleJob
     * @throws ServiceException
     */
    public static void validateCronExpression(ScheduleJob scheduleJob) throws ServiceException {
        if (!CronExpression.isValidExpression(scheduleJob.getCronExpression())) {
            throw new ServiceException(String.format("Job %s expression %s is not correct!", scheduleJob.getClassName(), scheduleJob.getCronExpression()));
        }
    }
}
