package com.linyi.zhcompus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linyi.zhcompus.pojo.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.linyi.zhcompus.pojo.utils.LoginForm;

/**
* @author linyi
* @description 针对表【tb_teacher】的数据库操作Service
* @createDate 2022-07-01 23:16:09
*/
public interface TeacherService extends IService<Teacher> {

    Teacher login(LoginForm loginForm);
    Teacher getTeacherById(int intValue);

    IPage<Teacher> getTeachersByOpr(Page<Teacher> pageParam, Teacher teacher);
}
