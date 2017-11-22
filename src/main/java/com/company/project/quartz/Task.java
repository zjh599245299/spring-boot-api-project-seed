package com.company.project.task;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by zhoujh on 2017/11/22
 */
public class Task implements Job {
    private Logger logger = Logger.getLogger(Task.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("【" + context.getJobDetail().getKey().getName() + "】:执行任务");
    }
}
