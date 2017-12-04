package com.company.project.service.impl;

import com.company.project.dao.AccountMapper;
import com.company.project.model.Account;
import com.company.project.service.AccountService;
import com.company.project.core.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
* 业务实现类
*
* @author Zhoujh
* @date 2017/12/01
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class AccountServiceImpl extends BaseServiceImpl<Account> implements AccountService {
    @Resource
    private AccountMapper sysAccountMapper;

}
