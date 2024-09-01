package cn.daqi.mock.api.entity.requests;

import lombok.Data;

@Data
public class MockRuleRequest {
    // 项目ID
    private Integer apiId;

    // 接口名称
    private Integer current=1;

    // 请求页数
    private Integer pageSize=20;
}
