package com.linyi.zhcompus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linyi.zhcompus.pojo.Admin;
import com.linyi.zhcompus.service.AdminService;
import com.linyi.zhcompus.utils.MD5;
import com.linyi.zhcompus.utils.Result;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@SuppressWarnings({"all"})
@RestController
@RequestMapping("/sms/adminController")
@Transactional
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 管理员 分页 查询
     * @param pageNum
     * @param pageSize
     * @param adminName
     * @return
     */
    @GetMapping("/getAllAdmin/{pageNum}/{pageSize}")
    public Result getAllAdmin(@PathVariable Integer pageNum,@PathVariable Integer pageSize,String adminName){
        Page<Admin> pageInfo = new Page<>(pageNum,pageSize);
        IPage<Admin> page = adminService.getAdmins(pageInfo, adminName);
        return Result.ok(page);
    }

    /**
     * 管理员 添加 修改
     * @param admin
     * @return
     */
    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(@RequestBody Admin admin){
        if (!Strings.isEmpty(admin.getPassword())) {
            admin.setPassword(MD5.encrypt(admin.getPassword()));
        }
        adminService.saveOrUpdate(admin);
        return Result.ok();
    }

    /**
     * 管理员 删除
     * @param ids
     * @return
     */
    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(@RequestBody List<Integer> ids){
        adminService.removeByIds(ids);
        return Result.ok();
    }
}
