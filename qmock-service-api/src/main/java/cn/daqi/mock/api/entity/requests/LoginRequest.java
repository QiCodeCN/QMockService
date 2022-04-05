package cn.daqi.mock.api.entity.requests;

import lombok.Data;

/**
 * @ Author: Zhang Qi
 * @ Copyright: 博客&公众号《大奇测试开发》
 * @ Describe:
 */
@Data
public class LoginRequest {
    private String username;
    private String password;
}
