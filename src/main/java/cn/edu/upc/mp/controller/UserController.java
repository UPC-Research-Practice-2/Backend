package cn.edu.upc.mp.controller;


import cn.edu.upc.mp.dao.UserRepository;
import cn.edu.upc.mp.entity.User;
import cn.edu.upc.mp.entity.info.Failure;
import cn.edu.upc.mp.entity.info.Success;
import cn.edu.upc.mp.securty.JWTUtil;
import cn.edu.upc.mp.securty.UnauthorizedException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;


    @RequestMapping(value = "/currentUserInfo", method = RequestMethod.GET)
    @ResponseBody
    public Object currentUserInfo() {
        //登录有效性验证
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated() == false) {
            return new Failure("用户登录失败！");
        }

//        try {
//            // 根据token确定当前登录用户
//            UserInfo userInfo = new UserInfo(generalService.getLoginUser());
//            // 返回当前用户的信息（通过新的构造型，避免返回密码等信息）
//            return new SuccessInfo(userInfo);
//        } catch (Exception e) {
//            return new FailureInfo(7003, "获取用户详细信息失败。");
//        }
        return null;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(String phoneNumber, String password) {
        try {
            // 根据用户提交的手机号码作为索引定位到唯一用户
            User user = userRepository.getUserByPhoneNumber(phoneNumber);
            // 定位不到则返回手机号码对应的用户不存在
            if (user == null) {
                return new Failure(404, "用户不存在");
            }
            //用户名密码是否匹配
            if (User.getPasswordHash(password).equals(user.getPassword())) {
                // 密码匹配则返回一个token作为身份凭证
                String token = JWTUtil.generateToken(phoneNumber, User.getPasswordHash(password));
                return new Success(token);
            } else {
                // 否则返回用户名密码不匹配
                return new Failure(401, "用户名密码不匹配！");
            }
        } catch (Exception e) {
            System.err.println("用户名密码验证出现异常！详细信息：");
            System.err.println(e.toString());
        }
        return new Failure("登录过程遇到问题，请稍后重试。");
    }

//    @RequestMapping(value = "/test", method = RequestMethod.GET)
//    @ResponseBody
//    public Object test() {
//        Subject subject = SecurityUtils.getSubject();
//        if (subject.isAuthenticated() == false) {
//            return new FailureInfo();
//        }
//        return new SuccessInfo("success!");
//    }

    @RequestMapping(value = "/userID", method = RequestMethod.GET)
    @ResponseBody
    public Object getUserID() {
        //登录有效性验证
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated() == false) {
            return new Failure("用户登录失败！");
        }

        try {
            // 获取当前登录用户，并返回其ID
            String token = null;
            try {
                token = subject.getPrincipal().toString();
            } catch (Exception e) {
                return new Failure("获取当前用户信息失败！");
            }
            User user = userRepository.getUserByPhoneNumber(JWTUtil.getPhoneNumber(token));
            return new Success(user.getUserID());
        } catch (Exception e) {
            return new Failure(-2, "获取用户ID信息失败。");
        }
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ResponseBody
    public Object signup(String password, String realName, String department, String email, String phoneNumber, Integer userGroup) {
        try {
            // 邮箱是否已经注册
            if (userRepository.getUserByEmail(email) != null) {
                return new Failure(7103, "该邮箱已注册，不可使用！");
            }
            // 邮箱格式正则匹配格式
            String emailCheck = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(emailCheck);
            Matcher matcher = regex.matcher(email);
            Boolean isMatched = matcher.matches();
            if (isMatched == false) {
                return new Failure(7102, "邮箱格式不正确");
            }
            // 手机号码是否注册
            if (userRepository.getUserByPhoneNumber(phoneNumber) != null) {
                return new Failure(7105, "该手机号码已注册，不可使用！");
            }
            // 手机号码正则匹配格式
            String mobileCheck = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
            regex = Pattern.compile(mobileCheck);
            matcher = regex.matcher(phoneNumber);
            isMatched = matcher.matches();
            if (isMatched == false) {
                return new Failure(7104, "手机号码格式不正确");
            }

            User newUser = new User(password, email, phoneNumber);
            userRepository.save(newUser);

            return new Success(newUser.getUserID());

        } catch (Exception e) {
            System.err.println("遇到错误，请核实：");
            System.err.println(e.toString());
            return new Failure(7005, "注册遇到未知错误，请通知管理员检查系统日志，谢谢！");
        }
    }


    @RequestMapping(value = "/password", method = RequestMethod.POST)
    @ResponseBody
    public Object changePassword(String oldPassword, String newPassword) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated() == false) {
            return new Failure("没有登录，请重新登录后再试。");
        }
        // 通过是否是32位简单验证是否是MD5加密之后的结果
        if (oldPassword.length() != 32 || newPassword.length() != 32) {
            return new Failure(7006, "原密码格式不正确。");
        }

        try {
            // 获取当前登录用户
            User user = null;
            try {
                // 获取当前登录用户，并返回其ID
                String token = null;
                try {
                    token = subject.getPrincipal().toString();
                } catch (Exception e) {
                    return new Failure("获取当前用户信息失败！");
                }
                user = userRepository.getUserByPhoneNumber(JWTUtil.getPhoneNumber(token));
            } catch (Exception e) {
                return new Failure(-2, "获取用户ID信息失败。");
            }

            // 检验旧密码是否正确，如果不正确返回提示
            if (user.getPassword().equals(User.getPasswordHash(oldPassword)) == false) {
                return new Failure(7003, "原密码不正确。");
            }
            // 通过检验的话新密码加入数据库
            user.setPassword(User.getPasswordHash(newPassword));
            userRepository.save(user);
            return new Success("密码修改成功！");
        } catch (Exception e) {
            System.err.println("密码修改失败！");
            System.err.println(e.toString());
            return new Failure(7004, "密码修改遇到未知错误。");
        }
    }

//    @ApiOperation(value = "更新用户个人信息")
//    @RequestMapping(value = "/currentUserInfo", method = RequestMethod.POST)
//    @ResponseBody
//    public Object updateCurrentUserInfo(String email, String phoneNumber) {
//        // 登录有效性验证
//        Subject subject = SecurityUtils.getSubject();
//        if (subject.isAuthenticated() == false) {
//            return new FailureInfo();
//        }
//        // 邮箱和电话标记，分别标注输入的是否是当前的手机/邮箱
//        // 用来判断是否更改数据库。
//        Boolean emailFlag = true;
//        Boolean phoneNumberFlag = true;
//
//        try {
//            User user = generalService.getLoginUser();
//            // 判断是否有所变更
//            if (user.getPhoneNumber().equals(phoneNumber)) {
//                phoneNumberFlag = false;
//            }
//            if (user.getEmail().equals(email)) {
//                emailFlag = false;
//            }
//            // 是当前用户则没有不提示已注册
//            // 不是当前用户且能找到当前邮箱用户，则提示邮箱已注册
//            if (emailFlag && userRepository.getUserByEmail(email) != null) {
//                return new FailureInfo(7103, "该邮箱已注册，不可使用！");
//            }
//            if (generalService.checkEmail(email) != true) {
//                return new FailureInfo(7102, "邮箱格式不正确");
//            }
//            // 邮箱写入对象属性
//            user.setEmail(email);
//
//            // 是当前用户则没有不提示已注册
//            // 不是当前用户且能找到当前手机用户，则提示邮箱已注册
//            if (phoneNumberFlag && userRepository.getUserByPhoneNumber(phoneNumber) != null) {
//                return new FailureInfo(7105, "该手机号码已注册，不可使用！");
//            }
//            if (generalService.checkMoiblePhone(phoneNumber) != true) {
//                return new FailureInfo(7104, "手机号码格式不正确");
//            }
//            // 手机写入对象属性
//            user.setPhoneNumber(phoneNumber);
//            userRepository.save(user);
//            return new SuccessInfo("个人信息更新成功！");
//        } catch (Exception e) {
//            System.err.println("个人信息更新失败！");
//            System.err.println(e.toString());
//            return new FailureInfo(7005, "个人信息更新遇到未知错误。");
//        }
//    }
//
//    @ApiOperation(value = "获取当前用户的用户组")
    @RequestMapping(value = "/userGroup", method = RequestMethod.GET)
    @ResponseBody
    public Object getUserGroup() {
        return new Success(0);
//        try {
//            // 登录有效性验证
//            Subject subject = SecurityUtils.getSubject();
//            if (subject.isAuthenticated() == false) {
//                return new FailureInfo();
//            }
//            User user = generalService.getLoginUser();
//            return new SuccessInfo(user.getUserGroup());
//        } catch (Exception e) {
//            return new FailureInfo(7006, "获取用户组失败。");
//        }
    }

}
