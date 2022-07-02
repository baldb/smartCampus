package com.linyi.zhcompus.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName tb_clazz
 */
@TableName(value ="tb_clazz")
@Data
public class Clazz implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;


    private String name;


    private String number;


    private String introducation;


    private String headmaster;


    private String telephone;


    private String email;


    private String gradeName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}