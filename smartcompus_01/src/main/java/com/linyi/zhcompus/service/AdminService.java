package com.linyi.zhcompus.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.linyi.zhcompus.pojo.Admin;
import com.linyi.zhcompus.pojo.utils.LoginForm;

public interface AdminService extends IService<Admin> {


    Admin login(LoginForm loginForm);

    Admin getAdminById(int userId);

    IPage<Admin> getAdmins(Page<Admin> pageInfo, String adminName);
}
