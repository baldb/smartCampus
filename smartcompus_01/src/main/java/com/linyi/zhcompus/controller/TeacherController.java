package com.linyi.zhcompus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linyi.zhcompus.pojo.Teacher;
import com.linyi.zhcompus.service.TeacherService;
import com.linyi.zhcompus.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 林逸
 * cool boy
 * 1.0
 */
@RestController
@RequestMapping("/sms/teacherController")
@Transactional
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    /**
     * 教师信息 分页 查询
     * @param pageNum
     * @param pageSize
     * @param teacher
     * @return
     */
    @GetMapping("/getTeachers/{pageNum}/{pageSize}")
    public Result getTeachers(@PathVariable Integer pageNum,@PathVariable Integer pageSize,Teacher teacher) {
        Page<Teacher> pageParam = new Page<>(pageNum, pageSize);
        IPage<Teacher> page = teacherService.getTeachersByOpr(pageParam, teacher);
        return Result.ok(page);
    }

    /**
     * 教师信息 添加 修改
     * @param teacher
     * @return
     */
    @PostMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(@RequestBody Teacher teacher) {
        teacherService.saveOrUpdate(teacher);
        return Result.ok();
    }

    /**
     * 教师信息 删除
     * @param ids
     * @return
     */
    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(@RequestBody List<Integer> ids){
        teacherService.removeByIds(ids);
        return Result.ok();
    }
}

