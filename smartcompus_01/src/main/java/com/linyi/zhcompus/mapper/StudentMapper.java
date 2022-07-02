package com.linyi.zhcompus.mapper;

import com.linyi.zhcompus.pojo.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author linyi
* @description 针对表【tb_student】的数据库操作Mapper
* @createDate 2022-07-01 23:15:56
* @Entity com.linyi.zhcompus.pojo.Student
*/
@Repository
public interface StudentMapper extends BaseMapper<Student> {

}




