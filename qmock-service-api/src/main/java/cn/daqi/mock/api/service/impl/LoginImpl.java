package cn.daqi.mock.api.service.impl;

import cn.daqi.mock.api.commons.RespCode;
import cn.daqi.mock.api.commons.RespResult;
import cn.daqi.mock.api.entity.requests.LoginRequest;
import cn.daqi.mock.api.mapper.LoginMapper;
import cn.daqi.mock.api.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ Author: Zhang Qi
 * @ Copyright: 博客&公众号《大奇测试开发》
 * @ Describe:
 */
@Service
public class LoginImpl implements LoginService {

    @Autowired
    LoginMapper loginMapper;

    @Override
    public RespResult accountLogin(LoginRequest req) {
        Integer count= loginMapper.userLogin(req.getUsername(), req.getPassword());
        if (count > 0) {
            return RespResult.success();
        } else {
            return RespResult.failure(RespCode.USER_AUTHORITY_FAILURE);
        }
    }
}
