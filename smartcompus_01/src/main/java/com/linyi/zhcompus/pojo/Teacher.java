package com.linyi.zhcompus.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName tb_teacher
 */
@TableName(value ="tb_teacher")
@Data
public class Teacher implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;


    private String tno;


    private String name;


    private String gender;


    private String password;


    private String email;


    private String telephone;


    private String address;


    private String portraitPath;


    private String clazzName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}