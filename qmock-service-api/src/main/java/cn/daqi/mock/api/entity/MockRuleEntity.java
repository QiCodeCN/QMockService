package cn.daqi.mock.api.entity;

import lombok.Data;

@Data
public class MockRuleEntity extends BaseEntity{
    Integer id;
    Integer apiId;
    String Title;
    String type;
    Integer enable;
    String requestFilter;
    String responseBody;
    Integer responseCode=200;
    String shell;
}
