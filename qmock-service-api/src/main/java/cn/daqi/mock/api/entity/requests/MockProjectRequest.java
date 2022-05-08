package cn.daqi.mock.api.entity.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ Author: Zhang Qi
 * @ Copyright: 博客&公众号《大奇测试开发》
 * @ Describe: 项目管理实体信息类
 */
@Data
public class MockProjectRequest {
    // 项目ID
    private Integer id;

    // 项目名称
    @NotBlank(message = "项目名称不能为空")
    private String name;

    // 项目描述
    private String desc;

    // 项目负责人
    private String owner;

    // 项目属性 public-公开,private-私有
    private String type;

    // 操作人
    private String operator;
}
