package cn.daqi.mock.api.entity;

import lombok.Data;

import java.sql.Date;

/**
 * @ Author: Zhang Qi
 * @ Copyright: 博客&公众号《大奇测试开发》
 * @ Describe:
 */
@Data
public class BaseEntity {
    private String createUser;
    private Date createDate;
    private String updateUser;
    private Date updateDate;
}
