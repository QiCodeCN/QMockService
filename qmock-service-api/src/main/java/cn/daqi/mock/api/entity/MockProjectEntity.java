package cn.daqi.mock.api.entity;

import lombok.Data;

/**
 * @ Author: Zhang Qi
 * @ Copyright: 博客&公众号《大奇测试开发》
 * @ Describe: 项目管理字段实体类
 */
@Data
public class MockProjectEntity extends BaseEntity{
    // 自增ID编号
    private Integer id;

    // 项目名称
    private String name;

    // 项目类型 public 或 private
    private String type;

    // 项目描述
    private String desc;

    // 项目管理人或者负责人
    private String owner;
}
