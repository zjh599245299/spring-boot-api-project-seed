package com.company.project.web;

import com.company.project.core.QuartzManager;
import com.company.project.core.Result;
import com.company.project.core.ResultCode;
import com.company.project.core.ResultGenerator;
import com.company.project.model.Account;
import com.company.project.service.AccountService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 控制器类
 *
 * @author Zhoujh
 * @date 2017/12/01
 */
@RestController
@RequestMapping("/account")
public class AccountController {
    @Resource
    private AccountService accountService;

    @GetMapping("/addjob")
    public Result index() {
        Result result = new Result();
        QuartzManager.addJob("getUser", AccountTask.class, "*/1 * * * * ?");
        result.setCode(ResultCode.SUCCESS);
        result.setMessage("任务添加成功");
        return result;
    }

    @GetMapping("/stopjob")
    public Result stop() {
        Result result = new Result().setCode(ResultCode.SUCCESS).setMessage("任务已停止");
        QuartzManager.shutdownJobs();
        return result;
    }

    @PostMapping("/add")
    public Result add(Account account) {
        accountService.save(account);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        accountService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(Account account) {
        accountService.update(account);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        Account account = accountService.findById(id);
        return ResultGenerator.genSuccessResult(account);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size);
        List<Account> list = accountService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
