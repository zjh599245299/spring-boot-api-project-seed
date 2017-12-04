package com.company.project.web;

import com.company.project.model.Account;
import com.company.project.service.AccountService;
import com.company.project.service.impl.AccountServiceImpl;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.annotation.Resource;

/**
 * @author zhoujh on 2017/12/1
 */
public class AccountTask implements Job {
    @Resource
    private AccountService service;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Account account = service.findById(1);
        System.out.println(account);
    }
}
