package cn.daqi.mock.api.entity;

import lombok.Data;

@Data
public class MockInterfaceEntity extends BaseEntity{

    // 接口名称
    private Integer id;

    // 归属分类
    private Integer tagId;

    // 归属项目
    private Integer projectId;

    // 接口名称
    private String title;

    // 接口方法
    private String method;

    // 接口路径
    private String path;

    // 状态 默认0启用 1关闭
    private String enable;

    // 接口描述
    private String desc;

    // 默认状态码
    private Integer responseCode;

    // 默认返回值
    private String responseDefault;

}
