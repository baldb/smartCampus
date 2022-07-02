package com.linyi.zhcompus.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName tb_grade
 */
@TableName(value ="tb_grade")
@Data
public class Grade implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;


    private String name;


    private String manager;


    private String email;


    private String telephone;


    private String introducation;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}