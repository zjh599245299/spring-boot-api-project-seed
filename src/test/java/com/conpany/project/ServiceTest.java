package com.conpany.project;

import com.company.project.model.SysAccount;
import com.company.project.service.SysAccountService;
import org.apache.tomcat.util.security.MD5Encoder;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by zhoujh on 2017/11/22
 */
public class ServiceTest extends Tester {

    @Autowired
    private SysAccountService sysAccountService;

    @Test
    public void testAdd() {
        SysAccount sysAccount = new SysAccount();
        sysAccount.setUsername("ceshi");
        sysAccount.setNickname("测试");
        sysAccount.setPassword("1111111");
        sysAccount.setSalt("abcdefg");
        sysAccount.setStatus(1);
        sysAccount.setCreateat(new Date());
        SysAccount sysAccount1 = sysAccount;
//        int result = sysAccountService.save(sysAccount);
        List<SysAccount> models = Lists.newArrayList();
        models.add(sysAccount);
        models.add(sysAccount1);
        int result = sysAccountService.save(models);
        System.out.println(result);
    }

}
