package cn.daqi.mock.api.entity;

import lombok.Data;

/**
 * @ Author: Zhang Qi
 * @ Copyright: 博客&公众号《大奇测试开发》
 * @ Describe:
 */
@Data
public class LoginEntity extends BaseEntity{

    private Integer id;
    private String name ;
    private String password;
}
