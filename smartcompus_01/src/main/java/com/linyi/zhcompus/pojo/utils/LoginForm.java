package com.linyi.zhcompus.pojo.utils;

import lombok.Data;

/**
 * @project: ssm_sms
 * @description: 用户登录表单信息
 * 封装了登陆返回给后端的请求。便于使用Body
 */
@Data
public class LoginForm {

    private String username;
    private String password;
    private String verifiCode; //验证码
    private Integer userType;

}
