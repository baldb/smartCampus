package com.linyi.zhcompus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linyi.zhcompus.pojo.Grade;
import com.linyi.zhcompus.service.GradeService;
import com.linyi.zhcompus.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 林逸
 * cool boy
 * 1.0
 */
@Api(tags = "年级控制器")
@RestController
@RequestMapping("/sms/gradeController")
@Slf4j
@Transactional
public class GradeController {

    @Autowired
    private GradeService gradeService;

    /**
     * 分页 查询 显示 年级
     * @param pageNum
     * @param pageSize
     * @param gradeName
     * @return
     */
    @ApiOperation("查询Grade信息")
    @GetMapping("/getGrades/{pageNum}/{pageSize}")
    public Result getGrades(
            @ApiParam("第几页的页数") @PathVariable Integer pageNum,
            @ApiParam("每页输出的条数") @PathVariable Integer pageSize,
            @ApiParam("模糊查询条件") String gradeName){

        log.info("当前页{}，总页数{}", pageNum, pageSize);
        // 分页
        Page<Grade> page = new Page<>(pageNum, pageSize);
        // 查询
        IPage<Grade> pageInfo = gradeService.getGradeByOpr(page, gradeName);
        return Result.ok(pageInfo);
    }

    /**
     * 添加 修改 年级
     * @param grade
     * @return
     */
    @ApiOperation("添加/修改Grade信息，,有id则修改，没有则添加")
    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(
            @ApiParam("Grade信息的JSON格式") @RequestBody Grade grade) {
        gradeService.saveOrUpdate(grade);
        return Result.ok();
    }

    /**
     * 删除 年级
     * @param ids
     * @return
     */
    @ApiOperation("删除Grade信息")
    @DeleteMapping("/deleteGrade")
    public Result deleteGradeById(
            @ApiParam("要删除的所有年级id的JSON集合") @RequestBody List<Integer> ids){
        gradeService.removeByIds(ids);
        return Result.ok();
    }

    /**
     * 班级管理显示年级下拉框
     * @return
     */
    @ApiOperation("班级管理显示年级下拉框")
    @GetMapping("/getGrades")
    public Result getGrades(){
        List<Grade> grades = gradeService.getGrades();
        return Result.ok(grades);
    }
}
