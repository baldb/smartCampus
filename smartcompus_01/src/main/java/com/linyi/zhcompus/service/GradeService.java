package com.linyi.zhcompus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linyi.zhcompus.pojo.Grade;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author linyi
* @description 针对表【tb_grade】的数据库操作Service
* @createDate 2022-07-01 23:15:23
*/
public interface GradeService extends IService<Grade> {

    IPage<Grade> getGradeByOpr(Page<Grade> page, String gradeName);

    List<Grade> getGrades();
}
