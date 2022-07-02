package com.linyi.zhcompus.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;

import com.linyi.zhcompus.pojo.Admin;
import com.linyi.zhcompus.pojo.Student;
import com.linyi.zhcompus.pojo.Teacher;
import com.linyi.zhcompus.pojo.utils.LoginForm;
import com.linyi.zhcompus.service.AdminService;
import com.linyi.zhcompus.service.StudentService;
import com.linyi.zhcompus.service.TeacherService;
import com.linyi.zhcompus.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 针对非操作数据库表的操作
 * 例如登陆
 */
@SuppressWarnings({"all"})
@RestController
@RequestMapping("/sms/system")
public class SystemController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    /**
     * 获取验证码
     * @param request
     * @param response
     */
    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response) {
        // 获取图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        // 获取图片上的文字
//        char[] verifiCode1 = CreateVerifiCodeImage.getVerifiCode();
        //把上面的char[]格式转换成字符串String格式
        String verifiCode = new String(CreateVerifiCodeImage.getVerifiCode());
        // 将验证码放入session域，为下次验证准备
        HttpSession session = request.getSession();
        session.setAttribute("verifiCode", verifiCode);
        // 将验证码图片响应给浏览器
        /**
         * 利用输出流发送
         * response.getOutputStream()输出流
         * ImageIO方法可以帮助我们直接通过io数据流将图片显示给前端
          */
        try {
            ImageIO.write(verifiCodeImage, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 用户登录
     * @param loginForm
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginForm loginForm, HttpServletRequest request){

        /**
         * 验证码校验：
         * 获取后段传过去的验证码图片上的验证码
         * request.getSession().getAttribute("verifiCode");
         * 获取用户输入的验证码
         * loginForm.getVerifiCode();
         */
        String verifiCode = (String)request.getSession().getAttribute("verifiCode");
        String inputverifiCode = loginForm.getVerifiCode();
        if ("".equals(verifiCode) || verifiCode == null) { //传过去的为{}或null，则验证码失效
            return Result.fail().message("验证码失效，请刷新");
        }
        if (!verifiCode.equalsIgnoreCase(inputverifiCode)){ //传过去的验证码与用户输入的验证码不匹配，则验证码有误
            return Result.fail().message("验证码有误，请重试");
        }
        // 验证码验证成功后，就移除使用过的验证码
        request.getSession().removeAttribute("verifiCode");
        // 用户类型校验
        Map<String, Object> map = new LinkedHashMap<>();
        Integer userType = loginForm.getUserType();
        switch (loginForm.getUserType()){
            case 1:
                try {
                    Admin admin = adminService.login(loginForm);
                    if (admin != null) {
                        // 将用户的类型和用户id转换成一个密文，以token的名称返回客户端
                        //.longValue() ：将Integer型转为long类型
                        String token = JwtHelper.createToken(admin.getId().longValue(), 1);
                        map.put("token", token);
                    } else {
                        throw new RuntimeException("用户名或密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException ex) {
                    ex.printStackTrace();
                    return Result.fail().message(ex.getMessage());
                }
            case 2:
                try {
                    Student student = studentService.login(loginForm);
                    if (student != null) {
                        // 将用户的类型和用户id转换成一个密文，以token的名称返回客户端
                        String token = JwtHelper.createToken(student.getId().longValue(), 2);
                        map.put("token", token);
                    } else {
                        throw new RuntimeException("用户名或密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException ex) {
                    ex.printStackTrace();
                    return Result.fail().message(ex.getMessage());
                }
            case 3:
                try {
                    Teacher teacher = teacherService.login(loginForm);
                    if (teacher != null) {
                        // 将用户的类型和用户id转换成一个密文，以token的名称返回客户端
                        String token = JwtHelper.createToken(teacher.getId().longValue(), 3);
                        map.put("token", token);
                    } else {
                        throw new RuntimeException("用户名或密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException ex) {
                    ex.printStackTrace();
                    return Result.fail().message(ex.getMessage());
                }
            default:
                return Result.fail().message("没有该用户");

        }
    }

    /**
     * 跳转首页
     * @param token
     * @return
     */
    @GetMapping("/getInfo")
    public Result getInfoByToken(@RequestHeader("token") String token){
        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration){
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        // 解析用户id和用户类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        Map<String, Object> map = new LinkedHashMap<>();
        switch (userType){
            case 1:
                Admin admin = adminService.getAdminById(userId.intValue());
                map.put("user",admin);
                map.put("userType",1);
                break;
            case 2:
                Student student = studentService.getStudentById(userId.intValue());
                map.put("user",student);
                map.put("userType",2);
                break;
            case 3:
                Teacher teacher = teacherService.getTeacherById(userId.intValue());
                map.put("user",teacher);
                map.put("userType",3);
                break;
        }
        return Result.ok(map);
    }

    /**
     * 图片上传
     * @param multipartFile
     * @return
     */
    @PostMapping("/headerImgUpload")
    public Result headerImgUpload(@RequestPart("multipartFile") MultipartFile multipartFile) {
        //使用UUID随机生成文件名
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        //生成新的文件名字
        String newFileName = uuid.concat(multipartFile.getOriginalFilename());
        // 保存文件到指定目录
        String portraitPath ="D:/project/zhxy/target/classes/public/upload/".concat(newFileName);
        try {
            multipartFile.transferTo(new File(portraitPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 响应图片的路径
        String path = "upload/"+newFileName;
        return Result.ok(path);
    }


    /**
     * 修改密码
     * @param token
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @PostMapping("/updatePwd/{oldPwd}/{newPwd}")
    public Result updatePwd(@RequestHeader String token, @PathVariable String oldPwd, @PathVariable String newPwd){
        boolean expiration = JwtHelper.isExpiration(token);
        if(expiration){
            // 如果token过期
            return Result.fail().message("token失效!");
        }
        //通过token获取当前登录的用户id
        Long userId = JwtHelper.getUserId(token);
        //通过token获取当前登录的用户类型
        Integer userType = JwtHelper.getUserType(token);
        // 将明文密码转换为暗文
        oldPwd= MD5.encrypt(oldPwd);
        newPwd= MD5.encrypt(newPwd);
        if(userType == 1){
            QueryWrapper<Admin> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("id",userId.intValue()).eq("password",oldPwd);
            Admin admin = adminService.getOne(queryWrapper);
            if (admin != null) {
                admin.setPassword(newPwd);
                adminService.saveOrUpdate(admin);
            }else{
                return Result.fail().message("原密码输入有误！");
            }
        }else if(userType == 2){
            QueryWrapper<Student> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("id",userId.intValue()).eq("password",oldPwd);
            Student student = studentService.getOne(queryWrapper);
            if (student != null) {
                student.setPassword(newPwd);
                studentService.saveOrUpdate(student);
            }else{
                return Result.fail().message("原密码输入有误！");
            }
        }
        else if(userType == 3){
            QueryWrapper<Teacher> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("id",userId.intValue()).eq("password",oldPwd);
            Teacher teacher = teacherService.getOne(queryWrapper);
            if (teacher != null) {
                teacher.setPassword(newPwd);
                teacherService.saveOrUpdate(teacher);
            }else{
                return Result.fail().message("原密码输入有误！");
            }
        }
        return Result.ok();
    }
}
