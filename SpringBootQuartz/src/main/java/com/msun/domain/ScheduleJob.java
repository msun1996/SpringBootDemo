package com.msun.domain;

import lombok.Data;
import java.util.Date;

@Data
public class ScheduleJob {
    private int id;
    private String className; // 执行类
    private String cronExpression; // 执行时间
    private String jobName; // 任务名称
    private String jobGroup; // 任务分组
    private String triggerName;
    private String triggerGroup;
    private Boolean pause; // 暂停
    private String description;
    private Date createTime;
    private Date lastUpdateTime;

}
