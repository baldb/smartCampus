package com.linyi.zhcompus.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName tb_student
 */
@TableName(value ="tb_student")
@Data
public class Student implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;


    private String sno;


    private String name;


    private String gender;


    private String password;


    private String email;


    private String telephone;


    private String address;


    private String introducation;


    private String portraitPath;


    private String clazzName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}