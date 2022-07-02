package com.linyi.zhcompus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linyi.zhcompus.pojo.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import com.linyi.zhcompus.pojo.utils.LoginForm;

/**
* @author linyi
* @description 针对表【tb_student】的数据库操作Service
* @createDate 2022-07-01 23:15:56
*/
public interface StudentService extends IService<Student> {

    Student login(LoginForm loginForm);

    Student getStudentById(int intValue);

    IPage<Student> getStudentByOpr(Page<Student> page, Student student);
}
