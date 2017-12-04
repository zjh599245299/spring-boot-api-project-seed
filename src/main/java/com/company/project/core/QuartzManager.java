package com.company.project.core;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 定时任务管理类
 *
 * @author zhoujh
 */
public class QuartzManager {
    private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";
    private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";

    private static void quartzRemoveJob(TriggerKey triggerKey, JobKey jobKey) {
        try {
            Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
            // 停止触发器
            sched.pauseTrigger(triggerKey);
            // 移除触发器
            sched.unscheduleJob(triggerKey);
            // 删除任务
            sched.deleteJob(jobKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
     *
     * @param jobName 任务名
     * @param cls     任务类
     * @param time    触发时间设置，参考quartz说明文档
     */
    public static void addJob(String jobName, Class cls, String time) {
        try {
            Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
            // 任务名，任务组，任务执行类
            JobDetail jobDetail = JobBuilder.newJob(cls).withIdentity(jobName, JOB_GROUP_NAME).build();
            // 定时规则
            CronScheduleBuilder cron = CronScheduleBuilder.cronSchedule(time);
            // 触发器名,触发器组
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, TRIGGER_GROUP_NAME).withSchedule(cron).build();
            sched.scheduleJob(jobDetail, trigger);
            // 启动
            if (!sched.isShutdown()) {
                sched.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加一个定时任务
     *
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param jobClass         任务
     * @param time             时间设置，参考quartz说明文档
     */
    public static void addJob(String jobName, String jobGroupName,
                              String triggerName, String triggerGroupName, Class jobClass,
                              String time) {
        try {
            Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
            // 任务名，任务组，任务执行类
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
            // 定时规则
            CronScheduleBuilder cron = CronScheduleBuilder.cronSchedule(time);
            // 触发器
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerName, triggerGroupName)
                    .withSchedule(cron).build();
            sched.scheduleJob(jobDetail, trigger);
            // 启动
            if (!sched.isShutdown()) {
                sched.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
     *
     * @param jobName 触发器名称（默认与job名称一致）
     * @param time    新的触发时间
     */
    public static void modifyJobTime(String jobName, String time) {
        try {
            Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
            CronTrigger trigger = (CronTrigger) sched.getTrigger(TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME));
            if (trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(time)) {
                JobDetail jobDetail = sched.getJobDetail(JobKey.jobKey(jobName, JOB_GROUP_NAME));
                Class objJobClass = jobDetail.getJobClass();
                removeJob(jobName);
                addJob(jobName, objJobClass, time);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改一个任务的触发时间
     *
     * @param triggerName      触发器名称
     * @param triggerGroupName 触发器组名
     * @param time             触发时间
     */
    public static void modifyJobTime(String triggerName,
                                     String triggerGroupName, String time) {
        try {
            Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
            CronTrigger trigger = (CronTrigger) sched.getTrigger(TriggerKey.triggerKey(triggerName, triggerGroupName));
            if (trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(time)) {
                // 重新设置触发时间
                CronScheduleBuilder cron = CronScheduleBuilder.cronSchedule(time);
                trigger.getTriggerBuilder().withSchedule(cron);
                // 重启触发器
                sched.resumeTrigger(TriggerKey.triggerKey(triggerName, triggerGroupName));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
     *
     * @param jobName 任务名
     */
    public static void removeJob(String jobName) {
        quartzRemoveJob(TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME), JobKey.jobKey(jobName, JOB_GROUP_NAME));
    }

    /**
     * 移除一个任务
     *
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     */
    public static void removeJob(String jobName, String jobGroupName,
                                 String triggerName, String triggerGroupName) {
        quartzRemoveJob(TriggerKey.triggerKey(triggerName, triggerGroupName), JobKey.jobKey(jobName, jobGroupName));
    }

    /**
     * 启动所有定时任务
     */
    public static void startJobs() {
        try {
            Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
            sched.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭所有定时任务
     */
    public static void shutdownJobs() {
        try {
            Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
            if (!sched.isShutdown()) {
                sched.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}  