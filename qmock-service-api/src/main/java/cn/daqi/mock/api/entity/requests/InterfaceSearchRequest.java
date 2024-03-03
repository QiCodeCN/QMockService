package cn.daqi.mock.api.entity.requests;

import lombok.Data;
import lombok.NonNull;

@Data
public class InterfaceSearchRequest {
    // 项目ID
    private Integer projectId;

    // 分类ID
    private Integer tagId;

    // 接口名称
    private String title;

    // 是否启用
    private Integer enable;

    // 接口方法
    private String method;

    // 接口路径（模糊匹配）
    private String path;

    // 接口名称
    private Integer current=1;

    // 请求页数
    private Integer pageSize=20;
}
