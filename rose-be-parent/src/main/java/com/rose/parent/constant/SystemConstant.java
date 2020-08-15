package com.rose.parent.constant;

public interface SystemConstant {

    // redis 中用户信息key前缀
    String LOGIN_USER_INFO_PREFIX = "user_id_springcloudalibaba_";

    // redis 中用户信息保存时间 8h
    long LOGIN_USER_INFO__SAVE_TIME = 8 * 60;

    // redis中用户登录验证码前缀
    String LOGIN_CODE_PREFIX = "login_code_springcloudalibaba_";

    // redis中用户登录验证码保存时间 5分钟
    long LOGIN_CODE_SAVE_TIME = 5;

    // 系统前后端交互中 userId、token key名
    String SYSTEM_USER_ID = "userId";
    String SYSTEM_TOKEN_NAME = "token";
}