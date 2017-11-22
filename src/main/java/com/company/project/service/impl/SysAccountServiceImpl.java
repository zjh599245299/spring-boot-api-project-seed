package com.company.project.service.impl;

import com.company.project.dao.SysAccountMapper;
import com.company.project.model.SysAccount;
import com.company.project.service.SysAccountService;
import com.company.project.core.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2017/11/22.
 */
@Service
@Transactional
public class SysAccountServiceImpl extends BaseServiceImpl<SysAccount> implements SysAccountService {
    @Resource
    private SysAccountMapper sysAccountMapper;

}
