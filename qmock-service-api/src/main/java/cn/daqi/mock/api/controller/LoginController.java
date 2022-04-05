package cn.daqi.mock.api.controller;

import cn.daqi.mock.api.commons.RespResult;
import cn.daqi.mock.api.entity.requests.LoginRequest;
import cn.daqi.mock.api.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Author: Zhang Qi
 * @ Copyright: 博客&公众号《大奇测试开发》
 * @ Describe: 登录接口API
 */
@RestController
@RequestMapping(value = "/api/login")
public class LoginController {

    @Autowired
    LoginService loginService;

    /**
     * 登录验证接口
     * @param req Post请求body参数体
     * @return JSON统一格式体
     */
    @PostMapping(value = "/account")
    public RespResult login(@RequestBody LoginRequest req){
        return loginService.accountLogin(req);
    }
}
